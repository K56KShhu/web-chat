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

/**
 * 该Servlet用于处理复杂查询讨论区的请求
 */
@WebServlet(
        name = "TopicManageInfoServlet",
        urlPatterns = {"/topic_manage_info.do"}
)
public class TopicManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessStr = request.getParameter("access"); //讨论区类型
        String search = request.getParameter("search"); //查询内容
        String page = request.getParameter("page"); //请求页数
        String orderStr = request.getParameter("order"); //排序依据
        boolean isReverse = "true".equals(request.getParameter("isReverse")); //是否降序 true降序, false升序

        //处理页数
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        //判断讨论区类型
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
        //判断排序依据
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

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        PageBean<TopicPo> pageBeanPo;
        //判断搜索方式
        if (search != null && search.trim().length() > 0) {
            //根据关键字查询, 无法进行排序
            pageBeanPo = topicService.queryTopics(access, currentPage, search);
        } else {
            //根据排序查询
            pageBeanPo = topicService.queryTopics(access, currentPage, order, isReverse);
        }

        //将TopicPo转换为TopicVo
        List<TopicPo> topicPos = pageBeanPo.getList();
        List<TopicVo> topicVos = new ArrayList<>();
        for (TopicPo topicPo : topicPos) {
            topicVos.add(BeanUtil.topicPoToVoForList(topicPo));
        }
        //重构分页对象
        PageBean<TopicVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, topicVos);

        request.setAttribute("access", accessStr);
        request.setAttribute("order", orderStr);
        request.setAttribute("search", search);
        request.setAttribute("pageBean", pageBeanVo);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher("topic_manage.jsp").forward(request, response);
    }
}
