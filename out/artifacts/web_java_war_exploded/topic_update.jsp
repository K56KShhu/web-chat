<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新讨论区</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>更新讨论区</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="讨论区更新成功"/>
    <jsp:param name="faile" value="讨论区更新失败, 原因如下:"/>
</jsp:include>

<form method="post" action="topic_update.do">
    标题: <input type="text" maxlength="60" name="title" value="${requestScope.topic.title}"/><br/>
    描述: <textarea name="desc" rows="15" cols="80" maxlength="255">${requestScope.topic.description}</textarea><br/>
    <input type="hidden" name="topicId" value="${requestScope.topic.topicId}"/>
    <input type="submit" value="提交修改"/>
</form>

</body>
</html>
