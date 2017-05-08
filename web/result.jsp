<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--操作成功--%>
<c:if test="${requestScope.errors != null && !requestScope.errors.isEmpty()}">
    ${param.faile}<br/>
    <c:forEach var="error" items="${requestScope.errors}">
        <c:out value="${error}"/><br/>
    </c:forEach>
</c:if>
<%--操作失败--%>
<c:if test="${requestScope.errors != null && requestScope.errors.isEmpty()}">
    ${param.success}<br/>
</c:if>
