<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>file list</title>
</head>
<body>
<h1>file list</h1>
<c:url value="file_upload.do" var="uploadUrl">
    <c:param name="topicId" value="${param.topicId}"/>
    <c:param name="shareType" value="file"/>
</c:url>
<form method="post" enctype="multipart/form-data" action="${uploadUrl}">
    file: <input type="file" name="uploadFile"/><br/>
    <input type="submit" value="upload your file"/>
</form>
<br/><br/>

<%--
<table border="1">
    <tr>
        <th>filename</th>
        <th>short filename</th>
    </tr>
    <c:forEach var="file" items="${requestScope.files}">
        <tr>
            <c:url value="file_download.do" var="downurl">
                <c:param name="filename" value="${file.key}"/>
            </c:url>
            <td><a href="${downurl}">${file.key}</a></td>
            <td>${file.value}</td>
        </tr>
    </c:forEach>
    %-->

    <%--    *error*
    <c:forEach var="file" items="${requestScope.files}">
        <tr>
            <td><a href="file_download.do?filename=${file.key}">${file.key}</a></td>
            <td>${file.value}</td>
        </tr>
    </c:forEach>
    --%>

    <%--    *work*
    <%
        Map<String, String> files = (Map<String, String>) request.getAttribute("files");
        if (files != null) {
            for (Map.Entry<String, String> file : files.entrySet()) {
                String downurl = java.net.URLEncoder.encode(file.getKey());
    %>
    <tr>
        <td><%= file.getKey() %>
        </td>
        <td><%= file.getValue() %>
        </td>
        <td><a href="file_download.do?filename=<%= downurl%>">下载</a></td>
    </tr>
    <%
            }
        }
    %>
    --%>
<%--</table>--%>

</body>
</html>
