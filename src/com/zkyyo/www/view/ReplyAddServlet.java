package com.zkyyo.www.view;

import com.zkyyo.www.po.ReplyPo;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "ReplyAddServlet",
        urlPatterns = {"/reply_add.do"}
)
public class ReplyAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String content = request.getParameter("content");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        String page = "index.jsp";
        List<String> errors = new ArrayList<>();
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        if (!replyService.isValidId(topicId)) {
            errors.add("bad topicId");
            if (!replyService.isExisted(Integer.valueOf(topicId))) {
                errors.add("topic not exist");
            } else {
                page = "topic_chat_info.do?topicId=" + Integer.valueOf(topicId); // 主题有效
            }
        }
        if (!replyService.isValidContent(content)) {
            errors.add("bad content");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("content", content);
        } else {
            ReplyPo reply = new ReplyPo();
            reply.setUserId(userId);
            reply.setTopicId(Integer.valueOf(topicId));
            reply.setContent(content);
            replyService.addReply(reply);
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
