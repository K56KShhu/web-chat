<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小组管理</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<div style="text-align: right">
    <a href="group_add.jsp">创建小组</a>&nbsp;
</div>

<h1>小组管理</h1>

<table border="1" align="center">
    <tr>
        <th>小组名</th>
        <th>人数</th>
        <th>创建时间</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
        <%--小组详细信息--%>
        <c:url value="group_detail.do" var="groupDetailUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <%--向小组中添加用户--%>
        <c:url value="group_find_user.jsp" var="addUserUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <%--解散小组--%>
        <c:url value="group_delete.do" var="groupDeleteUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <tr>
            <td>${group.name}</td>
            <td>${group.population}</td>
            <td>${group.created}</td>
            <td><a href="${groupDetailUrl}">档案</a></td>
            <td><a href="${addUserUrl}">添加成员</a></td>
            <td><a href="${groupDeleteUrl}">解散</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
