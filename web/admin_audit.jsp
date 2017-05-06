<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin audit</title>
</head>
<body>

<h1>admin audit</h1>

<c:choose>
    <c:when test="${requestScope.users != null}">
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
                    <td><a href="${approvedUrl}">通过</a></td>
                    <td><a href="${failedUrl}">不通过</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <c:redirect url="admin_audit_info.do"/>
    </c:otherwise>
</c:choose>


</body>
</html>
