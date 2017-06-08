<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建小组</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>创建小组</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="小组创建成功"/>
    <jsp:param name="faile" value="小组创建失败, 原因如下:"/>
</jsp:include>

<form method="post" action="group_add.do">
    组名:<input type="text" maxlength="16" size="25" name="name" placeholder="请输入小组名, 创建后不可修改"
               value="${requestScope.name}"/><br/>
    描述:<textarea name="description" rows="4" cols="25" maxlength="100"
                 placeholder="(可选)">${requestScope.description}</textarea><br/>
    <input type="submit"/>
</form>

</body>
</html>
