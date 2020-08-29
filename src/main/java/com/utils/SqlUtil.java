package com.utils;
/**
 * 用来批量删除sql语句 并使用定时任务定时调用
 */

import com.jdbc.pool.JdbcConnPoolV2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/17 8:46 下午
 */
public class SqlUtil {
    /**
     * 批量删除delete为1 的字段
     */
    public static void doSql() {
        JdbcConnPoolV2 jdbcConnPoolV2 = JdbcConnPoolV2.producePool();
        Connection conn = jdbcConnPoolV2.getConn();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            //增加语句
            for (String s : FileUtil.sqlList) {
                statement.addBatch(s);
            }
            //执行语句
            System.out.println("现在开始执行定期删除计划........");
            statement.executeBatch();
            //批量清除
            statement.clearBatch();
            conn.close();
            System.out.println("执行结束");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public static void main(String[] args) {
        JdbcConnPoolV2 jdbcConnPoolV2 = JdbcConnPoolV2.producePool();
        Connection conn = jdbcConnPoolV2.getConn();
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
