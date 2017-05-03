<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>index</title>
</head>
<body>

<h1>index</h1>
<a href="logout.do">logout</a><br/>
<a href="user_update_info.do">user update</a><br/>
<c:choose>
    <c:when test="${requestScope.topics != null}">
        <table border="1">
            <tr>
                <th>title</th>
                <th>desc</th>
                <th>isPrivate</th>
                <th>replyAccount</th>
                <th>lastTime</th>
                <th>created</th>
            </tr>
            <c:forEach var="topic" items="${requestScope.topics}">
                <tr>
                    <td><a href="topic_chat_info.do?topicId=${topic.topicId}">${topic.title}</a></td>
                    <td>${topic.description}</td>
                    <td>${topic.isPrivate}</td>
                    <td>${topic.replyAccount}</td>
                    <td>${topic.lastTime}</td>
                    <td>${topic.created}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <c:redirect url="topic_find.do"/>
    </c:otherwise>
</c:choose>


</body>
</html>
