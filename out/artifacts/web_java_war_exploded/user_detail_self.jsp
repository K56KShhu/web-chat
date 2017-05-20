<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的信息</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_user.jsp" %>

<div style="text-align: right">
    <a href="<c:url value='user_update_info.do'/>">修改信息</a>&nbsp;
</div>

<h1>我的信息</h1>

<h2>个人</h2>
<table border="1" align="center">
    <tr>
        <th>用户名</th>
        <td>${requestScope.user.username}</td>
    </tr>
    <tr>
        <th>性别</th>
        <td>${requestScope.user.sex}</td>
    </tr>
    <tr>
        <th>邮箱</th>
        <td>${requestScope.user.email}</td>
    </tr>
</table>

<h2>小组</h2>
<c:forEach var="group" items="${requestScope.groups}">
    <table border="1" align="center">
        <tr>
            <th>小组名</th>
            <td>${group.key.name}</td>
        </tr>
        <tr>
            <th>描述</th>
            <td>${group.key.description}</td>
        </tr>
        <tr>
            <th>被授权</th>
            <td>
                <table border="1" align="center">
                    <tr>
                        <th>title</th>
                        <th>created</th>
                    </tr>
                    <c:forEach var="topic" items="${group.value}">
                        <tr>
                            <td>${topic.title}</td>
                            <td>${topic.created}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>
    <br/>
</c:forEach>
</table>

</body>
</html>
