<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>举报</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<h1>举报</h1>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="success" value="举报提交成功"/>
    <jsp:param name="faile" value="举报提交失败, 原因如下:"/>
</jsp:include>

<form method="post" action="report_add.do">
    举报原因: <textarea name="reason" rows="4" cols="25" maxlength="100" placeholder="(可选)">${requestScope.reason}</textarea>
    <input type="hidden" name="contentId" value="${param.contentId}${requestScope.contentId}"/>
    <input type="hidden" name="contentType" value="${param.contentType}${requestScope.contentType}"/>
    <input type="submit" value="提交"/>
</form>

</body>
</html>
