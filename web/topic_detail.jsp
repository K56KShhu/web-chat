<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic detail</title>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>topic detail</h1>

<h2>topic info</h2>
<table border="1">
    <tr>
        <th>title</th>
        <td>${requestScope.topic.title}</td>
    </tr>
    <tr>
        <th>desc</th>
        <td>${requestScope.topic.description}</td>
    </tr>
    <tr>
        <th>type</th>
        <td>${requestScope.topic.isPrivateStr}</td>
    </tr>
    <tr>
        <th>creator</th>
        <td>${requestScope.topic.creatorUsername}</td>
    </tr>
    <tr>
        <th>modifier</th>
        <td>${requestScope.topic.lastModifyUsername}</td>
    </tr>
    <tr>
        <th>reply account</th>
        <td>${requestScope.topic.replyAccount}</td>
    </tr>
    <tr>
        <th>last reply</th>
        <td>${requestScope.topic.lastTime}</td>
    </tr>

    <tr>
        <th>created</th>
        <td>${requestScope.topic.created}</td>
    </tr>
</table>

<h2>group info</h2>
<table border="1">
    <tr>
        <th>name</th>
        <th>desc</th>
        <th>population</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
        <tr>
            <td>${group.name}</td>
            <td>${group.description}</td>
            <td>${group.population}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
