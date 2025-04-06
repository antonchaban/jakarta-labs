<%--
  Created by IntelliJ IDEA.
  User: anton.chaban
  Date: 06.04.2025
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profiles</title>
    <%@include file="style.jspf" %>
</head>
<body>
<%@include file="header.jspf" %>
<c:forEach var="profile" items="${profiles}">
    <div class="job-wrapper">
        <h1><a href="profile?id=${profile.id}"><c:out value="${profile.username}"/></a></h1>
        <hr>
        <ul class="requirements">
            <li>Age: <span style="color: red"><c:out
                    value=" ${profile.publicInfo.age} year(s)"/></span>
            </li>
            <li class="description">
                <c:out value="${profile.publicInfo.description}"/>
            </li>
        </ul>
        <div class="more">
            <a href="profile?id=${profile.id}">View More</a>
        </div>

    </div>
</c:forEach>
<%@include file="footer.jspf" %>
</body>
</html>