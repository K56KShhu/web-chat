<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>group detail</title>
</head>
<body>

<h1>group detail</h1>

<c:url value="group_find_user.jsp" var="addUserUrl">
    <c:param name="groupId" value="${requestScope.group.groupId}"/>
</c:url>

<a href="${addUserUrl}">add user</a>

<h2>小组信息</h2>
<table border="1">
    <tr>
        <th>name</th>
        <td>${requestScope.group.name}</td>
    </tr>
    <tr>
        <th>population</th>
        <td>${requestScope.group.population}</td>
    </tr>
    <tr>
        <th>description</th>
        <td>${requestScope.group.description}</td>
    </tr>
    <tr>
        <th>created</th>
        <td>${requestScope.group.created}</td>
    </tr>
    <tr>
        <th>members</th>
        <td>
            <table border="1">
                <tr>
                    <th>username</th>
                    <th>sex</th>
                    <th>email</th>
                    <th>status</th>
                    <th>created</th>
                </tr>
                <c:forEach var="user" items="${requestScope.users}">
                    <c:url value="group_remove_user.do" var="removeUserUrl">
                        <c:param name="groupId" value="${requestScope.group.groupId}"/>
                        <c:param name="userId" value="${user.userId}"/>
                    </c:url>
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.sex}</td>
                        <td>${user.email}</td>
                        <td>${user.status}</td>
                        <td>${user.created}</td>
                        <td><a href="${removeUserUrl}">remove</a></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<h2>该小组用于以下讨论区</h2>
<table border="1">
    <tr>
        <th>title</th>
        <th>created</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topics}">
        <tr>
            <td>${topic.title}</td>
            <td>${topic.created}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
