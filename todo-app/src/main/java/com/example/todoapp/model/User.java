package com.example.todoapp.model;

public class User {

    private int id;
    private String username;

    // Constructor for creating a new user
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        this.username = username;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;




}
