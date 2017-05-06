<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic private add</title>
</head>
<body>

<h1>topic private add</h1>

<form method="post" action="topic_add.do">
    标题: <input type="text" name="title"/><br/>
    描述: <textarea name="desc" rows="15" cols="80"></textarea><br/>
    <input type="hidden" name="isPrivate" value="false"/>
    <input type="submit" value="发布"/>
</form>


</body>
</html>
