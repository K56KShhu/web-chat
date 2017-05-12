<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>group manage</title>
</head>
<body>

<h1>group manage</h1>

<table border="1">
    <tr>
        <th>name</th>
        <th>description</th>
        <th>population</th>
        <th>created</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
        <c:url value="group_detail.do" var="groupDetailUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <tr>
            <td>${group.name}</td>
            <td>${group.description}</td>
            <td>${group.population}</td>
            <td>${group.created}</td>
            <td><a href="${groupDetailUrl}">detail</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
