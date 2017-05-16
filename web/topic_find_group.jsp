<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic find group</title>
</head>
<body>

<h1>topic find group</h1>

搜索小组:
<form method="get" action="topic_find_group.do">
    <input type="hidden" name="topicId" value="${param.topicId}"/>
    <input type="text" name="search" value="${requestScope.search}"/>
    <input type="submit"/>
</form>

<c:if test="${requestScope.groups != null}">
    <table border="1">
        <tr>
            <th>name</th>
            <th>description</th>
            <th>population</th>
            <th>created</th>
        </tr>
        <c:forEach var="group" items="${requestScope.groups}">
            <c:url value="topic_add_group.do" var="addGroupUrl">
                <c:param name="topicId" value="${param.topicId}"/>
                <c:param name="groupId" value="${group.groupId}"/>
            </c:url>
            <tr>
                <td>${group.name}</td>
                <td>${group.description}</td>
                <td>${group.population}</td>
                <td>${group.created}</td>
                <td><a href="${addGroupUrl}">add</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
