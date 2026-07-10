package com.milktea.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static DataSource dataSource;

    static {
        try {
            // 加载数据库配置文件
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            // 创建C3P0连接池
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass(properties.getProperty("jdbc.driver"));
            cpds.setJdbcUrl(properties.getProperty("jdbc.url"));
            cpds.setUser(properties.getProperty("jdbc.username"));
            cpds.setPassword(properties.getProperty("jdbc.password"));

            // 设置连接池参数
            cpds.setInitialPoolSize(Integer.parseInt(properties.getProperty("c3p0.initialPoolSize")));
            cpds.setMaxPoolSize(Integer.parseInt(properties.getProperty("c3p0.maxPoolSize")));
            cpds.setMinPoolSize(Integer.parseInt(properties.getProperty("c3p0.minPoolSize")));
            cpds.setMaxIdleTime(Integer.parseInt(properties.getProperty("c3p0.maxIdleTime")));
            cpds.setAcquireIncrement(Integer.parseInt(properties.getProperty("c3p0.acquireIncrement")));
            cpds.setMaxStatements(Integer.parseInt(properties.getProperty("c3p0.maxStatements")));

            dataSource = cpds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接池初始化失败", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection对象
     * @throws SQLException SQL异常
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 获取数据源
     *
     * @return DataSource对象
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 关闭数据库连接
     *
     * @param connection Connection对象
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}