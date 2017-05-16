<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${requestScope.user.username}"/>的个人信息</title>
</head>
<body>

<%@ include file="/WEB-INF/header_user.jsp" %>

<h1><c:out value="${requestScope.user.username}"/>的个人信息</h1>

<h2>user info</h2>
<table border="1">
    <tr>
        <th>username</th>
        <td>${requestScope.user.username}</td>
    </tr>
    <tr>
        <th>sex</th>
        <td>${requestScope.user.sex}</td>
    </tr>
    <tr>
        <th>email</th>
        <td>${requestScope.user.email}</td>
    </tr>
</table>

</body>
</html>
