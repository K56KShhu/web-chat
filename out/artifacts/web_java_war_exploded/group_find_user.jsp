<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>group add user</title>
</head>
<body>

<h1>group add user</h1>

搜索用户
<form method="get" action="group_find_user.do">
    <input type="hidden" name="groupId" value="${param.groupId}"/>
    <input type="text" name="search" value="${requestScope.search}"/>
    <input type="submit"/>
</form>

<table border="1">
    <tr>
        <th>username</th>
        <th>sex</th>
        <th>status</th>
        <th>created</th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <c:url value="group_add_user.do" var="addUserUrl">
            <c:param name="groupId" value="${param.groupId}"/>
            <c:param name="userId" value="${user.userId}"/>
        </c:url>
        <tr>
            <td>${user.username}</td>
            <td>${user.sex}</td>
            <td>${user.status}</td>
            <td>${user.created}</td>
            <td><a href="${addUserUrl}">add</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
