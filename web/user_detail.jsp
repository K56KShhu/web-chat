<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user detail</title>
</head>
<body>

<h1>user detail</h1>

<a href="<c:url value='user_update_info.do'/>">修改信息</a>&nbsp;

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

<h2>group info</h2>
<table border="1">
    <tr>
        <th>name</th>
        <th>description</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
        <tr>
            <td>${group.name}</td>
            <td>${group.description}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
