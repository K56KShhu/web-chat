package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.dao.impl.ReplyDaoJdbcImpl;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 该Servlet用于处理添加文本回复的请求
 */
@WebServlet(
        name = "ReplyTextAddServlet",
        urlPatterns = {"/reply_text_add.do"}
)
public class ReplyTextAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId"); //讨论区ID
        String content = request.getParameter("content"); //文本回复内容
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象
        int userId = access.getUserId();

        List<String> errors = new ArrayList<>();
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");

        //检查讨论区是否存在
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        //检查用户是否有权限发表回复, 以下用户有权限: 位于授权小组的普通用户, admin, root
        int tId = Integer.valueOf(topicId);
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApproved(groups, "admin", "root")) {
            response.sendRedirect("index.jsp");
            return;
        }

        //检查回复文本是否合法
        if (!replyService.isValidContent(content)) {
            errors.add("bad content");
        }
        //判断输入是否有误
        if (errors.isEmpty()) {
            ReplyPo reply = new ReplyPo();
            reply.setUserId(userId);
            reply.setTopicId(tId);
            reply.setContent(content);
            reply.setContentType(ReplyDaoJdbcImpl.CONTENT_TYPE_TEXT);
            replyService.addReply(reply);
            String page = "topic_chat_info.do?topicId=" + topicId; //讨论区有效
            response.sendRedirect(page);
        } else {
            request.setAttribute("content", content);
            request.setAttribute("errors", errors);
            request.setAttribute("topicId", topicId);
            request.getRequestDispatcher("topic_chat_info.do").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
