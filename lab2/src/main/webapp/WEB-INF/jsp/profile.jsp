<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jspf" %>

<div class="profile-container">
  <h2>Profile of <c:out value="${profile.username}"/></h2>
  <ul>
    <li>Age: <c:out value="${profile.publicInfo.age}"/></li>
    <li>Description: <c:out value="${profile.publicInfo.description}"/></li>
  </ul>

  <c:choose>
    <c:when test="${not empty sessionScope.user and sessionScope.user.id ne profile.id}">
      <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/send">
        <input type="hidden" name="toId" value="${profile.id}"/>
        <button type="submit">Send Invitation</button>
      </form>
    </c:when>
    <c:when test="${not empty sessionScope.user and sessionScope.user.id eq profile.id}">
      <a href="profile-edit.jsp?id=${profile.id}">Edit Your Profile</a>
      <p>
        <a href="${pageContext.request.contextPath}/date-app/invitation/inbox">View Your Invitations</a>
      </p>
    </c:when>
    <c:otherwise>
      <p><a href="login.jsp">Login to send invitation</a></p>
    </c:otherwise>
  </c:choose>
</div>

<%@ include file="footer.jspf" %>