<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic chat</title>
</head>
<body>

<c:choose>
    <c:when test="${requestScope.topic != null}">
        <%--讨论区的图片分享和文件分享--%>
        <c:url value="file_list.do" var="fileUrl">
            <c:param name="topicId" value="${requestScope.topic.topicId}"/>
            <c:param name="shareType" value="file"/>
        </c:url>
        <c:url value="file_list.do" var="imageUrl">
            <c:param name="topicId" value="${requestScope.topic.topicId}"/>
            <c:param name="shareType" value="image"/>
        </c:url>
        <a href="${imageUrl}">图片分享区</a>
        <a href="${fileUrl}">文件分享区</a>

        <%--主题信息--%>
        <h1><c:out value="${requestScope.topic.title}"/></h1>
        <p><c:out value="${requestScope.topic.description}"/></p>

        <%--输入文本内容--%>
        <form method="post" action="reply_add.do">
            text:<br/>
            <textarea name="content" rows="5" cols="60"></textarea>
            <input type="hidden" name="topicId" value="${requestScope.topic.topicId}"/>
            <input type="submit" value="reply"/>
        </form>

        <%--输入图片内容--%>
        <c:url value="file_upload.do" var="uploadUrl">
            <c:param name="topicId" value="${requestScope.topic.topicId}"/>
            <c:param name="shareType" value="chat"/>
        </c:url>
        <form method="post" enctype="multipart/form-data" action="${uploadUrl}">
            file: <input type="file" name="uploadFile"/><br/>
            <input type="submit" value="发送图片"/>
        </form>
        <br/><br/>

        <%--聊天信息--%>
        <c:forEach var="reply" items="${requestScope.replys}">
            ${reply.created}&nbsp;${reply.userId}<br/>
            <c:out value="${reply.content}"/><br/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:redirect url="index.jsp"/>
    </c:otherwise>
</c:choose>

</body>
</html>
