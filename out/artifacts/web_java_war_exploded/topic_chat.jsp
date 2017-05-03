<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic chat</title>
</head>
<body>

<c:choose>
    <c:when test="${requestScope.topic != null}">
        <h1>${requestScope.topic.title}</h1>
        <p>${requestScope.topic.description}</p>
    </c:when>
    <c:otherwise>
        <c:redirect url="index.jsp"/>
    </c:otherwise>
</c:choose>

</body>
</html>
