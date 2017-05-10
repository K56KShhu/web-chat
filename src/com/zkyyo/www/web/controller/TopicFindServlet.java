package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "TopicFindServlet",
        urlPatterns = {"/topic_find.do"}
)
public class TopicFindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<TopicPo> topics;
        if (search != null) {
            topics = topicService.findTopicsByKeys(search);
        } else {
            topics = topicService.findTopics();
        }
        request.setAttribute("topics", topics);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
