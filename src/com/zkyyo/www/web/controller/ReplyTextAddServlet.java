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

@WebServlet(
        name = "ReplyTextAddServlet",
        urlPatterns = {"/reply_text_add.do"}
)
public class ReplyTextAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String content = request.getParameter("content");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        /*
        String page = "index.jsp";
        List<String> errors = new ArrayList<>();
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        //检测主题是否存在
        if (!topicService.isExisted(Integer.valueOf(topicId))) { //不存在
            response.sendRedirect(page);
            return;
        } else { //存在
            page = "topic_chat_info.do?topicId=" + topicId; // 主题有效
        }
        */
        List<String> errors = new ArrayList<>();
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");

        //检查讨论区是否存在
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        //检查用户是否有权限发表回复(admin或受小组授权)
        int tId = Integer.valueOf(topicId);
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApprovedInTopic("admin", groups)) {
            response.sendRedirect("index.jsp");
            return;
        }

        //检查输入是否合法
        if (!replyService.isValidContent(content)) {
            errors.add("bad content");
        }
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
