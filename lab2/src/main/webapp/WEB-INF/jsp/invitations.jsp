<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="https://jakarta.ee/tags/core" %>
<html>
<head>
  <%@include file="style.jspf"%>
  <title>Invitations</title></head>
<body>
  <%@ include file="header.jspf" %>
  <h2>Your Invitations</h2>

  <h3>Incoming Invitations</h3>
  <c:if test="${empty incomingInvitations}">
    <p>No incoming invitations.</p>
  </c:if>
  <c:forEach var="inv" items="${incomingInvitations}">
    <div class="invitation">
      <p>From user ID: <c:out value="${inv.fromId}"/></p>
      <form method="post" action="/date-app/invitation/accept">
        <input type="hidden" name="fromId" value="${inv.fromId}"/>
        <button type="submit">Accept</button>
      </form>
        <%--    <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/delete">--%>
        <%--      <input type="hidden" name="toId" value="${sessionScope.user.id}"/>--%>
        <%--      <input type="hidden" name="fromId" value="${inv.fromId}"/>--%>
        <%--      <button type="submit">Delete</button>--%>
        <%--    </form>--%>
    </div>
  </c:forEach>

  <h3>Outgoing Invitations</h3>
  <c:if test="${empty outgoingInvitations}">
    <p>No outgoing invitations.</p>
  </c:if>
  <%--<c:forEach var="inv" items="${outgoingInvitations}">--%>
  <%--  <div class="invitation">--%>
  <%--    <p>To user ID: <c:out value="${inv.toId}"/></p>--%>
  <%--    <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/delete">--%>
  <%--      <input type="hidden" name="toId" value="${inv.toId}"/>--%>
  <%--      <input type="hidden" name="fromId" value="${sessionScope.user.id}"/>--%>
  <%--      <button type="submit">Cancel Invitation</button>--%>
  <%--    </form>--%>
  <%--  </div>--%>
  <%--</c:forEach>--%>
</body>
</html>

<%@ include file="footer.jspf" %>

