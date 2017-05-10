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
        String isPrivate = request.getParameter("isPrivate");
        if ("false".equals(isPrivate)) {
            processPubicTopic(request, response);
        } else if ("true".equals("isPrivate")) {
            processPrivateTopic(request, response);
        } else {
            response.sendRedirect("admin_index.jsp");
        }
    }

    private void processPubicTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<String> errors = new ArrayList<>();
        if (!topicService.isValidTitle(title)) {
            errors.add("bad title");
        }
        if (!topicService.isValidDescription(desc)) {
            errors.add("bad description");
        }

        if (errors.isEmpty()) {
            TopicPo topic = new TopicPo();
            topic.setTitle(title);
            topic.setDescription(desc);
            topic.setCreatorId(userId);
            topic.setLastModifyId(userId);
            topic.setIsPrivate(TopicService.NOT_PRIVATE);
            topicService.addTopic(topic);
        } else {
            request.setAttribute("title", title);
            request.setAttribute("desc", desc);
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("topic_add_public.jsp").forward(request, response);
    }

    private void processPrivateTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
