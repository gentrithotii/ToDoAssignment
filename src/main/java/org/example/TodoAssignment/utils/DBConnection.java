package org.example.TodoAssignment.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private String url;
    private String user;
    private String password;

    private DBConnection() {
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.user = dotenv.get("DB_USER");
        this.password = dotenv.get("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new RuntimeException("No db environment variables in .env file ");
        }
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

//    private void loadDBConfigFromFile() {
//        String filePath = "src/main/resources/file";
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            this.url = reader.readLine();
//            this.user = reader.readLine();
//            this.password = reader.readLine();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load file", e);
//        }
//    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
