package com.zkyyo.www.web.controller;

import com.zkyyo.www.po.TopicPo;
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
        name = "TopicUpdateServlet",
        urlPatterns = {"/topic_update.do"}
)
public class TopicUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        List<String> errors = new ArrayList<>();
        //判断讨论区ID是否合法且存在
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            if (topicService.isExisted(tId)) {
                if (!topicService.isValidTitle(title)) {
                    errors.add("bad title");
                }
                if (!topicService.isValidDescription(desc)) {
                    errors.add("bad desc");
                }
                //判断修改内容是否有误
                if (errors.isEmpty()) {
                    TopicPo topic = new TopicPo();
                    topic.setTopicId(tId);
                    topic.setTitle(title);
                    topic.setDescription(desc);
                    topic.setLastModifyId(userId);
                    topicService.updateTopic(topic);
                }
                //无论更新成功还是失败都回显信息
                TopicPo temp = new TopicPo();
                temp.setTopicId(tId);
                temp.setTitle(title);
                temp.setDescription(desc);
                request.setAttribute("topic", temp);
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("topic_update.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "该讨论区不存在");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
