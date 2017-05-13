<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic manage</title>
</head>
<body>

<%@ include file="/WEB-INF/header_for_admin.jsp" %>

<h1>topic manage</h1>

<a href="topic_add.jsp">add topic</a><br/>

<table border="1">
    <tr>
        <th>title</th>
        <th>creator</th>
        <th>lastModifyId</th>
        <th>isPrivate</th>
        <th>replyAccount</th>
        <th>lastTime</th>
        <th>created</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topics}">
        <%--更新讨论区信息--%>
        <c:url value="topic_update_info.do" var="topicUpdateUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <%--删除讨论区--%>
        <c:url value="topic_delete.do" var="topicDeleteUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td>${topic.title}<br/>${topic.description}</td>
            <td>${topic.creatorId}</td>
            <td>${topic.lastModifyId}</td>
            <td>${topic.isPrivate}</td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
            <td>${topic.created}</td>
            <td><a href="${topicUpdateUrl}">修改</a></td>
            <td><a href="${topicDeleteUrl}">删除</a></td>
                <%--为private讨论区增加小组访问权限--%>
            <c:if test="${topic.isPrivate == 1}">
                <c:url value="topic_find_group.jsp" var="addGroupUrl">
                    <c:param name="topicId" value="${topic.topicId}"/>
                </c:url>
                <td><a href="${addGroupUrl}">授权小组</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>


</body>
</html>
