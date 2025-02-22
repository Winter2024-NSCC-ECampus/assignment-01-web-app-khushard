package com.example.todoapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Utility class that manages DB connections.
 * */
public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/todo_app";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Static block to load the MySQL JDBC Driver. Runs when class is first loaded
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
     }
    // Makes a connection to the DB
     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
     }


    }
