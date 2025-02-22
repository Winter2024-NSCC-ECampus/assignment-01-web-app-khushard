package com.example.todoapp.model;
import java.sql.Timestamp;

public class Todo {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String status;
    private Timestamp dateCreated;

    public Todo() {}

    // Contructor for creating a new todo
    public Todo(int userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = "PENDING";
    }

    // Getters/setters for the todo properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
