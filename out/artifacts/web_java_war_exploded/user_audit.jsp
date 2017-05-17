<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin audit</title>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<c:if test="${requestScope.user == null}">
    <c:redirect url="user_audit_info.do"/>
</c:if>

<h1>admin audit</h1>

<table border="1">
    <tr>
        <th>username</th>
        <th>sex</th>
        <th>email</th>
        <th>created</th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <c:url value="admin_audit.do" var="approvedUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="isApproved" value="true"/>
        </c:url>
        <c:url value="admin_audit.do" var="failedUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="isApproved" value="false"/>
        </c:url>
        <tr>
            <td>${user.username}</td>
            <td>${user.sex}</td>
            <td>${user.email}</td>
            <td>${user.created}</td>
            <td><a href="${approvedUrl}">审核通过</a></td>
            <td><a href="${failedUrl}">审核不通过</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
