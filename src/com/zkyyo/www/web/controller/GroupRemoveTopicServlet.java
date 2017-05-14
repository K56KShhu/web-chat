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
        name = "GroupRemoveTopicServlet",
        urlPatterns = {"/group_remove_topic.do"}
)
public class GroupRemoveTopicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String topicId = request.getParameter("topicId");

        String url = "admin_index.jsp";
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (groupService.isValidId(groupId) && topicService.isValidId(topicId)) {
            int gId = Integer.valueOf(groupId);
            int tId = Integer.valueOf(topicId);
            if (groupService.isExisted(gId)) {
                url = "group_detail.do?groupId=" + groupId;
                if (topicService.isExisted(tId)) {
                    groupService.removeTopic(gId, tId);
                }
            }
        }
        response.sendRedirect(url);
    }
}
