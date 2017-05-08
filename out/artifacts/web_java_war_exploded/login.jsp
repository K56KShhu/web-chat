<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>

<jsp:include page="errors.jsp"/>

<form method="post" action="login.do">
    username: <input type="text" name="username"/><br/>
    password: <input type="password" name="password"/><br/>
    <input type="submit"/>
</form>
<br/>

<a href="register.jsp">注册</a>

</body>
</html>
