package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.util.CheckUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于简单查询讨论区的请求
 */
@WebServlet(
        name = "TopicFindServlet",
        urlPatterns = {"/topic_find.do"}
)
public class TopicFindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search"); //查询内容
        String page = request.getParameter("page"); //请求页数
        String orderStr = request.getParameter("order"); //排序依据
        boolean isReverse = "true".equals(request.getParameter("isReverse")); //是否倒序, true倒序, false升序

        //处理页数
        int currentPage = 1;
        if (CheckUtil.isValidInteger(CheckUtil.NUMBER_POSITIVE, page, 10)) {
            currentPage = Integer.valueOf(page);
        }
        //判断排序依据
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

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        PageBean<TopicPo> pageBeanPo;
        //判断搜索方式
        if (search != null && search.trim().length() > 0) {
            //通过关键字查询, 无法进行排序
            pageBeanPo = topicService.queryTopics(TopicService.ACCESS_PUBLIC, currentPage, search);
        } else {
            //通过排序搜索
            pageBeanPo = topicService.queryTopics(TopicService.ACCESS_PUBLIC, currentPage, order, isReverse);
        }

        request.setAttribute("order", orderStr);
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBeanPo);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
