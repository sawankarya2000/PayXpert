package com.payxpert.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
    public static Connection getDBConn() {
        Connection conn = null;
        try {
            String connectionString = DBPropertiesUtil.getConnectionString("resources/db.properties");
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connected");

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}