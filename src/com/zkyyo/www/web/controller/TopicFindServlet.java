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

@WebServlet(
        name = "TopicFindServlet",
        urlPatterns = {"/topic_find.do"}
)
public class TopicFindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeStr = request.getParameter("type");
        String search = request.getParameter("search");
        String page = request.getParameter("page");
        String orderStr = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");

        String url = "index.jsp";
        //获取讨论区类型
        int type;
        if ("public".equals(typeStr)) {
            type = TopicService.ACCESS_PUBLIC;
        } else if ("private".equals(typeStr)) {
            type = TopicService.ACCESS_PRIVATE;
            url = "topic_private.jsp";
        } else {
            type = TopicService.ACCESS_PUBLIC;
        }
        //处理页数
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
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
        } else {
            order = TopicService.ORDER_BY_CREATED;
        }

        //获得数据
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        PageBean<TopicPo> pageBean;
        //判断搜索方式
        if (search != null && search.trim().length() > 0) { //根据关键词搜索, 无法进行排序
            pageBean = topicService.queryTopics(type, search, currentPage);
        } else { //根据排序搜索
            pageBean = topicService.queryTopics(type, currentPage, order, isReverse);
        }

        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher(url).forward(request, response);

        /*
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
        */
    }
}
