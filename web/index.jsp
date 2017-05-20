<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>咱部落</title>
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
    <a href="topic_find_private.do">进入授权讨论区</a>
</div>

<h1>咱部落</h1>

<form method="get" action="topic_find.do">
    搜索讨论区: <input type="text" name="search" value="${requestScope.search}" maxlength="1024" size="40"/>
    <input type="submit"/>
</form>

<c:if test="${requestScope.pageBean == null}">
    <c:redirect url="topic_find.do"/>
</c:if>

<c:url value="topic_find.do" var="replyAccountOderUrl">
    <c:param name="order" value="replyAccount"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
</c:url>
<c:url value="topic_find.do" var="lastTimeOrderUrl">
    <c:param name="order" value="lastTime"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
</c:url>
<c:url value="topic_find.do" var="createdOrderUrl">
    <c:param name="order" value="created"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
</c:url>

<table border="1" align="center">
    <tr>
        <th>讨论区</th>
        <th><a href="${replyAccountOderUrl}">回复</a></th>
        <th><a href="${lastTimeOrderUrl}">最后回复</a></th>
    </tr>
    <c:forEach var="topic" items="${requestScope.pageBean.list}">
        <c:url value="topic_chat_info.do" var="topicUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td><a href="${topicUrl}">${topic.title}</a><br/>${topic.description}</td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
                <%--[管理员]权限--%>
            <c:if test="${sessionScope.access.isUserInRole('admin')}">
                <%--更新讨论区信息--%>
                <c:url value="topic_update_info.do" var="topicUpdateUrl">
                    <c:param name="topicId" value="${topic.topicId}"/>
                </c:url>
                <%--删除讨论区--%>
                <c:url value="topic_delete.do" var="topicDeleteUrl">
                    <c:param name="topicId" value="${topic.topicId}"/>
                </c:url>
                <td><a href="${topicUpdateUrl}">修改</a></td>
                <td><a href="${topicDeleteUrl}">删除</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<%--分页系统--%>
<c:set var="myTotalIndex" value="11" scope="page"/>
<c:set var="myQueryUrl" value="topic_find.do" scope="page"/>
<c:set var="myPageBean" value="${requestScope.pageBean}" scope="page"/>

<%--首页, 上一页, 下一页, 尾页设置--%>
<c:url value="${pageScope.myQueryUrl}" var="firstPageUrl">
    <c:param name="page" value="1"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="previousPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage - 1}"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="nextPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage + 1}"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="lastPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.totalPage}"/>
    <c:param name="search" value="${requestScope.search}"/>
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
        <c:param name="search" value="${requestScope.search}"/>
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


<%--分页封装--%>
<%--<c:set var="myPage" value="${requestScope.pageBean}" scope="request"/>--%>
<%--<c:set var="myQueryUrl" value="topic_find.do" scope="request"/>--%>
<%--<c:set var="myTotalIndex" value="11" scope="request"/>--%>
<%--<c:set var="myIsReverse" value="${requestScope.isReverse}" scope="request"/>--%>
<%--用param传递会有乱码问题, 原因是使用param的话是通过URL传递, 而URL采用ISO-8859-1编码--%>
<%--<c:set var="mySearch" value="${requestScope.search}" scope="request"/>--%>
<%--<jsp:include page="WEB-INF/paging.jsp"/>--%>
<%--分页封装--%>

<%--
<c:choose>首页  用户  小组  讨论区  举报
topic manage

￼ 搜索: ￼
 ￼Submit
add topic
title	creator	lastModifyId	isPrivate	reply	last	created
请问打码是一种怎样的体验?
听说现在很多人都在学习编程,大家认为变成是一种怎样的体验	2	2	0
    <c:when test="${requestScope.topics != null}">
        <table border="1">
            <tr>
                <th>title</th>
                <th>desc</th>
                <th>isPrivate</th>
                <th>replyAccount</th>
                <th>lastTime</th>
                <th>created</th>
            </tr>
            <c:forEach var="topic" items="${requestScope.topics}">
                <c:url value="topic_chat_info.do" var="topicUrl">
                    <c:param name="topicId" value="${topic.topicId}"/>
                </c:url>
                <tr>
                    <td><a href="${topicUrl}">${topic.title}</a></td>
                    <td>${topic.description}</td>
                    <td>${topic.isPrivate}</td>
                    <td>${topic.replyAccount}</td>
                    <td>${topic.lastTime}</td>
                    <td>${topic.created}</td>
                    <c:if test="${sessionScope.access.isUserInRole('admin')}">
                        <c:url value="topic_delete.do" var="deleteUrl">
                            <c:param name="topicId" value="${topic.topicId}"/>
                        </c:url>
                        <c:url value="topic_update_info.do" var="updateUrl">
                            <c:param name="topicId" value="${topic.topicId}"/>
                        </c:url>
                        <td><a href="${deleteUrl}">删除</a></td>
                        <td><a href="${updateUrl}">修改</a></td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <c:redirect url="topic_find.do"/>
    </c:otherwise>
</c:choose>
--%>

</body>
</html>
