package com.caijing.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 * 提供获取数据库连接的静态方法
 */
public class DBUtil {

    // 数据库连接配置
    private static final String DB_URL = "jdbc:mysql://1.94.3.191:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Lyx!@#qwe123";

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}