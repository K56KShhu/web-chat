<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>group manage</title>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>group manage</h1>

<p>
    <a href="group_add.jsp">创建小组</a>
</p>

<table border="1">
    <tr>
        <th>name</th>
        <th>description</th>
        <th>population</th>
        <th>created</th>
    </tr>
    <c:forEach var="group" items="${requestScope.groups}">
        <%--向小组中添加用户--%>
        <c:url value="group_find_user.jsp" var="addUserUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <%--小组详细信息--%>
        <c:url value="group_detail.do" var="groupDetailUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <%--解散小组--%>
        <c:url value="group_delete.do" var="groupDeleteUrl">
            <c:param name="groupId" value="${group.groupId}"/>
        </c:url>
        <tr>
            <td>${group.name}</td>
            <td>${group.description}</td>
            <td>${group.population}</td>
            <td>${group.created}</td>
            <td><a href="${addUserUrl}">add user</a></td>
            <td><a href="${groupDetailUrl}">detail</a></td>
            <td><a href="${groupDeleteUrl}">delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
