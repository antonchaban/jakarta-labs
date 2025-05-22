<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Profile Page</title>
  <%@ include file="style.jspf" %>
</head>
<body>
<%@ include file="header.jspf" %>

<div class="profile-container">
  <h2 class="profile-title">Profile of <c:out value="${profile.username}"/></h2>

  <ul class="profile-details">
    <li><span class="detail-label">Age:</span> <span class="detail-value"><c:out value="${profile.publicInfo.age}"/></span></li>
    <li><span class="detail-label">Bio:</span> <span class="detail-value"><c:out value="${profile.publicInfo.bio}"/></span></li>
  </ul>

  <c:choose>
    <c:when test="${isOwner}">
      <div class="private-info">
        <p><span class="detail-label">Email:</span> <span class="detail-value"><c:out value="${profile.privateInfo.email}"/></span></p>
      </div>
      <div style="display: flex; justify-content: space-between; margin-top: 10px; width: 100%;">
        <a href="${pageContext.request.contextPath}/date-app/profile/edit?id=${profile.id}" class="button edit-btn">Edit Your Profile</a>
        <a href="${pageContext.request.contextPath}/date-app/invitations" class="button edit-btn">View Your Invitations</a>
      </div>
    </c:when>

    <c:when test="${invStatus == 'ACCEPTED'}">
      <div class="private-info">
        <p><span class="detail-label">Email:</span> <span class="detail-value"><c:out value="${profile.privateInfo.email}"/></span></p>
      </div>
    </c:when>

    <c:when test="${invStatus == 'PENDING'}">
      <p class="pending-label">Invitation pending</p>
    </c:when>

    <c:otherwise>
      <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/send" class="action-form">
        <input type="hidden" name="toId" value="${profile.id}"/>
        <button type="submit" class="button send-btn">Send Invitation</button>
      </form>
    </c:otherwise>
  </c:choose>

</div>

<%@ include file="footer.jspf" %>
</body>
</html>