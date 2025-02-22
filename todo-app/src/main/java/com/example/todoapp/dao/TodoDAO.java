package com.example.todoapp.dao;
import com.example.todoapp.model.Todo;
import com.example.todoapp.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling todo-related DB operations.
 * */
public class TodoDAO {

    /**
     * Adds a new todo to the DB
     * */
    public void addTodo(Todo todo) throws SQLException {
        String sql = "INSERT INTO todos (user_id, title, description, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, todo.getUserId());
            ps.setString(2, todo.getTitle());
            ps.setString(3, todo.getDescription());
            ps.setString(4, todo.getStatus());

            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all the todos in the DB for a specific user.
     * */
    public List<Todo> getAllTodosByUser(int userId) throws SQLException {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create todo object for each db record
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setUserId(rs.getInt("user_id"));
                todo.setTitle(rs.getString("title"));
                todo.setDescription(rs.getString("description"));
                todo.setStatus(rs.getString("status"));
                todo.setDateCreated(rs.getTimestamp("created_at"));
                todos.add(todo);

            }
        }
        return todos;
    }

    /**
     * Updates an existing todo
     * */
    public void updateTodo(Todo todo) throws SQLException {
        String sql = "UPDATE todos SET description = ?, status = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, todo.getDescription());
            pstmt.setString(2, todo.getStatus());
            pstmt.setInt(3, todo.getId());
            pstmt.setInt(4, todo.getUserId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a todo from the DB.
     * */
    public void deleteTodo(int todoId, int userId) throws SQLException {
        String sql = "DELETE FROM todos WHERE id = ? AND user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, todoId);
            ps.setInt(2, userId);

            ps.executeUpdate();
        }
    }

    /**
     * Retrieves a specific todo by the ID, and only if it belongs to the current user.
     * */
    public Todo getTodoById(int todoId, int userId) throws SQLException {
        String sql = "SELECT * FROM todos WHERE id = ? AND user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, todoId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTitle(rs.getString("title"));
                todo.setDescription(rs.getString("description"));
                todo.setStatus(rs.getString("status"));
                return todo;
            }
        }
        return null;
    }

}
