package org.example.TodoAssignment.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/todoit";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private DBConnection() {
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
