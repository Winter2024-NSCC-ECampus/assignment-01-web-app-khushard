package com.example.todoapp.servlet;
import com.example.todoapp.dao.TodoDAO;
import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet handling all todo operations (CRUD).
 * Maps to URL "/todo/*" to handle all todo related requests.
 * Requires authenticated user by checking sessions.
 * */
@WebServlet("/todo/*")
public class TodoServlet extends HttpServlet{
    // Database access object for todo operations
    private TodoDAO todoDAO = new TodoDAO();

    /**
     * Handles GET requests for todo operations:
     * - /todo/list: Shows a list of all todos
     * - /todo/add: Shows add todo form
     * - /todo/edit: Shows edit todo form
     * First, it checks if the user is logged in.
     * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Checks for valid session, redirects to login page if not authenticated.
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String path = request.getPathInfo();

        switch (path) {
            case "/list":
                listTodos(request, response);
                break;
            case "/add":
                request.getRequestDispatcher("/add-todo.jsp").forward(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
        }
    }

    /**
     * Handles POST requests for todo operations:
     * - /todo/add: Creates a new todo
     * - /todo/update: Updates an existing todo
     * - /todo/delete: Deletes todo
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/add":
                addTodo(request, response);
                break;
            case "/update":
                updateTodo(request, response);
                break;
            case "/delete":
                deleteTodo(request, response);
                break;

        }
    }

    /**
     * Gets all todos for the logged-in user and displays them.
     * Fetches the todos from the DB and forwards them to the JSP to be displayed
     * */
    private void listTodos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try{
            // Get current user from the session
            User user = (User) request.getSession().getAttribute("user");
            // Get user's todos from database.
            List<Todo> todos = todoDAO.getAllTodosByUser(user.getId());
            // Send todos to JSP for display
            request.setAttribute("todos", todos);
            request.getRequestDispatcher("/todo-list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Creates a new todo for the current user.
     * Gets todo details from form and saves them to the DB
     * */
    private void addTodo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            // Get current user and form data.
            User user = (User) request.getSession().getAttribute("user");
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            // Create and save new tools
            Todo todo = new Todo(user.getId(), title, description);
            todoDAO.addTodo(todo);

            // Redirect to todo list.
            response.sendRedirect(request.getContextPath() + "/todo/list");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Shows edit form for the specific todo.
     * Verifies todo belongs to the current user before showing the form
     * */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            // Get the todo ID and current user
            int todoId = Integer.parseInt(request.getParameter("id"));
            User user = (User) request.getSession().getAttribute("user");

            // Get todo if it belongs to current user.
            Todo todoToEdit = todoDAO.getTodoById(todoId, user.getId());

            if (todoToEdit != null) {
                // Show edit form with todo data
                request.setAttribute("todo", todoToEdit);
                request.getRequestDispatcher("/edit-todo.jsp").forward(request, response);
            } else {
                // Todo not found or doesnt belong to user
                response.sendRedirect(request.getContextPath() + "/todo/list");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Update existing todo.
     * Verifies todo belongs to current user before updating.
     * */
    private void updateTodo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // Get current user and form data
            User user = (User) request.getSession().getAttribute("user");
            int todoId = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            // Create todo obj with updated data
            Todo todo = new Todo();
            todo.setId(todoId);
            todo.setUserId(user.getId());
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setStatus(status);

            // update the todo in the DB
            todoDAO.updateTodo(todo);
            response.sendRedirect(request.getContextPath()+ "/todo/list");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Deletes todo.
     * Verifies todo belongs to current user before deleting.
    * */
    private void deleteTodo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get current user and todo ID
            User user = (User) request.getSession().getAttribute("user");
            int todoId = Integer.parseInt(request.getParameter("id"));

            // Delete todo if it belongs to user.
            todoDAO.deleteTodo(todoId, user.getId());
            response.sendRedirect(request.getContextPath() + "/todo/list");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}
