package com.dana;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionDatabase {
    private static final String DB_URL = "jdbc:sqlite:SQLite/subscription_api.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
