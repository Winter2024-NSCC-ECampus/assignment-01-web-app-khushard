package com.example.todoapp.dao;

import com.example.todoapp.model.User;
import com.example.todoapp.util.DatabaseUtil;
import java.sql.*;
/**
 * Data Access Object for handling all user-related database operations.
 * Manages user auth, registration and retrieves user data.
 * */

public class UserDAO {

    /**
     * Registers a new user in the DB
     * */
    public User registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             // Use auto generated user ID.
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Seting values for the SQL query
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            // Execute update returns number of rows affected
            int affected = pstmt.executeUpdate();

            // Check if insert was successful, > 0 means a row was created.
            if (affected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                return user;
            }
            // If no rows are added, throw error
            throw new SQLException("Registration failed - no rows affected.");
        }
    }

    /**
     * Validates User login credentials
     * */

    public User validateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Creates and returns user obj if the credentials match
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        }
        // Null if no match is found
        return null;
    }
}
