package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.TopicPo;
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
        name = "TopicAddServlet",
        urlPatterns = {"/topic_add.do"}
)
public class TopicAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String typeStr = request.getParameter("type");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<String> errors = new ArrayList<>();
        int type = TopicService.ACCESS_PUBLIC;
        if ("public".equals(typeStr)) {
            type = TopicService.ACCESS_PUBLIC;
        } else if ("private".equals(typeStr)) {
            type = TopicService.ACCESS_PRIVATE;
        } else {
            errors.add("bad type");
        }
        if (!topicService.isValidTitle(title)) {
            errors.add("bad title");
        }
        if (!topicService.isValidDescription(description)) {
            errors.add("bad description");
        }

        if (errors.isEmpty()) {
            TopicPo topic = new TopicPo();
            topic.setTitle(title);
            topic.setDescription(description);
            topic.setCreatorId(userId);
            topic.setLastModifyId(userId);
            topic.setIsPrivate(type);
            topicService.addTopic(topic);
        } else {
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("type", typeStr);
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("topic_add.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processPublicTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processPrivateTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
