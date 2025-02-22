<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo List</title>
    <style>
        .container {
            width: 800px;
            margin: 20px auto;
            padding: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .todo-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .todo-table th, .todo-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .todo-table th {
            background-color: #f8f9fa;
        }
        .btn {
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-add {
            background-color: #28a745;
            color: white;
        }
        .btn-edit {
            background-color: #ffc107;
            color: #000;
        }
        .btn-delete {
            background-color: #dc3545;
            color: white;
        }
        .btn-logout {
            background-color: #6c757d;
            color: white;
        }
        .status-pending {
            color: #dc3545;
        }
        .status-completed {
            color: #28a745;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2>Welcome, ${user.username}</h2>
        <div>
            <a href="${pageContext.request.contextPath}/todo/add" class="btn btn-add">Add New Todo</a>
            <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-logout">Logout</a>
        </div>
    </div>

    <table class="todo-table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Created At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${todos}" var="todo">
            <tr>
                <td>${todo.title}</td>
                <td>${todo.description}</td>
                <td class="status-${todo.status.toLowerCase()}">${todo.status}</td>
                <td>${todo.dateCreated}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/todo/edit?id=${todo.id}"
                       class="btn btn-edit">Edit</a>
                    <form action="${pageContext.request.contextPath}/todo/delete"
                          method="post" style="display: inline;">
                        <input type="hidden" name="id" value="${todo.id}">
                        <button type="submit" class="btn btn-delete"
                                onclick="return confirm('Are you sure?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
