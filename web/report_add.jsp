<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>report add</title>
</head>
<body>

<h1>report add</h1>

<form method="post" action="report_add.do">
    举报原因: <textarea name="reason">${requestScope.reason}</textarea>
    <input type="hidden" name="contentId" value="${param.contentId}${requestScope.contentId}"/>
    <input type="hidden" name="contentType" value="${param.contentType}${requestScope.contentType}"/>
    <input type="submit" value="提交"/>
</form>

</body>
</html>
