package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.bean.vo.TopicVo;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.util.BeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        String orderStr = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");

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
        PageBean<TopicPo> pageBeanPo;
        //判断搜索方式
        if (search != null && search.trim().length() > 0) { //根据关键词搜索, 无法进行排序
            pageBeanPo = topicService.queryTopics(TopicService.ACCESS_PUBLIC, currentPage, search);
        } else { //根据排序搜索
            pageBeanPo = topicService.queryTopics(TopicService.ACCESS_PUBLIC, currentPage, order, isReverse);
        }

        request.setAttribute("order", orderStr);
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBeanPo);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
    /*
    private void processPublicTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String page = request.getParameter("page");
        String orderStr = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");

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
            pageBean = topicService.queryTopics(TopicService.ACCESS_PUBLIC, search, currentPage);
        } else { //根据排序搜索
            pageBean = topicService.queryTopics(TopicService.ACCESS_PUBLIC, currentPage, order, isReverse);
        }

        request.setAttribute("type", request.getAttribute("type"));
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    private void processPrivateTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String page = request.getParameter("page");
        String orderStr = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");
        Access access = (Access) request.getSession().getAttribute("access");
        Set<Integer> topics = access.getGroups();

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
            pageBean = topicService.queryTopics(TopicService.ACCESS_PRIVATE, search, currentPage);
        } else { //根据排序搜索
            pageBean = topicService.queryTopics(TopicService.ACCESS_PRIVATE, currentPage, order, isReverse);
        }

        request.setAttribute("type", request.getAttribute("type"));
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    */
