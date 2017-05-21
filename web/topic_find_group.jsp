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

<h1>查找小组</h1>

<form method="get" action="topic_find_group.do">
    <input type="hidden" name="topicId" value="${param.topicId}"/>
    搜索小组: <input type="text" maxlength="1024" name="search" value="${requestScope.search}"/>
    <input type="submit"/>
</form>

<c:if test="${requestScope.groups != null}">
    <table border="1" align="center">
        <tr>
            <th>小组名</th>
            <th>人数</th>
            <th>创建日期</th>
        </tr>
        <c:forEach var="group" items="${requestScope.groups}">
            <%--小组详细信息--%>
            <c:url value="group_detail.do" var="groupDetailUrl">
                <c:param name="groupId" value="${group.groupId}"/>
            </c:url>
            <%--讨论区授权小组URL--%>
            <c:url value="topic_add_group.do" var="addGroupUrl">
                <c:param name="topicId" value="${param.topicId}"/>
                <c:param name="groupId" value="${group.groupId}"/>
            </c:url>
            <tr>
                <td><a href="${groupDetailUrl}">${group.name}</a></td>
                <td>${group.population}</td>
                <td>${group.created}</td>
                <td><a href="${addGroupUrl}">授权</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
