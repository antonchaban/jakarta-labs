<%--
  Created by IntelliJ IDEA.
  User: anton.chaban
  Date: 06.04.2025
  Time: 20:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Profiles</title>
    <%@ include file="style.jspf" %>
</head>
<body>
<%@ include file="header.jspf" %>

<!-- Profiles Grid Container -->
<div class="profiles-container">
    <c:if test="${empty profiles}">
        <p class="no-profiles">No profiles found.</p>
    </c:if>

    <c:forEach var="profile" items="${profiles}">
        <div class="profile-card">
            <h2>
                <a href="${pageContext.request.contextPath}/date-app/profile?id=${profile.id}">
                    <c:out value="${profile.username}"/>
                </a>
            </h2>
            <hr/>
            <ul class="requirements">
                <li>
                    Age: <span><c:out value="${profile.publicInfo.age}"/> year(s)</span>
                </li>
                <li class="description">
                    <c:out value="${profile.publicInfo.bio}"/>
                </li>
            </ul>
            <div class="more">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/date-app/profile?id=${profile.id}">View Profile</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/date-app/login">Login to View</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="footer.jspf" %>
</body>
</html>
