<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic private</title>
</head>
<body>

<%@ include file="/WEB-INF/header.jsp" %>

<h1>授权讨论区</h1>

<a href="topic_find.do">进入公共讨论区</a>

<table border="1">
    <tr>
        <th>title</th>
        <th>reply</th>
        <th>last</th>
        <th>created</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topics}">
        <c:url value="topic_chat_info.do" var="topicUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td><a href="${topicUrl}">${topic.title}</a><br/>${topic.description}</td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
            <td>${topic.created}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
