package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 该Servlet用于处理获取小组细节信息的请求
 */
@WebServlet(
        name = "GroupDetailServlet",
        urlPatterns = {"/group_detail.do"}
)
public class GroupDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId"); //小组ID

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        //判断小组ID是否合法
        if (groupService.isValidId(groupId)) {
            int gId = Integer.valueOf(groupId);
            //判断小组是否存在
            if (groupService.isExisted(gId)) {
                GroupPo group = groupService.findGroup(gId);
                //获取该小组下所有用户的信息
                List<UserPo> users = userService.queryUsersByGroup(gId);
                //获取授权该小组的所有讨论区信息
                List<TopicPo> topics = topicService.queryTopicsByGroup(gId);
                request.setAttribute("group", group);
                request.setAttribute("users", users);
                request.setAttribute("topics", topics);
                request.getRequestDispatcher("group_detail.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("admin_index.jsp");
    }
}
