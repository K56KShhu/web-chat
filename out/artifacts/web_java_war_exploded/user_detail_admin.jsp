<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.user.username}的档案</title>
</head>
<body>

<h1>${requestScope.user.username}的档案</h1>

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
    <tr>
        <th>status</th>
        <td>${requestScope.user.status}</td>
    </tr>
    <tr>
        <th>created</th>
        <td>${requestScope.user.created}</td>
    </tr>
</table>

<h2>group info</h2>
<c:forEach var="group" items="${requestScope.groups}">
    <table border="1">
        <tr>
            <th>name</th>
            <td>${group.key.name}</td>
        </tr>
        <tr>
            <th>description</th>
            <td>${group.key.description}</td>
        </tr>
        <tr>
            <th>access</th>
            <td>
                <table border="1">
                    <tr>
                        <th>title</th>
                        <th>created</th>
                    </tr>
                    <c:forEach var="topic" items="${group.value}">
                        <tr>
                            <td>${topic.title}</td>
                            <td>${topic.created}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>
    <br/>
</c:forEach>
</table>
</body>
</html>
