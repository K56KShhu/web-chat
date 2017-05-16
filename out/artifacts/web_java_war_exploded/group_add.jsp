<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>group add</title>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>group add</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="小组创建成功"/>
    <jsp:param name="faile" value="小组创建失败, 原因如下:"/>
</jsp:include>

<form method="post" action="group_add.do">
    name:<input type="text" name="name" value="${requestScope.name}"/><br/>
    description:<textarea name="description">${requestScope.description}</textarea><br/>
    <input type="submit"/>
</form>

</body>
</html>
