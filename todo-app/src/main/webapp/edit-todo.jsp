<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Todo</title>
  <style>
    .container {
      width: 500px;
      margin: 50px auto;
      padding: 20px;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
    }
    .form-group input, .form-group textarea, .form-group select {
      width: 100%;
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    .btn {
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
      margin-right: 10px;
    }
    .btn-save {
      background-color: #28a745;
      color: white;
    }
    .btn-cancel {
      background-color: #6c757d;
      color: white;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Edit Todo</h2>

  <form action="${pageContext.request.contextPath}/todo/update" method="post">
    <input type="hidden" name="id" value="${todo.id}">

    <div class="form-group">
      <label for="title">Title:</label>
      <input type="text" id="title" name="title" value="${todo.title}" required>
    </div>
    <div class="form-group">
      <label for="description">Description:</label>
      <textarea id="description" name="description" rows="4">${todo.description}</textarea>
    </div>
    <div class="form-group">
      <label for="status">Status:</label>
      <select id="status" name="status">
        <option value="PENDING" ${todo.status == 'PENDING' ? 'selected' : ''}>Pending</option>
        <option value="COMPLETED" ${todo.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
      </select>
    </div>
    <button type="submit" class="btn btn-save">Save</button>
    <a href="${pageContext.request.contextPath}/todo/list" class="btn btn-cancel">Cancel</a>
  </form>
</div>
</body>
</html>