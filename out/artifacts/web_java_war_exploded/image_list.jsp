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
        <th>image</th>
        <th>userId</th>
        <th>created</th>
    </tr>
    <c:forEach var="image" items="${requestScope.images}">
        <c:url value="image_show.do" var="imageUrl">
            <c:param name="relativePath" value="${image.path}"/>
        </c:url>
        <tr>
            <td><img src="${imageUrl}"></td>
            <td>${image.userId}</td>
            <td>${image.created}</td>
            <c:if test="${sessionScope.access.isUserInRole('admin')}">
                <c:url value="file_delete.do" var="deleteUrl">
                    <c:param name="fileId" value="${image.fileId}"/>
                </c:url>
                <td><a href="${deleteUrl}">删除</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

</body>
</html>
