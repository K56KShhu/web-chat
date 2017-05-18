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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet(
        name = "UserDetailOtherServlet",
        urlPatterns = {"/user_detail_other.do"}
)
public class UserDetailOtherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        Access access = (Access) request.getSession().getAttribute("access");

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (userService.isValidUserId(userId)) {
            int uId = Integer.valueOf(userId);
            if (access.isUserInRole("admin")) {
                processAllInfo(uId, request, response);
            } else {
                if (uId == access.getUserId()) {
                    response.sendRedirect("user_detail_self.do");
                } else {
                    processLimitInfo(uId, request, response);
                }
            }
        }
//        if (userService.isValidUserId(userId)) {
//            int uId = Integer.valueOf(userId);
//            if (uId == access.getUserId()) {
//                response.sendRedirect("user_detail_self.do");
//            } else {
//                if (access.isUserInRole("admin")) {
//                    processAllInfo(uId, request, response);
//                } else {
//                    processLimitInfo(uId, request, response);
//                }
//            }
//        }
    }

    private void processAllInfo(int userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得个人信息
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.getUser(userId);
        UserVo userVo = BeanUtil.userPoToVo(userPo);
        //获得小组信息
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        Set<Integer> groupIds = userService.getGroups(userId);
        Map<GroupPo, List<TopicPo>> groups = new HashMap<>();
        for (int groupId : groupIds) {
            GroupPo group = groupService.findGroup(groupId);
            List<TopicPo> topics = topicService.queryTopicsByGroup(groupId);
            groups.put(group, topics);
        }

        request.setAttribute("user", userVo);
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("user_detail_admin.jsp").forward(request, response);
    }

    private void processLimitInfo(int userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得个人信息
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.getUser(userId);
        request.setAttribute("user", userPo);
        request.getRequestDispatcher("user_detail_other.jsp").forward(request, response);
    }
}
