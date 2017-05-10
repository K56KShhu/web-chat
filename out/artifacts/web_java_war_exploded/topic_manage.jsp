<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic manage</title>
</head>
<body>

<h1>topic manage</h1>

<table border="1">
    <tr>
        <th>title</th>
        <th>desc</th>
        <th>creator</th>
        <th>lastModifyId</th>
        <th>isPrivate</th>
        <th>replyAccount</th>
        <th>lastTime</th>
        <th>created</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topics}">
        <c:url value="topic_update_info.do" var="topicUpdateUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <c:url value="topic_delete.do" var="topicDeleteUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td>${topic.title}</td>
            <td>${topic.description}</td>
            <td>${topic.creatorId}</td>
            <td>${topic.lastModifyId}</td>
            <td>${topic.isPrivate}</td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
            <td>${topic.created}</td>
            <td><a href="${topicUpdateUrl}">修改</a></td>
            <td><a href="${topicDeleteUrl}">删除</a></td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
