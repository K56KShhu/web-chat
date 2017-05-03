<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>user update</title>
</head>
<body>

<h1>user update</h1>
<c:choose>
    <c:when test="${requestScope.errors != null && requestScope.errors.size() == 0}">
        success<br/>
    </c:when>
    <c:otherwise>
        <c:forEach var="error" items="${requestScope.errors}">
            ${error}
        </c:forEach>
    </c:otherwise>
</c:choose>
<form method="post" action="user_update.do">
    password: <input type="password" name="password"/><br/>
    confirmed password: <input type="password" name="confirmedPsw"/><br/>
    sex: <input type="radio" name="sex" value="male" ${requestScope.user.sex == "male" ? "checked" : ""}/>male
    <input type="radio" name="sex" value="female" ${requestScope.user.sex == "female" ? "checked" : ""}/>female
    <input type="radio" name="sex" value="secret" ${requestScope.user.sex == "secret" ? "checked" : ""}/>secret<br/>
    email: <input type="text" name="email"
                  value="${requestScope.user.email != null ? requestScope.user.email : ""}"/><br/>
    <input type="submit" value="submit"/>
</form>
<br/><br/>

</body>
</html>
