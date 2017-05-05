package com.zkyyo.www.view;

import com.zkyyo.www.po.ReplyPo;
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
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        //检测主题是否存在
        if (!topicService.isExisted(Integer.valueOf(topicId))) { //不存在
            response.sendRedirect(page);
            return;
        } else { //存在
            page = "topic_chat_info.do?topicId=" + Integer.valueOf(topicId); // 主题有效
        }
        if (!replyService.isValidContent(content)) {
            errors.add("bad content");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("content", content);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            ReplyPo reply = new ReplyPo();
            reply.setUserId(userId);
            reply.setTopicId(Integer.valueOf(topicId));
            reply.setContent(content);
            replyService.addReply(reply);
            response.sendRedirect(page);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
