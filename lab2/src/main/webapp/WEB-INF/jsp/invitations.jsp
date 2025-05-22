<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 16:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Invitations</title>
  <%@ include file="style.jspf" %>
</head>
<body>
<%@ include file="header.jspf" %>

<div class="invitation-container">

  <!-- Accepted Incoming -->
  <section class="inv-section">
    <h3 class="section-title">Accepted Incoming</h3>
    <c:if test="${empty acceptedIncoming}">
      <p class="empty-message">No accepted incoming invitations.</p>
    </c:if>
    <c:forEach var="inv" items="${acceptedIncoming}">
      <div class="invitation-card accepted-card">
        <p><span class="inv-label">From:</span> <span class="inv-value">${inv.sender.username}</span></p>
      </div>
    </c:forEach>
  </section>

  <!-- Pending Incoming -->
  <section class="inv-section">
    <h3 class="section-title">Pending Incoming</h3>
    <c:if test="${empty pendingIncoming}">
      <p class="empty-message">You have no incoming invitations.</p>
    </c:if>
    <c:forEach var="inv" items="${pendingIncoming}">
      <div class="invitation-card">
        <p><span class="inv-label">From:</span> <span class="inv-value">${inv.sender.username}</span></p>
        <div class="inv-actions">
          <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/accept" class="action-form">
            <input type="hidden" name="invId" value="${inv.sender.id}"/>
            <button type="submit" class="button accept-btn">Accept</button>
          </form>
          <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/delete" class="action-form">
            <input type="hidden" name="invId" value="${inv.id}"/>
            <button type="submit" class="button delete-btn">Decline</button>
          </form>
        </div>
      </div>
    </c:forEach>
  </section>

  <!-- Accepted Outgoing -->
  <section class="inv-section">
    <h3 class="section-title">Accepted Outgoing</h3>
    <c:if test="${empty acceptedOutgoing}">
      <p class="empty-message">No accepted outgoing invitations.</p>
    </c:if>
    <c:forEach var="inv" items="${acceptedOutgoing}">
      <div class="invitation-card accepted-card">
        <p><span class="inv-label">To:</span> <span class="inv-value">${inv.receiver.username}</span></p>
      </div>
    </c:forEach>
  </section>

  <!-- Pending Outgoing -->
  <section class="inv-section">
    <h3 class="section-title">Pending Outgoing</h3>
    <c:if test="${empty pendingOutgoing}">
      <p class="empty-message">You have no outgoing invitations.</p>
    </c:if>
    <c:forEach var="inv" items="${pendingOutgoing}">
      <div class="invitation-card">
        <p><span class="inv-label">To:</span> <span class="inv-value">${inv.receiver.username}</span></p>
        <div class="inv-actions">
          <form method="post" action="${pageContext.request.contextPath}/date-app/invitation/delete" class="action-form">
            <input type="hidden" name="invId" value="${inv.id}"/>
            <button type="submit" class="button cancel-btn">Cancel</button>
          </form>
        </div>
      </div>
    </c:forEach>
  </section>

</div>

<%@ include file="footer.jspf" %>
</body>
</html>