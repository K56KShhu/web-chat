<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>img list</title>
</head>
<body>

<h1>img list</h1>
<c:url value="file_upload.do" var="uploadUrl">
    <c:param name="topicId" value="${param.topicId}"/>
    <c:param name="shareType" value="image"/>
</c:url>
<form method="post" enctype="multipart/form-data" action="${uploadUrl}">
    file: <input type="file" name="uploadFile"/><br/>
    <input type="submit" value="upload your image"/>
</form>
<br/><br/>

<table border="1">
    <tr>
        <th>author</th>
        <th>image</th>
    </tr>
    <c:forEach var="image" items="${requestScope.images}">
        <tr>
            <td>${image.value.userId}</td>
            <td><img src="${image.key}"></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
