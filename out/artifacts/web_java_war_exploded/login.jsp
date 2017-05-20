<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>咱部落</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<c:url value="image_show_website.do" var="imageUrl">
    <c:param name="relativePath" value="/moon.ico"/>
</c:url>
<br/><img alt="部落格" src="${imageUrl}">

<h1>咱部落</h1>
<h2>与部落成员分享你的知识、经验和见解</h2>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="faile" value="登录失败, 原因如下:"/>
</jsp:include>

<%--<form method="post" action="login.do">--%>
<%--username: <input type="text" name="username"/><br/>--%>
<%--password: <input type="password" name="password"/><br/>--%>
<%--<input type="submit"/>--%>
<%--</form>--%>
<form method="post" action="login.do">
    username:<input type="text" name="username"/><br/>
    password:<input type="password" name="password"/><br/>
    <input type="checkbox" name="remember" value="true">自动登录
    <input type="submit"/>
</form>

<a href="register.jsp">立即注册</a>

</body>
</html>
