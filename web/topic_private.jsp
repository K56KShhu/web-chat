<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>授权讨论区</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_user.jsp" %>

<div style="text-align: right">
    <a href="topic_find.do">进入公共讨论区</a>&nbsp;
</div>

<h1>授权讨论区</h1>

<table border="1" align="center">
    <tr>
        <th>讨论区</th>
        <th>回复数</th>
        <th>最后回复时间</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topics}">
        <c:url value="topic_chat_info.do" var="topicUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td><a href="${topicUrl}">${topic.title}</a><br/></td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
