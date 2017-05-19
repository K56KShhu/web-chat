<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>topic chat</title>
</head>
<body>

<c:if test="${requestScope.topic == null}">
    <c:redirect url="topic_find.do"/>
</c:if>

<%@ include file="/WEB-INF/header_user.jsp" %>

<%--讨论区的图片分享和文件分享--%>
<c:url value="file_list.do" var="fileUrl">
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
    <c:param name="shareType" value="file"/>
</c:url>
<c:url value="file_list.do" var="imageUrl">
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
    <c:param name="shareType" value="image"/>
</c:url>

<div style="text-align: right">
    <a href="${imageUrl}">图片分享区</a>&nbsp;<br/>
    <a href="${fileUrl}">文件分享区</a>&nbsp;
</div>

<jsp:include page="WEB-INF/result.jsp">
    <jsp:param name="faile" value="发送失败, 原因如下:"/>
</jsp:include>

<%--主题信息--%>
<h1><c:out value="${requestScope.topic.title}"/></h1>
<p><c:out value="${requestScope.topic.description}"/></p>

<%--输入文本内容--%>
<form method="post" action="reply_text_add.do">
    文本:<textarea name="content" rows="5" cols="60">${requestScope.content}</textarea>
    <input type="hidden" name="topicId" value="${requestScope.topic.topicId}"/>
    <input type="submit" value="reply"/>
</form>

<%--输入图片内容--%>
<c:url value="file_upload.do" var="uploadUrl">
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
    <c:param name="shareType" value="chat"/>
</c:url>
<form method="post" enctype="multipart/form-data" action="${uploadUrl}">
    图片: <input type="file" name="uploadFile"/>
    <input type="submit" value="发送图片"/>
</form>
<br/><br/>

<%--聊天信息--%>
<c:forEach var="reply" items="${requestScope.pageBean.list}">
    <%--举报--%>
    <c:url value="report_add.jsp" var="reportUrl">
        <c:param name="contentType" value="reply"/>
        <c:param name="contentId" value="${reply.replyId}"/>
    </c:url>
    <%--用户个人信息--%>
    <c:url value="user_detail_other.do" var="userInfoUrl">
        <c:param name="userId" value="${reply.userId}"/>
    </c:url>
    <a href="${userInfoUrl}">${reply.username}</a>&nbsp;
    (${reply.created})&nbsp;
    <a href="${reportUrl}">举报</a>&nbsp;
    <%--[管理员]删除讨论区信息--%>
    <c:if test="${sessionScope.access.isUserInRole('admin')}">
        <c:url value="reply_delete.do" var="deleteReplyUrl">
            <c:param name="replyId" value="${reply.replyId}"/>
        </c:url>
        <a href="${deleteReplyUrl}">删除</a>
    </c:if>
    <br/>
    <c:choose>
        <%--文本信息--%>
        <c:when test="${reply.contentType == 0}">
            <pre><c:out value="${reply.content}"/></pre>
        </c:when>
        <%--图片信息--%>
        <c:when test="${reply.contentType == 1}">
            <c:url value="image_show.do" var="imageUrl">
                <c:param name="relativePath" value="${reply.content}"/>
            </c:url>
            <img src="${imageUrl}"/><br/>
        </c:when>
    </c:choose>
</c:forEach>

<%--分页系统--%>
<c:set var="myTotalIndex" value="11" scope="page"/>
<c:set var="myQueryUrl" value="topic_chat_info.do" scope="page"/>
<c:set var="myPageBean" value="${requestScope.pageBean}" scope="page"/>

<%--首页, 上一页, 下一页, 尾页设置--%>
<c:url value="${pageScope.myQueryUrl}" var="firstPageUrl">
    <c:param name="page" value="1"/>
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="previousPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage - 1}"/>
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="nextPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage + 1}"/>
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="lastPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.totalPage}"/>
    <c:param name="topicId" value="${requestScope.topic.topicId}"/>
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
        <c:param name="topicId" value="${requestScope.topic.topicId}"/>
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

</body>
</html>
