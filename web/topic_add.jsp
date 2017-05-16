<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic add</title>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>topic add</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="讨论区创建成功"/>
    <jsp:param name="faile" value="讨论区创建失败, 原因如下:"/>
</jsp:include>

<form method="post" action="topic_add.do">
    标题: <input type="text" name="title" value="${requestScope.title}"/><br/>
    描述: <textarea name="description" rows="15" cols="80">${requestScope.description}</textarea><br/>
    <input type="radio" name="type" value="public" ${requestScope.type == "public" ? "checked" : ""}>public
    <input type="radio" name="type" value="private" ${requestScope.type == "private" ? "checked" : ""}>private
    <br/>
    <input type="submit" value="发布"/>
</form>

</body>
</html>
