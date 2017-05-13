package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(
        name = "UserDetailServlet",
        urlPatterns = {"/user_detail.do"}
)
public class UserDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Access access = (Access) request.getSession().getAttribute("access");
        //获得用户信息
        int userId = access.getUserId();
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.getUser(userId);
        //获得小组信息
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        Set<Integer> groupIds = access.getGroups();
        List<GroupPo> groups = new ArrayList<>();
        for (int groupId : groupIds) {
            groups.add(groupService.findGroup(groupId));
        }
        request.setAttribute("user", userPo);
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("user_detail.jsp").forward(request, response);
    }
}
