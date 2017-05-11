package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
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
        String page = request.getParameter("page");
        String order = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");
        boolean isReverse = "true".equals(isReverseStr);
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        PageBean<TopicPo> pageBean;
        if (search != null && search.trim().length() > 0) {
            pageBean = topicService.queryTopics(currentPage, search);
        } else {
            if ("replyAccount".equals(order)) {
                pageBean = topicService.queryTopics(currentPage, TopicService.ORDER_BY_REPLY_ACCOUNT, isReverse);
            } else if ("lastTime".equals(order)) {
                pageBean = topicService.queryTopics(currentPage, TopicService.ORDER_BY_LAST_TIME, isReverse);
            } else if ("created".equals(order)) {
                pageBean = topicService.queryTopics(currentPage, TopicService.ORDER_BY_CREATED, isReverse);
            } else {
                //默认按照最后回复时间的倒序进行排序
                pageBean = topicService.queryTopics(currentPage, TopicService.ORDER_BY_LAST_TIME, true);
            }
        }
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
