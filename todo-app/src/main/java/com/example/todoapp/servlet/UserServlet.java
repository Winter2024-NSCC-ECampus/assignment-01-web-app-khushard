package com.example.todoapp.servlet;
import com.example.todoapp.dao.UserDAO;
import com.example.todoapp.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet handling all user authentication related operations, such as login, registration, and logout.
 * */
@WebServlet("/auth/*")
public class UserServlet extends HttpServlet {
    // Database access object for database operations.
    private UserDAO userDAO = new UserDAO();


    /**
     * Handles GET requests for authentication pages:
     * - /auth/login: Shows the login page
     * - /auth/register: Shows the registration page
     * - /auth/logout: Handles user logout
     * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/login":
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;
            case "/register":
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case "/logout":
                request.getSession().invalidate(); // Clears user session
                response.sendRedirect(request.getContextPath() + "/auth/login");
                break;
        }
    }

    /**
     * Handles POST requests for authentication:
     * - /auth/login: Processes the login form
     * - /auth/register: Processes the registration form
    * */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        switch(pathInfo) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
        }
    }

    /**
     * Handles user login:
     * 1. Get username and password from request
     * 2. Validates credentials with the DB
     * 3. Creates a session if valid.
     * 4. Shows error if invalid.
     * */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Get the login form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Validate user info
            User user = userDAO.validateUser(username, password);
            if (user != null) {
                // Create session and store the user info
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/todo/list");
            } else {
                // Show error on invalid credentials
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Handles user registration process
     * 1. Gets new user details from registration form
     * 2. Attempts to create new user in the DB
     * 3. Redirects to login page when successful
     * 4. Shows error message on failure
     * */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the registration form info
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Create and save the new user in the DB
            User user = new User(username, password);
            userDAO.registerUser(user);
            // If successful, redirect to login
            response.sendRedirect(request.getContextPath() + "/auth/login");

        } catch (SQLException e) {

            // Check if it's a duplicate username error
            if (e.getMessage().contains("duplicate") || e.getMessage().contains("Duplicate")) {
                request.setAttribute("error", "Username already exists");
            } else {
                // Generic error message for SQL issues
                request.setAttribute("error", "Error during registration: " + e.getMessage());
            }
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

}
