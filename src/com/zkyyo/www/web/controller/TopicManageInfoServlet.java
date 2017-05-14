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
        name = "TopicManageInfoServlet",
        urlPatterns = {"/topic_manage_info.do"}
)
public class TopicManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<TopicPo> topicPos = topicService.findTopics();
        request.setAttribute("topics", topicPos);
        request.getRequestDispatcher("topic_manage.jsp").forward(request, response);
        */
        String accessStr =request.getParameter("access");
        String search = request.getParameter("search");
        String page = request.getParameter("page");
        String orderStr = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");

        //处理页数
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        int access;
        if ("all".equals(accessStr)) {
            access = TopicService.ACCESS_ALL;
        } else if ("public".equals(accessStr)) {
            access = TopicService.ACCESS_PUBLIC;
        } else if ("private".equals(accessStr)) {
            access = TopicService.ACCESS_PRIVATE;
        } else {
            access = TopicService.ACCESS_ALL;
        }
        //升降序依据
        boolean isReverse = "true".equals(isReverseStr);
        //排序依据
        int order;
        if ("replyAccount".equals(orderStr)) {
            order = TopicService.ORDER_BY_REPLY_ACCOUNT;
        } else if ("lastTime".equals(orderStr)) {
            order = TopicService.ORDER_BY_LAST_TIME;
        } else if ("created".equals(orderStr)) {
            order = TopicService.ORDER_BY_CREATED;
        } else if ("access".equals(orderStr)) {
            order = TopicService.ORDER_BY_ACCESS;
        } else {
            order = TopicService.ORDER_BY_CREATED;
        }

        //获得数据
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        PageBean<TopicPo> pageBean;
        //判断搜索方式
        if (search != null && search.trim().length() > 0) { //根据关键词搜索, 无法进行排序
            pageBean = topicService.queryTopics(access, search, currentPage);
        } else { //根据排序搜索
            pageBean = topicService.queryTopics(access, currentPage, order, isReverse);
        }

        request.setAttribute("access", accessStr);
        request.setAttribute("order", orderStr);
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("topic_manage.jsp").forward(request, response);
    }
}
