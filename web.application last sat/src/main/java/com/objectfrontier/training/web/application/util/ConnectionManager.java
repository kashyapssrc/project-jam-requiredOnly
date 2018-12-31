package com.objectfrontier.training.web.application.util;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {

    static Connection conn ;
    private static HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig("resources/ConnectionProperties.properties");
        config.setAutoCommit(false);
        ds = new HikariDataSource(config);
    }


    // inintialise ThreadLocal
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    // set connection to thread
    public static void initConnection() throws Exception {
        Connection connection = ds.getConnection();
        System.out.println("test2" + connection);
        threadLocal.set(connection);
    }


    // get connection from thread
    public static Connection getConnection() {
        Connection connection = threadLocal.get();
        System.out.println("test" + connection);
        return connection;
    }

    // remove thread
    public void remove() {

        threadLocal.remove();
    }

    public static void releaseConnection(boolean doCommit) {
        Connection connection = threadLocal.get();
        try {
            if (doCommit) {
                connection.commit();
                connection.close();
            } else {
                connection.rollback();
                connection.close();
            }
        } catch (Exception exception) {
            throw new AppException(ErrorCodes.CONNECTION_ERROR);
        }
    }
}
