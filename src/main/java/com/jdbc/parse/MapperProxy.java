package com.jdbc.parse;


import com.dao.IUserInfoMapper;
import com.entity.UserInfo;
import com.jdbc.pool.JdbcConnPoolV2;
import com.utils.Page;
import com.utils.PageSqlUtil;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 接口的代理类对象  调用的方法
 *
 * @author zp
 * @create 2020-07-26-15:47
 * <p>
 * wj
 * 2020-08-08 增加limit
 */
public class MapperProxy<T> implements InvocationHandler {
    //连接池
    private final JdbcConnPoolV2 jdbcConnPoolV2 = JdbcConnPoolV2.producePool();

    //test
    public static void main(String[] args) {
        MapperProxy<IUserInfoMapper> iUserMapperMapperProxy = new MapperProxy<>();
        IUserInfoMapper mapper = iUserMapperMapperProxy.getMapper(IUserInfoMapper.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("1");
//        userInfo.setUserMail("sagas");
//        userInfo.setUserCreateTime(new Date(System.currentTimeMillis()));
//        userInfo.setUserUpdateTime(new Date(System.currentTimeMillis()));
        userInfo.setUserPassword("1");
//        userInfo.setUserDelete(1);a
        //System.out.println(userInfo);

        //  List<UserInfo> user = mapper.listAllUsers();
        //System.out.println(user);
        PageSqlUtil pageSqlUtil = new PageSqlUtil();
        pageSqlUtil.setStartPage(1);
        pageSqlUtil.setItemNum(1);
        Page page = new Page();
        page.setCurrPage(1);
        page.setPageSize(1);
        PageSqlUtil instance = PageSqlUtil.getInstance(page);
        //System.out.println(userInfos);
        //System.out.println(byUser);
        System.out.println("11111");
////        User user1 = new User();
//        mapper.register(user);
//        User user2 = new User();
//        user.setUserId(8);
//        user.setUserName("沾上干");
//        user.setUserMail("sagas");
//        user.setUserCreateTime(new Date(System.currentTimeMillis()));
//        user.setUserUpdateTime(new Date(System.currentTimeMillis()));
//        user.setUserPassword("1234");
//        mapper.register(user2);
//        if(iUserMapperMapperProxy.list.size()!=0) System.out.println("登录成功");
    }

    /**
     * 获得mapper代理对象
     *
     * @param aClass 想要被代理的类
     * @return 返回一个代理对象
     */

    public T getMapper(Class<T> aClass) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{aClass}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = jdbcConnPoolV2.getConn();
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //根据CRUD调用不同的方法
        // 获得方法的全类名
        String s = method.getDeclaringClass().getName() + "." + method.getName();
        //System.out.println(s);
        MapperStatement mapperStatement = MapperParse.get(s);
        String sql = mapperStatement.getSql();
        PreparedStatement statement = connection.prepareStatement(sql);
        System.out.println("原生sql语句:" + sql);
        //设置占位置
        List<String> fieldNames = mapperStatement.getFieldNames();//拿到参数列表
        //System.out.println("参数列表" + fieldNames);
        String parameterType = mapperStatement.getParameterType();//拿到该参数列表对应的是那个类
//        System.out.println("参数列表对应的类为:" + parameterType);
        //这里的args是方法的参数
//        System.out.println("方法的参数:" + args[0]);
        //尝试

        if (!fieldNames.isEmpty()) {
            //动态生成
            Class<?> aClass1 = Class.forName(parameterType);

            List<Field> fields = Arrays.asList(args[0].getClass().getDeclaredFields());
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            fields.forEach(field -> {
                String name = field.getName();
                String getMethod = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                try {
                    Method declaredMethod = aClass1.getDeclaredMethod(getMethod);
                    Object invoke = declaredMethod.invoke(args[0]);
                    //将非空的值放入map中
                    stringObjectHashMap.put(name, invoke);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            //System.out.println(stringObjectHashMap);
            for (int i = 0; i < fieldNames.size(); i++) {
                //  根据属性名获取对应值
                String name = fieldNames.get(i);//todo:用于原子性sql语句 使用 map 比使用顺序插入更好
//                System.out.println(name);
//                System.out.println(stringObjectHashMap.get(name));
                /*System.out.println(name);*/
                if (SqlType.LIMIT.getName().equals(mapperStatement.getSqlType())
                ) {
                    statement.setInt(i + 1, Integer.parseInt((stringObjectHashMap.get(name)).toString()));
                } else { statement.setObject(i + 1, stringObjectHashMap.get(name));}
            }
        }
        System.out.println("sql语句:" + statement);
        //以上为了获取sql语句
        List<Object> list = new ArrayList<>();
        //开始进行操作
        //用枚举带替换魔法值
        if (SqlType.SELECT.getName().equals(mapperStatement.getSqlType()) ||
                SqlType.LIMIT.getName().equals(mapperStatement.getSqlType())
        ) {
            ResultSet resultSet = statement.executeQuery();
            ResultMap resultMap1 = mapperStatement.getResultMap();
            String classname = resultMap1.getClassname();
            //加载对应的bean类
            Class<?> aClass = Class.forName(classname);
            //得到结果集,这时就需要我们对结果集的解析
            ResultSetMetaData metaData = resultSet.getMetaData();//获得元数据
            int columnCount = metaData.getColumnCount();//获得列数

            //当解析结果的时候 无结果的时候其他返回的是null
            while (resultSet.next()) {
                Object o = aClass.getDeclaredConstructor().newInstance();//获取对应的bean类
                //遍历一行的数据
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);//获得列名
//                    System.out.println(columnLabel);
                    //把列名对应的java属性找出来
                    ResultMap resultMap = mapperStatement.getResultMap();
                    Result result = resultMap.get(columnLabel);
                    //System.out.println(result);
                    String fieldName = result.getFieldName();//获得了了java对应的属性
//                    System.out.println(fieldName);
                    //获得java字段的类型
                    Class<?> fieldType = result.getFieldType();
//                    System.out.println(fieldType);
                    Object object = resultSet.getObject(columnLabel);//获得对应的值
//                    System.out.println(object);
                    //获得属性,放置了属性
//                    declaredField.setAccessible(true);
//                    declaredField.set(o,resultSet.getObject(columnLabel));
                    String fieldMethod2 = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    //System.out.println(fieldMethod2);

                    Method declaredMethod = aClass.getDeclaredMethod(fieldMethod2, fieldType);
                    declaredMethod.invoke(o, object);
                }
//                System.out.println("查询结果" + o);
                list.add(o);
            }

        } else {//除了select语句之外的解析
//            System.out.println("执行语句");
            int i = statement.executeUpdate();
            connection.close();
            return i;
        }
        connection.close();
        return list;//将值传递出去,可能会更有用 @see:
//        return (list.size() == 0 ? null : list);
    }
}
