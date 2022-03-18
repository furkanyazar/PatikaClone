package com.patikadev.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    private Connection connection = null;

    public Connection connectDb() {
        try {
            this.connection = DriverManager.getConnection(Configs.DB_URL, Configs.DB_USERNAME, Configs.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.connection;
    }

    public static Connection getInstance() {
        DbConnector db = new DbConnector();
        return db.connectDb();
    }
}
