<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--用户个人信息--%>
<c:url value="user_detail_other.do" var="userInfoUrl">
    <c:param name="userId" value="${user.userId}"/>
</c:url>

<%--参与讨论区--%>
<c:url value="topic_chat_info.do" var="topicUrl">
    <c:param name="topicId" value="${topic.topicId}"/>
</c:url>

<%--小组详细信息--%>
<c:url value="group_detail.do" var="groupDetailUrl">
    <c:param name="groupId" value="${group.groupId}"/>
</c:url>
