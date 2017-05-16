<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>report add</title>
</head>
<body>

<%@ include file="/WEB-INF/header_for_admin.jsp" %>

<h1>report add</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="举报提交成功"/>
    <jsp:param name="faile" value="举报提交失败, 原因如下:"/>
</jsp:include>

<form method="post" action="report_add.do">
    举报原因: <textarea name="reason">${requestScope.reason}</textarea>
    <input type="hidden" name="contentId" value="${param.contentId}${requestScope.contentId}"/>
    <input type="hidden" name="contentType" value="${param.contentType}${requestScope.contentType}"/>
    <input type="submit" value="提交"/>
</form>

</body>
</html>
