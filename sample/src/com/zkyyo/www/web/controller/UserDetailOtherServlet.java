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

/**
 * 该Servlet用于处理获取其他用户信息的请求
 */
@WebServlet(
        name = "UserDetailOtherServlet",
        urlPatterns = {"/user_detail_other.do"}
)
public class UserDetailOtherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId"); //用户ID
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //判断用户是否存在
        if (!userService.isValidUserId(userId) || !userService.isUserExisted(Integer.valueOf(userId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        int uId = Integer.valueOf(userId);
        //根据查询者的角色分配请求
        if (access.isUserInRole("admin")) {
            //admin操作
            processAllInfo(uId, request, response);
        } else {
            //普通用户操作
            //判断是否查看本人信息
            if (uId == access.getUserId()) {
                response.sendRedirect("user_detail_self.do");
            } else {
                processLimitInfo(uId, request, response);
            }
        }
    }

    /**
     * 获取用户的所有信息
     *
     * @param userId   用户ID
     * @param request  请求对象
     * @param response 响应对象
     * @throws ServletException Servlet发生异常时抛出
     * @throws IOException      输入或输出发生异常时抛出
     */
    private void processAllInfo(int userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得用户信息
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.findUser(userId);
        UserVo userVo = BeanUtil.userPoToVo(userPo);
        //获取小组信息, 以及每个小组对应的讨论区信息
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

    /**
     * 获取用户的有限信息
     *
     * @param userId   用户ID
     * @param request  请求对象
     * @param response 响应对象
     * @throws ServletException Servlet发生异常时抛出
     * @throws IOException      输入或输出发生异常时抛出
     */
    private void processLimitInfo(int userId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得用户信息
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.findUser(userId);
        request.setAttribute("user", userPo);
        request.getRequestDispatcher("user_detail_other.jsp").forward(request, response);
    }
}
