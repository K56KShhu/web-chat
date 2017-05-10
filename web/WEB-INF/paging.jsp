<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="queryUrl" value="${param.queryUrl}" scope="page"/>
<c:set var="totalIndex" value="${param.totalIndex}" scope="page"/>
<c:set var="isReverse" value="${param.isReverse}" scope="page"/>

<c:url value="${pageScope.queryUrl}" var="firstPageUrl">
    <c:param name="page" value="1"/>
    <c:param name="isReverse" value="${pageScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.queryUrl}" var="previousPageUrl">
    <c:param name="page" value="${requestScope.page.currentPage - 1}"/>
    <c:param name="isReverse" value="${pageScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.queryUrl}" var="nextPageUrl">
    <c:param name="page" value="${requestScope.page.currentPage + 1}"/>
    <c:param name="isReverse" value="${pageScope.isReverse}"/>
</c:url>
<c:url value="${pageScope.queryUrl}" var="lastPageUrl">
    <c:param name="page" value="${requestScope.page.totalPage}"/>
    <c:param name="isReverse" value="${pageScope.isReverse}"/>
</c:url>

<c:set var="begin" value="1" scope="page"/>
<c:set var="end" value="${requestScope.page.totalPage}" scope="page"/>

<%--下面两个if保证: 当前页数位于中间时, 左右两边都有${param.totalIndex / 2}个页数, 共${param.totalIndex}个页数--%>
<c:if test="${requestScope.page.currentPage - param.totalIndex / 2 > 0}">
    <c:set var="begin" value="${requestScope.page.currentPage - 5}"/>
</c:if>
<c:if test="${requestScope.page.currentPage + param.totalIndex / 2 < requestScope.page.totalPage}">
    <c:set var="end" value="${requestScope.page.currentPage + param.totalIndex / 2}"/>
</c:if>

<%--下面两个if保证: 当前页数即使临近边界, 也能显示${param.totalIndex}个页数--%>
<c:if test="${requestScope.page.currentPage - param.totalIndex / 2 + 1 < 0}">
    <c:set var="begin" value="1"/>
    <%--判断给定的最大索引是否有效--%>
    <c:choose>
        <%--最大索引无效--%>
        <c:when test="${requestScope.page.totalPage < param.totalIndex}">
            <c:set var="end" value="${requestScope.page.totalPage}"/>
        </c:when>
        <%--最大索引有效--%>
        <c:otherwise>
            <c:set var="end" value="${param.totalIndex}"/>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${requestScope.page.currentPage + param.totalIndex / 2 > requestScope.page.totalPage}">
    <%--判断给定的最大索引是否有效--%>
    <c:choose>
        <%--最大索引无效--%>
        <c:when test="${requestScope.page.totalPage - param.totalIndex < 0}">
            <c:set var="begin" value="1"/>
        </c:when>
        <%--最大索引有效--%>
        <c:otherwise>
            <c:set var="begin" value="${requestScope.page.totalPage - param.totalIndex}"/>
        </c:otherwise>
    </c:choose>
    <c:set var="end" value="${requestScope.page.totalPage}"/>
</c:if>

<a href="${firstPageUrl}">首页</a>
<a href="${previousPageUrl}">上一页</a>
<c:forEach var="number" begin="${pageScope.begin}" end="${pageScope.end}">
    <c:url value="${pageScope.queryUrl}" var="indexPageUrl">
        <c:param name="page" value="${number}"/>
        <c:param name="isReverse" value="${pageScope.isReverse}"/>
    </c:url>
    <c:choose>
        <c:when test="${number == requestScope.page.currentPage}">
            [${number}]&nbsp;
        </c:when>
        <c:otherwise>
            <a href="${indexPageUrl}">${number}</a>&nbsp;
        </c:otherwise>
    </c:choose>
</c:forEach>
<a href="${nextPageUrl}">下一页</a>
<a href="${lastPageUrl}">最后一页</a>
