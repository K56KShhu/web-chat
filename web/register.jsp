<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<h1>注册</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="注册成功, 请等待管理员审核"/>
    <jsp:param name="faile" value="注册失败, 原因如下:"/>
</jsp:include>

<form method="post" action="register.do">
    用户名: <input type="text" name="username" value="${requestScope.username != null ? requestScope.username : ""}"/><br/>
    密码: <input type="password" name="password"/><br/>
    重复密码: <input type="password" name="confirmedPsw"/><br/>
    性别: <input type="radio" name="sex" value="male" ${requestScope.sex == "male" ? "checked" : ""}/>male
    <input type="radio" name="sex" value="female" ${requestScope.sex == "female" ? "checked" : ""}/>female
    <input type="radio" name="sex" value="secret" ${requestScope.sex == "secret" ? "checked" : ""}/>secret<br/>
    邮箱: <input type="text" name="email" value="${requestScope.email != null ? requestScope.email : ""}"/><br/>
    <input type="checkbox" name="isAgreed" value="true" checked/>阅读并接受<a href="rule.jsp">《咱部落用户协议》</a><br/>
    <input type="submit" value="submit"/>
</form>

</body>
</html>
