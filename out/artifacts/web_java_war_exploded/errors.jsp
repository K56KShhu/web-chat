<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${requestScope.errors != null && !requestScope.errors.isEmpty()}">
    操作失败, 原因如下<br/>
    <c:forEach var="error" items="${requestScope.errors}">
        <c:out value="${error}"/><br/>
    </c:forEach>
</c:if>
