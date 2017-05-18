<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>file list</title>
</head>
<body>

<%@ include file="/WEB-INF/header_user.jsp" %>

<c:url value="topic_chat_info.do" var="chatUrl">
    <c:param name="topicId" value="${requestScope.topicId}"/>
</c:url>
<div style="text-align: right">
    <a href="${chatUrl}">返回</a>
</div>

<h1>file list</h1>

<c:url value="file_upload.do" var="uploadUrl">
    <c:param name="topicId" value="${param.topicId}"/>
    <c:param name="shareType" value="file"/>
</c:url>
<form method="post" enctype="multipart/form-data" action="${uploadUrl}">
    file: <input type="file" name="uploadFile"/><br/>
    <input type="submit" value="upload your file"/>
</form>
<br/><br/>

<table border="1">
    <tr>
        <th>short name</th>
        <th>username</th>
        <th>created</th>
    </tr>
    <c:forEach var="file" items="${requestScope.pageBean.list}">
        <%--举报文件URL--%>
        <c:url value="report_add.jsp" var="reportUrl">
            <c:param name="contentType" value="file"/>
            <c:param name="contentId" value="${file.fileId}"/>
        </c:url>
        <%--下载文件URL--%>
        <c:url value="file_download.do" var="downUrl">
            <c:param name="relativePath" value="${file.path}"/>
        </c:url>
        <tr>
            <td><c:out value="${file.shortName}"/></td>
            <td><c:out value="${file.username}"/></td>
            <td><c:out value="${file.created}"/></td>
            <td><a href="${downUrl}">下载</a></td>
            <td><a href="${reportUrl}">举报</a></td>
                <%--[管理员]删除文件--%>
            <c:if test="${sessionScope.access.isUserInRole('admin')}">
                <c:url value="file_delete.do" var="deleteUrl">
                    <c:param name="fileId" value="${file.fileId}"/>
                </c:url>
                <td><a href="${deleteUrl}">删除</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<%--分页系统--%>
<c:set var="myTotalIndex" value="11" scope="page"/>
<c:set var="myQueryUrl" value="file_list.do" scope="page"/>
<c:set var="myPageBean" value="${requestScope.pageBean}" scope="page"/>

<%--首页, 上一页, 下一页, 尾页设置--%>
<c:url value="${pageScope.myQueryUrl}" var="firstPageUrl">
    <c:param name="page" value="1"/>
    <c:param name="topicId" value="${requestScope.topicId}"/>
    <c:param name="shareType" value="${requestScope.shareType}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="previousPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage - 1}"/>
    <c:param name="topicId" value="${requestScope.topicId}"/>
    <c:param name="shareType" value="${requestScope.shareType}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="nextPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage + 1}"/>
    <c:param name="topicId" value="${requestScope.topicId}"/>
    <c:param name="shareType" value="${requestScope.shareType}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="lastPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.totalPage}"/>
    <c:param name="topicId" value="${requestScope.topicId}"/>
    <c:param name="shareType" value="${requestScope.shareType}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>

<%--中间页设置--%>
<c:set var="begin" value="1" scope="page"/>
<c:set var="end" value="${pageScope.myPageBean.totalPage}" scope="page"/>

<%--下面两个if保证: 当前页数位于中间时, 左右两边都有${pageScope.myTotalIndex / 2}个页数, 共${pageScope.myTotalIndex}个页数--%>
<c:if test="${pageScope.myPageBean.currentPage - pageScope.myTotalIndex / 2 > 0}">
    <c:set var="begin" value="${pageScope.myPageBean.currentPage - 5}"/>
</c:if>
<c:if test="${pageScope.myPageBean.currentPage + pageScope.myTotalIndex / 2 < pageScope.myPageBean.totalPage}">
    <c:set var="end" value="${pageScope.myPageBean.currentPage + pageScope.myTotalIndex / 2}"/>
</c:if>

<%--下面两个if保证: 当前页数即使临近边界, 也能显示${pageScope.myTotalIndex}个页数--%>
<c:if test="${pageScope.myPageBean.currentPage - pageScope.myTotalIndex / 2 + 1 < 0}">
    <c:set var="begin" value="1"/>
    <%--判断给定的最大索引是否有效--%>
    <c:choose>
        <%--最大索引无效--%>
        <c:when test="${pageScope.myPageBean.totalPage < pageScope.myTotalIndex}">
            <c:set var="end" value="${pageScope.myPageBean.totalPage}"/>
        </c:when>
        <%--最大索引有效--%>
        <c:otherwise>
            <c:set var="end" value="${pageScope.myTotalIndex}"/>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${pageScope.myPageBean.currentPage + pageScope.myTotalIndex / 2 > pageScope.myPageBean.totalPage}">
    <%--判断给定的最大索引是否有效--%>
    <c:choose>
        <%--最大索引无效--%>
        <c:when test="${pageScope.myPageBean.totalPage - pageScope.myTotalIndex < 0}">
            <c:set var="begin" value="1"/>
        </c:when>
        <%--最大索引有效--%>
        <c:otherwise>
            <c:set var="begin" value="${pageScope.myPageBean.totalPage - pageScope.myTotalIndex}"/>
        </c:otherwise>
    </c:choose>
    <c:set var="end" value="${pageScope.myPageBean.totalPage}"/>
</c:if>

<%--显示--%>
<a href="${firstPageUrl}">首页</a>
<a href="${previousPageUrl}">上一页</a>
<c:forEach var="number" begin="${pageScope.begin}" end="${pageScope.end}">
    <c:url value="${pageScope.myQueryUrl}" var="indexPageUrl">
        <c:param name="page" value="${number}"/>
        <c:param name="topicId" value="${requestScope.topicId}"/>
        <c:param name="shareType" value="${requestScope.shareType}"/>
        <c:param name="order" value="${requestScope.order}"/>
        <c:param name="isReverse" value="${requestScope.isReverse}"/>
    </c:url>
    <c:choose>
        <c:when test="${number == pageScope.myPageBean.currentPage}">
            [${number}]&nbsp;
        </c:when>
        <c:otherwise>
            <a href="${indexPageUrl}">${number}</a>&nbsp;
        </c:otherwise>
    </c:choose>
</c:forEach>
<a href="${nextPageUrl}">下一页</a>
<a href="${lastPageUrl}">最后一页</a>
<%--
<table border="1">
    <tr>
        <th>filename</th>
        <th>shortname</th>
    </tr>
    <c:forEach var="file" items="${requestScope.files}">
        <tr>
            <td>${file.key}</td>
            <td>${file.value}</td>
        </tr>
    </c:forEach>
</table>
--%>

<%--
<table border="1">
    <tr>
        <th>filename</th>
        <th>short filename</th>
    </tr>
    <c:forEach var="file" items="${requestScope.files}">
        <tr>
            <c:url value="file_download.do" var="downUrl">
                <c:param name="relativePath" value="${file.key}"/>
            </c:url>
            <td>${file.key}</td>
            <td>${file.value}</td>
            <td><a href="${downUrl}">下载</a></td>
        </tr>
    </c:forEach>
</table>
--%>

<%--    *error*
<c:forEach var="file" items="${requestScope.files}">
    <tr>
        <td><a href="file_download.do?filename=${file.key}">${file.key}</a></td>
        <td>${file.value}</td>
    </tr>
</c:forEach>
--%>

<%--    *work*
<%
    Map<String, String> files = (Map<String, String>) request.getAttribute("files");
    if (files != null) {
        for (Map.Entry<String, String> file : files.entrySet()) {
            String downUrl = java.net.URLEncoder.encode(file.getKey());
%>
<tr>
    <td><%= file.getKey() %>
    </td>
    <td><%= file.getValue() %>
    </td>
    <td><a href="file_download.do?filename=<%= downUrl%>">下载</a></td>
</tr>
<%
        }
    }
%>
--%>
<%--</table>--%>

</body>
</html>
