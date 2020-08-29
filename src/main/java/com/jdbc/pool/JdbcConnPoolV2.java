package com.jdbc.pool;


import com.utils.AssertUtil;
import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

//todo: 1. 网络繁忙等待时的公平排队问题? 目前思路:排队. 2.事务与事务隔离? 3. 活跃数与栈数量的问题.

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/7/25 8:57 下午
 */
public class JdbcConnPoolV2 {
    private static final ThreadLocal<ConnProxy> local = new ThreadLocal<>();
    private static final Stack<ConnProxy> stack = new Stack<>();
    private static final AtomicInteger inActive = new AtomicInteger();
    private static final ReentrantLock lock = new ReentrantLock(true);
    //数据库配置对象
    //THreadLocal
    //Stack
    //线程安全的变量
    //单例
    //线程安全lock公平锁
    //todo:这里增加修饰符的原则: 在不知情况下 优先加满修饰符.
    private static DBConfig config;//配置读取一次
    private volatile static JdbcConnPoolV2 jdbcConnPoolV2;

    static {
        try {
            //拿到数据库的配置信息
            config = DBConfig.parseConfig();
            Class.forName(config.getDriver()); //todo:选择了在静态块里加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //单例工厂方法 对外只能提供这个

    //init
    private JdbcConnPoolV2() {
        //初始化容器
        for (int i = 0; i < config.getInitialSize(); i++) {
            ConnProxy connProxy = new ConnProxy();
            connProxy.setConn(newConn());
            stack.push(connProxy);
        }
    }

    public static JdbcConnPoolV2 producePool() {
        //双重认证
        if (jdbcConnPoolV2 == null) {
            synchronized (JdbcConnPoolV2.class) {
                if (jdbcConnPoolV2 == null) {
                    jdbcConnPoolV2 = new JdbcConnPoolV2();
                }
            }

        }
        return jdbcConnPoolV2;
    }

    //newConn
    private Connection newConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(config.getUrl(), config.getUserName(), config.getPassword());
            //别忘了判断连接是否为null以及 是否关闭自动提交
            AssertUtil.isNull(conn);
            // conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//todo:可能有的 事务隔离机制问题
            //  System.out.println(conn.getTransactionIsolation());
            conn.setAutoCommit(false);//不让连接自动提交(因为自动会连接池的性质)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //getNewConnProxy if(conn==null) procude  newConn 但因为是被增强了所以其实就是返回一个ConnProxy对象
    private ConnProxy getNewConnProxy() {
        ConnProxy connProxy = null;
        //直到造出新的位置(其实也就是connProxy不为空为止)
        int wait = 0;
        while (true) {
            //如果栈为空
            lock.lock();
            if (stack.isEmpty()) {
                //而且连接数小于最大连接数,因为根据我们的逻辑限制 只是为空不能保证连接池没有达到最大连接 因为可能有长连接的线程在
                if (inActive.get() < config.getMaxActive()) {//等于就不造了不然多一个
                    connProxy = new ConnProxy();
                    connProxy.setConn(newConn());
                }
            } else {
                connProxy = stack.pop();
            }
            lock.unlock();
            if (connProxy != null) {
                break;//找到了就退出
            }
            //如果栈已经超过了最大连接数但是还是为空的话 我们就要让它等待1s中
            try {
                Thread.sleep(1000);//别忘了等待其实就是睡
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wait += 1000;
            if (wait >= config.getMaxWaitTime()) {
                throw new RuntimeException("网络繁忙");
            }
        }
        //活跃数要增加(因为现在有人需要连接了)
        inActive.incrementAndGet();
        return connProxy;
    }

    //getConn 动态代理 重写close方法 重置setId 这是最重要的
    //因为这个connection种我们要的是代理的conn而不是本来的因为我们要重写close方法用于回收连接
    //大体要做到的是几个方面:
    //1.看看要求的线程目前有没有连接对象
    //2.如果没有的话 调用getNewConnProxy 这里有两种 一个是栈为空那么我们就new一个对象直接返回 一个是栈不为空 那么就直接pop 知道找到一个新的对象为止 我们就把这个对象给set 然后出来
    //3.如果有连接对象那么这个连接对象就是原来的
    //4.真正的代理部分在这里 通过method方法获得要调用的函数名然后再通过函数名去重新close方法
    //因为如果不重写的话我们的连接池是不会有新的连接数增加的 连接数的来源只有初始化的时候 和返回的时候(也就是我们定义为close的时候)
    //其他方法 照旧
    public Connection getConn() {
        return (Connection) Proxy.newProxyInstance(JdbcConnPoolV2.class.getClassLoader(), new Class[]{Connection.class},
                (p, m, args) -> {
                    //先看看这个线程有没有这个连接对象啊
                    ConnProxy connProxy = local.get();
                    //如果没有(因为local的性质实际上是个map如果key没有那么返回null) 我们就调用方法 造一个出来
                    if (connProxy == null) {
                        connProxy = getNewConnProxy();
                    }
                    //获得连接以后 说明该链接就不空闲了
                    connProxy.setIdle(0);
                    //让这个线程继续保证事务完整
                    local.set(connProxy);
                    //后面就是代理的事情啦
                    //  System.out.println(m.getName());
                    Connection conn = connProxy.getConn();
                    if ("close".equals(m.getName())) {
                        //先调用commit方法? 将东西关闭
                        Method commit = conn.getClass().getDeclaredMethod("commit"); //这里再进行commit 也不是代理类进行commit了
                        commit.invoke(conn);
                        //继续用代理类对象调用
//                Method commit = p.getClass().getDeclaredMethod("commit");
//                commit.invoke(p);
                        //close相当于把连接会给栈 所以要-1活跃数
                        inActive.decrementAndGet();
                        local.remove();
                        stack.push(connProxy);//如果调用这个方法 那么处理的连接对象本身就是上面那个
                        return null;
                    } else {
//                if ("commit".equals(m.getName())) {
//                    //close相当于把连接会给栈 所以要-1活跃数
//                    inActive.decrementAndGet();
//                    local.remove();
//                    stack.push(connProxy);//如果调用这个方法 那么处理的连接对象本身就是上面那个
//                }
                        //conn的其他方法
                        return m.invoke(conn, args);
                    }


                });

    }

    //ConnProxy 静态代理增强类 里面放的是一个一直在运作的线程(所以如果程序停不了可能就是因为这个原因)
    @Data
    /*
     * 专门用来增强时间的内部类(通过静态代理增强实践)
     * 相当于本来的连接没有计时间的功能 我们通过一个静态代理 让这个静态代理类 有这个连接并且多了一个监视它的线程,相当于是多了一个计时器,不满足条件了以后我们就可以把他们移除掉
     */
    public static class ConnProxy {
        private int idle;//最大空闲时间
        private Connection conn;//把conn装进去

        public ConnProxy() {
            new Thread(() -> {
                //线程要一直运行着
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    idle += 1000;
                    if (idle > config.getIdleTimeOut()) {
                        //如果大于最大空闲时间 还要判断 是否超过了最小连接数 因为 如果低于最小连接数空闲是没问题的
                        try {
                            lock.lock();
                            if (stack.size() > config.getMinActive()) {
                                conn.close();//连接本身的关闭方法
                                stack.remove(ConnProxy.this);
                                System.out.println("空闲数量-1,当前值为:" + stack.size());
                                break;//完了以后我们就把这个线程给结束
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } finally {
                            lock.unlock();//这个unlock要保证要在if括号的前后 所以肯定会在break后面 但是 因为是break导致锁无法释放所以要放到finally里面
                        }

                    }

                }

            }).start();
        }
    }

}
