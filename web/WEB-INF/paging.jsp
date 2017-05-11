<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url value="${requestScope.myQueryUrl}" var="firstPageUrl">
    <c:param name="page" value="1"/>
    <c:param name="isReverse" value="${requestScope.myIsReverse}"/>
    <c:param name="search" value="${requestScope.mySearch}"/>
</c:url>
<c:url value="${requestScope.myQueryUrl}" var="previousPageUrl">
    <c:param name="page" value="${requestScope.myPage.currentPage - 1}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="search" value="${requestScope.mySearch}"/>
</c:url>
<c:url value="${requestScope.myQueryUrl}" var="nextPageUrl">
    <c:param name="page" value="${requestScope.myPage.currentPage + 1}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="search" value="${requestScope.mySearch}"/>
</c:url>
<c:url value="${requestScope.myQueryUrl}" var="lastPageUrl">
    <c:param name="page" value="${requestScope.myPage.totalPage}"/>
    <c:param name="isReverse" value="${requestScope.isReverse}"/>
    <c:param name="search" value="${requestScope.mySearch}"/>
</c:url>

<c:set var="begin" value="1" scope="page"/>
<c:set var="end" value="${requestScope.myPage.totalPage}" scope="page"/>

<%--下面两个if保证: 当前页数位于中间时, 左右两边都有${requestScope.myTotalIndex / 2}个页数, 共${requestScope.myTotalIndex}个页数--%>
<c:if test="${requestScope.myPage.currentPage - requestScope.myTotalIndex / 2 > 0}">
    <c:set var="begin" value="${requestScope.myPage.currentPage - 5}"/>
</c:if>
<c:if test="${requestScope.myPage.currentPage + requestScope.myTotalIndex / 2 < requestScope.myPage.totalPage}">
    <c:set var="end" value="${requestScope.myPage.currentPage + requestScope.myTotalIndex / 2}"/>
</c:if>

<%--下面两个if保证: 当前页数即使临近边界, 也能显示${requestScope.myTotalIndex}个页数--%>
<c:if test="${requestScope.myPage.currentPage - requestScope.myTotalIndex / 2 + 1 < 0}">
    <c:set var="begin" value="1"/>
    <%--判断给定的最大索引是否有效--%>
    <c:choose>
        <%--最大索引无效--%>
        <c:when test="${requestScope.myPage.totalPage < requestScope.myTotalIndex}">
            <c:set var="end" value="${requestScope.myPage.totalPage}"/>
        </c:when>
        <%--最大索引有效--%>
        <c:otherwise>
            <c:set var="end" value="${requestScope.myTotalIndex}"/>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${requestScope.myPage.currentPage + requestScope.myTotalIndex / 2 > requestScope.myPage.totalPage}">
    <%--判断给定的最大索引是否有效--%>
    <c:choose>
        <%--最大索引无效--%>
        <c:when test="${requestScope.myPage.totalPage - requestScope.myTotalIndex < 0}">
            <c:set var="begin" value="1"/>
        </c:when>
        <%--最大索引有效--%>
        <c:otherwise>
            <c:set var="begin" value="${requestScope.myPage.totalPage - requestScope.myTotalIndex}"/>
        </c:otherwise>
    </c:choose>
    <c:set var="end" value="${requestScope.myPage.totalPage}"/>
</c:if>

<a href="${firstPageUrl}">首页</a>
<a href="${previousPageUrl}">上一页</a>
<c:forEach var="number" begin="${pageScope.begin}" end="${pageScope.end}">
    <c:url value="${requestScope.myQueryUrl}" var="indexPageUrl">
        <c:param name="page" value="${number}"/>
        <c:param name="isReverse" value="${requestScope.isReverse}"/>
        <c:param name="search" value="${requestScope.mySearch}"/>
    </c:url>
    <c:choose>
        <c:when test="${number == requestScope.myPage.currentPage}">
            [${number}]&nbsp;
        </c:when>
        <c:otherwise>
            <a href="${indexPageUrl}">${number}</a>&nbsp;
        </c:otherwise>
    </c:choose>
</c:forEach>
<a href="${nextPageUrl}">下一页</a>
<a href="${lastPageUrl}">最后一页</a>
