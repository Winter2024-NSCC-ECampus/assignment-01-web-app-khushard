# JSP Todo Application
A simple Todo application build using JSP, Servlets and MySQL, showing basic CRUD operations and user authentication.

## Features
- User Registration and Login
- Create, Read, Update, and Delete Todos
- User based Todo lists
- Status tracking for Todos

## Technical Stack
- Java Servlets
- JSP
- MySQL Database
- JDBC
- Tomcat Server

## Key Classes

### Models 
- User.java: User data model with id, username, and password.
- Todo.java: Todo item model with id, title, description and status

### Data Access Objects (DAOs)
- UserDAO.java: Handles user related database operations
- TodoDAO.java: Manages todo CRUD operations

### Servlets
- UserServlet.java: Handles authentication and user management
- TodoServlet.java: Manages todo operations.

## Database Schema
![image](https://github.com/user-attachments/assets/230c44a6-5d98-4164-9e3e-65bd3e0eb4cb)

## Setup Instructions
1. Install MySQL and create a database named "todo_app"
2. Run the SQL scripts to create the required tables
3. Configure the database connection in DatabaseUtil.java
4. Deploy on Tomcat server
5. Access via http://localhost:8080/todo-app/
