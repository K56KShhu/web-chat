<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小组档案</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/header_admin.jsp" %>

<c:url value="group_find_user.jsp" var="addUserUrl">
    <c:param name="groupId" value="${requestScope.group.groupId}"/>
</c:url>
<div style="text-align: right">
    <a href="${addUserUrl}">添加成员</a>&nbsp;
</div>

<h1>小组档案</h1>

<h2>小组信息</h2>
<table border="1" align="center">
    <tr>
        <th>小组名</th>
        <td>${requestScope.group.name}</td>
    </tr>
    <tr>
        <th>人数</th>
        <td>${requestScope.group.population}</td>
    </tr>
    <tr>
        <th>描述</th>
        <td>${requestScope.group.description}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td>${requestScope.group.created}</td>
    </tr>
    <tr>
        <th>会员</th>
        <td>
            <table border="1">
                <tr>
                    <th>username</th>
                    <th>sex</th>
                    <th>email</th>
                    <th>status</th>
                    <th>created</th>
                </tr>
                <c:forEach var="user" items="${requestScope.users}">
                    <c:url value="group_remove_user.do" var="removeUserUrl">
                        <c:param name="groupId" value="${requestScope.group.groupId}"/>
                        <c:param name="userId" value="${user.userId}"/>
                    </c:url>
                    <%--用户信息--%>
                    <c:url value="user_detail_other.do" var="userInfoUrl">
                        <c:param name="userId" value="${user.userId}"/>
                    </c:url>
                    <tr>
                        <td><a href="${userInfoUrl}"><c:out value="${user.username}"/></a></td>
                        <td>${user.sex}</td>
                        <td>${user.email}</td>
                        <td>${user.status}</td>
                        <td>${user.created}</td>
                        <td><a href="${removeUserUrl}">移除</a></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<h2>该小组用于以下讨论区</h2>
<table border="1" align="center">
    <tr>
        <th>讨论区</th>
        <th>创建时间</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topics}">
        <c:url value="group_remove_topic.do" var="removeTopicUrl">
            <c:param name="groupId" value="${requestScope.group.groupId}"/>
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <%--讨论区细节--%>
        <c:url value="topic_detail.do" var="topicDetailUrl">
            <c:param name="topicId" value="${topic.topicId}"/>
        </c:url>
        <tr>
            <td><a href="${topicDetailUrl}">${topic.title}</a></td>
            <td>${topic.created}</td>
            <td><a href="${removeTopicUrl}">移除</a></td>
        </tr>
    </c:forEach>
</table>
<br/>

</body>
</html>
