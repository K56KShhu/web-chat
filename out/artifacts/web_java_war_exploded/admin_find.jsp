<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin find</title>
</head>
<body>

<h1>admin find</h1>

<form method="get" action="admin_find.do">
    完整加冕者的完整用户名: <input type="text" name="username"/>
    <input type="submit"/>
</form>

<c:if test="${requestScope.user != null}">
    查询结果如下, 如若批准, 请输入root密码进行验证:
    <table border="1">
        <tr>
            <th>username</th>
            <td>${requestScope.user.username}</td>
        </tr>
        <tr>
            <th>sex</th>
            <td>${requestScope.user.sex}</td>
        </tr>
        <tr>
            <th>email</th>
            <td>${requestScope.user.email}</td>
        </tr>
        <tr>
            <th>status</th>
            <td>${requestScope.user.statusStr}</td>
        </tr>
        <tr>
            <th>created</th>
            <td>${requestScope.user.created}</td>
        </tr>
    </table>

    <form method="post" action="admin_add.do">
        <input type="hidden" name="userId" value="${requestScope.user.userId}"/>
        root: <input type="password" name="root"/>
        <input type="submit"/>
    </form>
</c:if>

</body>
</html>
