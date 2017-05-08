package com.zkyyo.www.view;

import com.zkyyo.www.po.TopicPo;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TopicUpdateInfoServlet",
        urlPatterns = {"/topic_update_info.do"}
)
public class TopicUpdateInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            if (topicService.isExisted(tId)) {
                TopicPo topic = topicService.findTopic(tId);
                request.setAttribute("topic", topic);
                request.getRequestDispatcher("topic_update.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("index.jsp");
    }
}
