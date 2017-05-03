package com.zkyyo.www.view;

import com.zkyyo.www.po.TopicPo;
import com.zkyyo.www.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TopicChatInfoServlet",
        urlPatterns = {"/topic_chat_info.do"}
)
public class TopicChatInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (topicId != null && topicService.isValidId(topicId)) {
            TopicPo topic = topicService.findTopic(Integer.valueOf(topicId));
            request.setAttribute("topic", topic);
            request.getRequestDispatcher("topic_chat.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
