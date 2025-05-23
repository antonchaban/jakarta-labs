<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 17:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="style.jspf" %>
<html>
<head>
  <title>Register</title>
</head>
<body>
<%@ include file="header.jspf" %>

<div class="auth-container">
  <div class="auth-card">
    <h2 class="auth-title">Create a New Account</h2>
    <form method="post" action="${pageContext.request.contextPath}/date-app/register" class="auth-form">
      <div class="form-group">
        <label for="username" class="form-label">Username</label>
        <input type="text" id="username" name="username" required class="form-input" />
      </div>
      <div class="form-group">
        <label for="email" class="form-label">Email</label>
        <input type="email" id="email" name="email" required class="form-input" />
      </div>
      <div class="form-group">
        <label for="bio" class="form-label">Bio</label>
        <input type="text" id="bio" name="bio" class="form-input" />
      </div>
      <div class="form-group">
        <label for="age" class="form-label">Age</label>
        <input type="number" id="age" name="age" min="0" required class="form-input" />
      </div>
      <div class="form-group">
        <label for="password" class="form-label">Password</label>
        <input type="password" id="password" name="password" required class="form-input" />
      </div>
      <div class="form-actions">
        <button type="submit" class="button submit-btn">Register</button>
        <a style="text-decoration: none" href="${pageContext.request.contextPath}/date-app/login" class="button submit-btn">Login</a>
      </div>
    </form>
  </div>
</div>

<%@ include file="footer.jspf" %>
</body>
</html>
