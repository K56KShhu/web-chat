<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic public add</title>
</head>
<body>

<h1>topic public add</h1>

<jsp:include page="errors.jsp">
    <jsp:param name="success" value="讨论区创建成功"/>
    <jsp:param name="faile" value="讨论区创建失败, 原因如下:"/>
</jsp:include>

<form method="post" action="topic_add.do">
    标题: <input type="text" name="title"/><br/>
    描述: <textarea name="desc" rows="15" cols="80"></textarea><br/>
    <input type="hidden" name="isPrivate" value="false"/>
    <input type="submit" value="创建"/>
</form>

</body>
</html>
