package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理讨论区授权小组的请求
 */
@WebServlet(
        name = "TopicAddGroupServlet",
        urlPatterns = {"/topic_add_group.do"}
)
public class TopicAddGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId"); //被授权的小组ID
        String topicId = request.getParameter("topicId"); //讨论区ID

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        String message = "添加失败, 讨论区或小组不存在";
        //判断小组ID, 讨论区ID是否合法
        if (groupService.isValidId(groupId) && topicService.isValidId(topicId)) {
            int gId = Integer.valueOf(groupId);
            int tId = Integer.valueOf(topicId);
            //判断小组, 讨论区是否存在
            if (groupService.isExisted(gId) && topicService.isExisted(tId)) {
                //判断讨论区是否已经授权该小组
                if (!topicService.isTopicHasGroup(gId, tId)) {
                    groupService.addTopic(gId, tId);
                    message = "添加成功";
                } else {
                    message = "添加失败, 讨论区已经授权该小组";
                }
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
