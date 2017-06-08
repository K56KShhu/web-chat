package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.dao.impl.TopicDaoJdbcImpl;
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

/**
 * 该Servlet用于处理创建讨论区的请求
 */
@WebServlet(
        name = "TopicAddServlet",
        urlPatterns = {"/topic_add.do"}
)
public class TopicAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title"); //标题
        String description = request.getParameter("description"); //描述
        String typeStr = request.getParameter("type"); //讨论区类型
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<String> errors = new ArrayList<>();
        //判断讨论区类型是否合法
        int type = TopicDaoJdbcImpl.ACCESS_PUBLIC;
        if ("public".equals(typeStr)) {
            type = TopicDaoJdbcImpl.ACCESS_PUBLIC;
        } else if ("private".equals(typeStr)) {
            type = TopicDaoJdbcImpl.ACCESS_PRIVATE;
        } else {
            errors.add("bad type");
        }
        //判断讨论区标题是否合法
        if (!topicService.isValidTitle(title)) {
            errors.add("标题长度有误");
        }
        //判断讨论区描述是否合法
        if (!topicService.isValidDescription(description)) {
            errors.add("描述过长");
        }

        //判断输入是否合法
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
        response.sendRedirect("topic_add.jsp");
    }
}
