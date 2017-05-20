<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查找小组</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>查赵小组</h1>

<form method="get" action="topic_find_group.do">
    <input type="hidden" name="topicId" value="${param.topicId}"/>
    搜索: <input type="text" name="search" value="${requestScope.search}"/>
    <input type="submit"/>
</form>

<c:if test="${requestScope.groups != null}">
    <table border="1" align="center">
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
