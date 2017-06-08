package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.bean.vo.UserVo;
import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 该Servlet用于处理获取本人信息的请求
 */
@WebServlet(
        name = "UserDetailSelfServlet",
        urlPatterns = {"/user_detail_self.do"}
)
public class UserDetailSelfServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象
        //获得用户信息
        int userId = access.getUserId();
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.findUser(userId);
        UserVo userVo = BeanUtil.userPoToVo(userPo);
        //获取小组信息, 以及每个小组对应的讨论区信息
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        Set<Integer> groupIds = access.getGroups();
        Map<GroupPo, List<TopicPo>> groups = new HashMap<>();
        for (int groupId : groupIds) {
            GroupPo group = groupService.findGroup(groupId);
            List<TopicPo> topics = topicService.queryTopicsByGroup(groupId);
            groups.put(group, topics);
        }
        request.setAttribute("user", userVo);
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("user_detail_self.jsp").forward(request, response);
    }
}
