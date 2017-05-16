<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user manage</title>
</head>
<body>

<%@ include file="/WEB-INF/header_for_admin.jsp" %>

<h1>user manage</h1>

<a href="admin_audit_info.do">audit</a><br/>

<form method="get" action="user_manage_info.do">
    搜索用户: <input type="text" name="search" value="${requestScope.search}"/>
    <input type="submit"/>
</form>

<c:url value="user_manage_info.do" var="sexOrderUrl">
    <c:param name="order" value="sex"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
</c:url>
<c:url value="user_manage_info.do" var="createdOrderUrl">
    <c:param name="order" value="created"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
</c:url>
<c:url value="user_manage_info.do" var="statusOrderUrl">
    <c:param name="order" value="status"/>
    <c:param name="isReverse" value="${requestScope.isReverse == true ? 'false' : 'true'}"/>
</c:url>

<table border="1">
    <tr>
        <th>username</th>
        <th><a href="${sexOrderUrl}">sex</a></th>
        <th>email</th>
        <th><a href="${statusOrderUrl}">status</a></th>
        <th><a href="${createdOrderUrl}">created</a></th>
    </tr>
    <c:forEach var="user" items="${requestScope.pageBean.list}">
        <c:url value="user_manage.do" var="userNormalUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="status" value="1"/>
            <c:param name="search" value="${requestScope.search}"/>
            <c:param name="order" value="${requestScope.order}"/>
            <c:param name="page" value="${requestScope.pageBean.currentPage}"/>
            <c:param name="isReverse" value="${requestScope.isReverse}"/>
        </c:url>
        <c:url value="user_manage.do" var="userNotApprovedUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="status" value="-1"/>
            <c:param name="search" value="${requestScope.search}"/>
            <c:param name="order" value="${requestScope.order}"/>
            <c:param name="page" value="${requestScope.pageBean.currentPage}"/>
            <c:param name="isReverse" value="${requestScope.isReverse}"/>
        </c:url>
        <c:url value="user_manage.do" var="userForbiddenUrl">
            <c:param name="userId" value="${user.userId}"/>
            <c:param name="status" value="-2"/>
            <c:param name="search" value="${requestScope.search}"/>
            <c:param name="order" value="${requestScope.order}"/>
            <c:param name="page" value="${requestScope.pageBean.currentPage}"/>
            <c:param name="isReverse" value="${requestScope.isReverse}"/>
        </c:url>
        <tr>
            <td>${user.username}</td>
            <td>${user.sex}</td>
            <td>${user.email}</td>
            <td>${user.status}</td>
            <td>${user.created}</td>
            <c:if test="${user.status == 1}">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><a href="${userForbiddenUrl}">封印</a></td>
                <td>&nbsp;</td>
            </c:if>
            <c:if test="${user.status == 0}">
                <td><a href="${userNotApprovedUrl}">审核不通过</a></td>
                <td><a href="${userNormalUrl}">审核通过</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </c:if>
            <c:if test="${user.status == -1}">
                <td>&nbsp;</td>
                <td><a href="${userNormalUrl}">审核通过</a></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </c:if>
            <c:if test="${user.status == -2}">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><a href="${userNormalUrl}">解除封印</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<%--分页系统--%>
<c:set var="myTotalIndex" value="11" scope="page"/>
<c:set var="myQueryUrl" value="user_manage_info.do" scope="page"/>
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


</body>
</html>
