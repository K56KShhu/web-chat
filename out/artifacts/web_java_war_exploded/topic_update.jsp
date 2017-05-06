<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic update</title>
</head>
<body>

<h1>topic update</h1>

<c:choose>
    <c:when test="${requestScope.errors != null && !requestScope.errors.isEmpty()}">
        更新失败:<br/>
        <c:forEach var="error" items="${requestScope.errors}">
            <c:out value="${error}"/><br/>
        </c:forEach>
    </c:when>
    <c:when test="${requestScope.errors != null && requestScope.errors.isEmpty()}">
        更新成功
    </c:when>
</c:choose>

<form method="post" action="topic_update.do">
    标题: <input type="text" name="title" value="${requestScope.topic.title}"/><br/>
    描述: <textarea name="desc" rows="15" cols="80">${requestScope.topic.description}</textarea><br/>
    <input type="hidden" name="topicId" value="${requestScope.topic.topicId}"/>
    <input type="submit" value="提交修改"/>
</form>

</body>
</html>
