<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 17:12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Edit Profile</title>
  <%@ include file="style.jspf" %>
</head>
<body>
<%@ include file="header.jspf" %>

<div class="edit-profile-container">
  <h2 class="edit-title">Edit Your Profile</h2>

  <form method="post"
        action="${pageContext.request.contextPath}/date-app/profile/update" class="edit-form">
    <input type="hidden" name="id" value="${profile.id}" />

    <div class="form-group">
      <label for="username" class="form-label">Username:</label>
      <input id="username" name="username" type="text"
             value="<c:out value='${profile.username}'/>" required class="form-input" />
    </div>

    <div class="form-group">
      <label for="age" class="form-label">Age:</label>
      <input id="age" name="age" type="number" min="0"
             value="<c:out value='${profile.publicInfo.age}'/>" required class="form-input" />
    </div>

    <div class="form-group">
      <label for="bio" class="form-label">Bio:</label>
      <textarea id="bio" name="bio" rows="4" required class="form-textarea"><c:out value="${profile.publicInfo.bio}"/></textarea>
    </div>

    <div class="form-group">
      <label for="newPassword" class="form-label">New Password (leave blank to keep current):</label>
      <input id="newPassword" name="newPassword" type="password" class="form-input" />
    </div>

    <div class="form-actions">
      <button type="submit" class="button save-btn">Save Changes</button>
      <a href="profile?id=${profile.id}" class="button cancel-btn">Cancel</a>
    </div>
  </form>
</div>

<%@ include file="footer.jspf" %>
</body>
</html>
