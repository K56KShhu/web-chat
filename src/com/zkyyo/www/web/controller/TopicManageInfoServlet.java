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
        name = "TopicManageInfoServlet",
        urlPatterns = {"/topic_manage_info.do"}
)
public class TopicManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<TopicPo> topicPos = topicService.findTopics();
        request.setAttribute("topics", topicPos);
        request.getRequestDispatcher("topic_manage.jsp").forward(request, response);
    }
}
