<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>report manage</title>
</head>
<body>

<h1>report manage</h1>

<table border="1">
    <tr>
        <th>user_id</th>
        <th>content_id</th>
        <th>content_type</th>
        <th>reason</th>
        <th>operation</th>
        <th>result</th>
    </tr>
    <c:forEach var="report" items="${requestScope.reports}">
        <c:url value="report_detail.do" var="detailReportUrl">
            <c:param name="contentId" value="${report.contentId}"/>
            <c:param name="contentType" value="${report.contentType}"/>
        </c:url>
        <c:url value="report_delete.do" var="deleteReportUrl">
            <c:param name="reportId" value="${report.reportId}"/>
        </c:url>
        <c:url value="report_manage.do" var="manageReportUrl">
            <c:param name="contentId" value="${report.contentId}"/>
            <c:param name="contentType" value="${report.contentType}"/>
        </c:url>
        <tr>
            <td>${report.userId}</td>
            <td>${report.contentId}</td>
            <td>${report.contentType}</td>
            <td>${report.reason}</td>
            <td><a href="${detailReportUrl}">查看详情</a>&nbsp;<a href="${manageReportUrl}">删除对应内容</a></td>
            <td><a href="${deleteReportUrl}">操作完毕</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
