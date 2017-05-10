<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user manage</title>
</head>
<body>

<h1>user manage</h1>

<form method="get" action="user_manage_info.do">
    搜索用户: <input type="text" name="search"/>
    <input type="submit"/>
</form>

<table border="1">
    <tr>
        <th>username</th>
        <th>sex</th>
        <th>email</th>
        <th>status</th>
        <th>created</th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <c:url value="user_manage.do" var="userNormalUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="status" value="1"/>
        </c:url>
        <c:url value="user_manage.do" var="userNotApprovedUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="status" value="-1"/>
        </c:url>
        <c:url value="user_manage.do" var="userForbiddenUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="status" value="-2"/>
        </c:url>
        <tr>
            <td>${user.username}</td>
            <td>${user.sex}</td>
            <td>${user.email}</td>
            <td>${user.status}</td>
            <td>${user.created}</td>
            <c:if test="${user.status == 1}">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><a href="${userForbiddenUrl}">封印</a></td>
                <td>&nbsp;</td>
            </c:if>
            <c:if test="${user.status == 0}">
                <td><a href="${userNotApprovedUrl}">审核不通过</a></td>
                <td><a href="${userNormalUrl}">审核通过</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </c:if>
            <c:if test="${user.status == -1}">
                <td>&nbsp;</td>
                <td><a href="${userNormalUrl}">审核通过</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </c:if>
            <c:if test="${user.status == -2}">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><a href="${userNormalUrl}">解除封印</a></td>
            </c:if>
        </tr>
    </c:forEach>

</table>

</body>
</html>
