package org.stminaclinic.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DBConnection {
    public static Connection CreateConnection() {
        try {
            Connection dbConnection = null;
            String url = "jdbc:mysql://localhost:3306/db";
            Properties info = new Properties();
            info.put("user", "");
            info.put("password", "");
            dbConnection = DriverManager.getConnection(url, info);
            return dbConnection;
        } catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
            return null;
        }
    }

}
