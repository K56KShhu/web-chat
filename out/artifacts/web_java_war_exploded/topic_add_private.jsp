<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic private add</title>
</head>
<body>

<h1>topic private add</h1>

<jsp:include page="result.jsp">
    <jsp:param name="success" value="讨论区创建成功"/>
    <jsp:param name="faile" value="讨论区创建失败, 原因如下:"/>
</jsp:include>

<form method="post" action="topic_add.do">
    标题: <input type="text" name="title"/><br/>
    描述: <textarea name="desc" rows="15" cols="80"></textarea><br/>
    <input type="radio" name="type" value="public">public
    <input type="radio" name="type" value="private">private
    <input type="submit" value="发布"/>
</form>


</body>
</html>
