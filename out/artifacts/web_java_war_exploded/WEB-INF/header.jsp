<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="text-align: right">
    <a href="<c:url value='topic_find.do'/>">首页</a>&nbsp;
    <a href="<c:url value='user_detail.do'/>">个人</a>&nbsp;
    <a href="<c:url value='logout.do'/>">注销</a>&nbsp;
    <c:if test="${sessionScope.access.isUserInRole('admin')}">
        <a href="<c:url value='admin_index.jsp'/>">管理</a>&nbsp;
    </c:if>
</div>
