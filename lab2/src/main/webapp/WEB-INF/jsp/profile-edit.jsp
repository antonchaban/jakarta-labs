<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="https://jakarta.ee/tags/core" %>

<html>
<head>
  <title>Edit Profile</title>
  <%@ include file="style.jspf" %>
</head>
<body>
<%@ include file="header.jspf" %>

<div class="edit-profile-container">
  <h2>Edit Your Profile</h2>

  <form method="post"
        action="${pageContext.request.contextPath}/date-app/profile/update">
    <input type="hidden" name="id"
           value="${profile.id}" />

    <div class="form-group">
      <label for="username">Username:</label>
      <input id="username" name="username" type="text"
             value="<c:out value='${profile.username}'/>"
             required />
    </div>

    <div class="form-group">
      <label for="age">Age:</label>
      <input id="age" name="age" type="number" min="0"
             value="<c:out value='${profile.publicInfo.age}'/>"
             required />
    </div>

    <div class="form-group">
      <label for="bio">Bio:</label>
      <textarea id="bio" name="description"
                rows="4" cols="50"
                required><c:out value="${profile.publicInfo.bio}"/></textarea>
    </div>

    <div class="form-group">
      <label for="newPassword">New Password (leave blank to keep current):</label>
      <input id="newPassword" name="newPassword"
             type="password" />
    </div>

    <div class="form-actions">
      <button type="submit">Save Changes</button>
      <a href="profile?id=${profile.id}">
        Cancel
      </a>
    </div>
  </form>
</div>

<%@ include file="footer.jspf" %>
</body>
</html>