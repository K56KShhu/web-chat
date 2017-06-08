<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>讨论区管理</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<div style="text-align: right">
    <a href="topic_add.jsp">创建讨论区</a>&nbsp;
</div>

<c:url value="topic_manage_info.do" var="replyAccountOderUrl">
    <c:param name="order" value="replyAccount"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>
<c:url value="topic_manage_info.do" var="lastTimeOrderUrl">
    <c:param name="order" value="lastTime"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>
<c:url value="topic_manage_info.do" var="createdOrderUrl">
    <c:param name="order" value="created"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>
<c:url value="topic_manage_info.do" var="accessOrderUrl">
    <c:param name="order" value="access"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>

<h1>讨论区管理</h1>

<form method="get" action="topic_manage_info.do">
    <select name="access">
        <option value="all" ${requestScope.access == 'all' ? 'selected' : ''}>所有讨论区</option>
        <option value="public" ${requestScope.access == 'public' ? 'selected': ''}>公共讨论区</option>
        <option value="private" ${requestScope.access == 'private' ? 'selected' : ''}>授权讨论区</option>
    </select>
    搜索: <input type="text" maxlength="1024" size="40" name="search"/>
    <input type="submit"/>
</form>

<table border="1" align="center">
    <tr>
        <th>讨论区</th>
        <th><a href="${accessOrderUrl}">类型</a></th>
        <th><a href="${replyAccountOderUrl}">回复</a></th>
        <th><a href="${lastTimeOrderUrl}">最后回复</a></th>
        <th><a href="${createdOrderUrl}">创建时间</a></th>
    </tr>
    <c:forEach var="topic" items="${requestScope.pageBean.list}">
        <%--参与讨论区--%>
        <c:url value="topic_chat_info.do" var="topicUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <%--讨论区细节--%>
        <c:url value="topic_detail.do" var="topicDetailUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <%--更新讨论区信息--%>
        <c:url value="topic_update_info.do" var="topicUpdateUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <%--删除讨论区--%>
        <c:url value="topic_delete.do" var="topicDeleteUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td><a href="${topicUrl}">${topic.title}</a></td>
            <td>${topic.isPrivateStr}</td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
            <td>${topic.created}</td>
            <td><a href="${topicDetailUrl}">档案</a></td>
            <td><a href="${topicUpdateUrl}">修改</a></td>
            <td><a href="${topicDeleteUrl}">删除</a></td>
                <%--为private讨论区增加小组访问权限--%>
            <c:if test="${topic.isPrivate == 1}">
                <c:url value="topic_find_group.jsp" var="addGroupUrl">
                    <c:param name="topicId" value="${topic.topicId}"/>
                </c:url>
                <td><a href="${addGroupUrl}">授权小组</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<%--分页系统--%>
<c:set var="myTotalIndex" value="11" scope="page"/>
<c:set var="myQueryUrl" value="topic_manage_info.do" scope="page"/>
<c:set var="myPageBean" value="${requestScope.pageBean}" scope="page"/>

<%--首页, 上一页, 下一页, 尾页设置--%>
<c:url value="${pageScope.myQueryUrl}" var="firstPageUrl">
    <c:param name="page" value="1"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="previousPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage - 1}"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="nextPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.currentPage + 1}"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="access" value="${requestScope.access}"/>
</c:url>
<c:url value="${pageScope.myQueryUrl}" var="lastPageUrl">
    <c:param name="page" value="${pageScope.myPageBean.totalPage}"/>
    <c:param name="search" value="${requestScope.search}"/>
    <c:param name="order" value="${requestScope.order}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="access" value="${requestScope.access}"/>
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
        <c:param name="access" value="${requestScope.access}"/>
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
