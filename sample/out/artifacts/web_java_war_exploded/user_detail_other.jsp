<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${requestScope.user.username}"/>的个人信息</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_user.jsp" %>

<h1><c:out value="${requestScope.user.username}"/>的信息</h1>

<h2>个人</h2>
<table border="1" align="center">
    <tr>
        <th>用户名</th>
        <td>${requestScope.user.username}</td>
    </tr>
    <tr>
        <th>性别</th>
        <td>${requestScope.user.sex}</td>
    </tr>
    <tr>
        <th>邮箱</th>
        <td>${requestScope.user.email}</td>
    </tr>
</table>

</body>
</html>
