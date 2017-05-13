package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TopicAddGroupServlet",
        urlPatterns = {"/topic_add_group.do"}
)
public class TopicAddGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String topicId = request.getParameter("topicId");

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        String message = "添加失败, 讨论区或小组不存在";
        if (groupService.isValidId(groupId) && topicService.isValidId(topicId)) {
            int gId = Integer.valueOf(groupId);
            int tId = Integer.valueOf(topicId);
            if (groupService.isExisted(gId) && topicService.isExisted(tId)) {
                groupService.addTopic(gId, tId);
                message = "开起来是添加成功了";
//                if (!groupService.isGroupInTopic(gId, tId)) {
//                    message = "添加成功"
//                } else {
//                    message = "添加失败, 讨论区已经包含该小组";
//                }
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
