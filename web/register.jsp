<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
</head>
<body>

<%--
<c:choose>
    <c:when test="${requestScope.errors != null && !requestScope.errors.isEmpty()}">
        注册失败<br/>
        <c:forEach var="error" items="${requestScope.errors}">
            <c:out value="${error}"/><br/>
        </c:forEach>
    </c:when>
    <c:when test="${requestScope.errors != null && requestScope.errors.isEmpty()}">
        注册成功, 请等待管理员审核<br/>
    </c:when>
</c:choose>
--%>

<jsp:include page="errors.jsp"/>
<c:if test="${requestScope.errors != null && requestScope.errors.isEmpty()}">
    注册成功, 请等待管理员审核<br/>
</c:if>

<form method="post" action="register.do">
    username: <input type="text" name="username" value="${requestScope.username != null ? requestScope.username : ""}"/><br/>
    password: <input type="password" name="password"/><br/>
    confirmed password: <input type="password" name="confirmedPsw"/><br/>
    sex: <input type="radio" name="sex" value="male" ${requestScope.sex == "male" ? "checked" : ""}/>male
    <input type="radio" name="sex" value="female" ${requestScope.sex == "female" ? "checked" : ""}/>female
    <input type="radio" name="sex" value="secret" ${requestScope.sex == "secret" ? "checked" : ""}/>secret<br/>
    email: <input type="text" name="email" value="${requestScope.email != null ? requestScope.email : ""}"/><br/>
    <input type="submit" value="submit"/>
</form>

</body>
</html>
