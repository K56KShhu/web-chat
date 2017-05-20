<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>讨论区档案</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>讨论区档案</h1>

<h2>讨论区</h2>
<table border="1" align="center">
    <tr>
        <th>标题</th>
        <td>${requestScope.topic.title}</td>
    </tr>
    <tr>
        <th>描述</th>
        <td><pre>${requestScope.topic.description}</pre></td>
    </tr>
    <tr>
        <th>类型</th>
        <td>${requestScope.topic.isPrivateStr}</td>
    </tr>
    <tr>
        <th>创建者</th>
        <td>${requestScope.topic.creatorUsername}</td>
    </tr>
    <tr>
        <th>最后修改者</th>
        <td>${requestScope.topic.lastModifyUsername}</td>
    </tr>
    <tr>
        <th>回复</th>
        <td>${requestScope.topic.replyAccount}</td>
    </tr>
    <tr>
        <th>最后回复时间</th>
        <td>${requestScope.topic.lastTime}</td>
    </tr>

    <tr>
        <th>创建时间</th>
        <td>${requestScope.topic.created}</td>
    </tr>
</table>

<h2>授权小组</h2>
<table border="1" align="center">
    <tr>
        <th>小组名</th>
        <th>人数</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
        <%--小组详细信息--%>
        <c:url value="group_detail.do" var="groupDetailUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <c:url value="topic_remove_group.do" var="removeGroupUrl">
            <c:param name="groupId" value="${group.groupId}"/>
            <c:param name="topicId" value="${requestScope.topic.topicId}"/>
        </c:url>
        <tr>
            <td><a href="${groupDetailUrl}">${group.name}</a></td>
            <td>${group.population}</td>
            <td><a href="${removeGroupUrl}">移除</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
