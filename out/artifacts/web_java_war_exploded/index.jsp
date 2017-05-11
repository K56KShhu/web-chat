<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>index</title>
</head>
<body>

<%@ include file="/WEB-INF/header.jsp" %>

<h1>index</h1>

<form method="get" action="topic_find.do">
    搜索讨论区: <input type="text" name="search"/>
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

<table border="1">
    <tr>
        <th>title</th>
        <th>isPrivate</th>
        <th><a href="${replyAccountOderUrl}">reply</a></th>
        <th><a href="${lastTimeOrderUrl}">last</a></th>
        <th><a href="${createdOrderUrl}">created</a></th>
    </tr>
    <c:forEach var="topic" items="${requestScope.pageBean.list}">
        <c:url value="topic_chat_info.do" var="topicUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td>
                <table>
                    <tr>
                        <td><a href="${topicUrl}">${topic.title}</a></td>
                    </tr>
                    <tr>
                        <td>${topic.description}</td>
                    </tr>
                </table>
            </td>
            <td>${topic.isPrivate}</td>
            <td>${topic.replyAccount}</td>
            <td>${topic.lastTime}</td>
            <td>${topic.created}</td>
        </tr>
    </c:forEach>
</table>

<%--分页封装--%>
<c:set var="myPage" value="${requestScope.pageBean}" scope="request"/>
<c:set var="myQueryUrl" value="topic_find.do" scope="request"/>
<c:set var="myTotalIndex" value="11" scope="request"/>
<c:set var="myIsReverse" value="${requestScope.isReverse}" scope="request"/>
<%--用param传递会有乱码问题, 原因是使用param的话是通过URL传递, 而URL采用ISO-8859-1编码--%>
<c:set var="mySearch" value="${requestScope.search}" scope="request"/>
<jsp:include page="WEB-INF/paging.jsp"/>
<%--分页封装--%>

<%--
<c:choose>
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
