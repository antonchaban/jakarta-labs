<%--
  Created by IntelliJ IDEA.
  User: anton.chaban
  Date: 06.04.2025
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error</title>
    <%@include file="style.jspf"%>
</head>
<body>
<%@include file="header.jspf"%>
<section>
    <h1>${message}</h1>
    <a href=".">Go to main page</a>
</section>
<br>
<%@include file="footer.jspf"%>
</body>
</html>