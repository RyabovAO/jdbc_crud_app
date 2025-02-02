package com.aleksey.crud_app.DBUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MySqlConnection {

    public static Connection getConnection() {
        String dbUrl;
        String dbUser;
        String dbPassword;
        String driverUrl;
        Connection connection;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/BdConfig.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dbUrl = properties.getProperty("url");
        dbUser = properties.getProperty("userName");
        dbPassword = properties.getProperty("password");
        driverUrl = properties.getProperty("driver");

        try {
            Class.forName(driverUrl);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
