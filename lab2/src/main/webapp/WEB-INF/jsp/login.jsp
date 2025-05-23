<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 16:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="style.jspf" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<%@ include file="header.jspf" %>

<div class="auth-container">
  <div class="auth-card">
    <h2 class="auth-title">Login to DatingApp</h2>
    <form method="post" action="${pageContext.request.contextPath}/date-app/login" class="auth-form">
      <div class="form-group">
        <label for="login" class="form-label">Username</label>
        <input type="text" id="login" name="login" required class="form-input" />
      </div>
      <div class="form-group">
        <label for="password" class="form-label">Password</label>
        <input type="password" id="password" name="password" required class="form-input" />
      </div>
      <div class="form-actions">
        <a style="text-decoration: none" href="${pageContext.request.contextPath}/date-app/register" class="button submit-btn">Register</a>
        <button type="submit" class="button submit-btn">Login</button>
      </div>
    </form>
  </div>
</div>

<%@ include file="footer.jspf" %>
</body>
</html>
