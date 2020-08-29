package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/19 9:00 上午
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://112.74.76.80:3306/cooking";
        String admin = "root";
        String pwd = "123456";
        Connection connection = DriverManager.getConnection(url, admin, pwd);
        System.out.println(connection);

    }
}
