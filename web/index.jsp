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
<a href="admin_index.jsp">admin</a><br/>
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
                <c:url value="topic_chat_info.do" var="topicUrl">
                    <c:param name="topicId" value="${topic.topicId}"/>
                </c:url>
                <tr>
                    <td><a href="${topicUrl}">${topic.title}</a></td>
                    <td>${topic.description}</td>
                    <td>${topic.isPrivate}</td>
                    <td>${topic.replyAccount}</td>
                    <td>${topic.lastTime}</td>
                    <td>${topic.created}</td>
                    <c:if test="${sessionScope.access.isUserInRole('admin')}">
                        <c:url value="topic_delete.do" var="deleteUrl">
                            <c:param name="topicId" value="${topic.topicId}"/>
                        </c:url>
                        <c:url value="topic_update_info.do" var="updateUrl">
                            <c:param name="topicId" value="${topic.topicId}"/>
                        </c:url>
                        <td><a href="${deleteUrl}">删除</a></td>
                        <td><a href="${updateUrl}">修改</a></td>
                    </c:if>
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
