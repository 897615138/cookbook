package com.listener;

import com.etc.FollowSystemUp;
import com.etc.LikeSystem;
import com.jdbc.pool.JdbcConnPoolV2;
import com.utils.FileUtil;
import com.utils.TimerUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;

/**
 * 监听器
 */
@WebListener
public class ApplicationListener implements ServletContextListener {
    private Timer timer = null;


    /**
     * 服务器启动的时候 自动调用该方法
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器启动了");

        String config = sce.getServletContext().getRealPath("WEB-INF/classes");
        System.out.println(config);

        //初始化db map
        FollowSystemUp.init();
        LikeSystem.init();
//        CollectSystem.init();
        //初始化连接池
        JdbcConnPoolV2.producePool();
        //初始化util.json map
        try {
            Class.forName("com.utils.FileUtil");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //初始化定时器

        //定时器功能 定时删除 Timer 

        //启动定时任务定时物理删除
        System.out.println("定时任务开始启动!");
        timer = new Timer(true);
        sce.getServletContext().log("定时器已经启动"); //添加日志
        timer.schedule(new TimerUtil(sce.getServletContext()), 0, Long.parseLong(FileUtil.timeLimit));//0表示任务无延迟,60*60*1000表示一个小时执行一次
        sce.getServletContext().log("已经添加任务");//添加日志
        System.out.println("初始化成功");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
        sce.getServletContext().log("定时器销毁");
    }
}
