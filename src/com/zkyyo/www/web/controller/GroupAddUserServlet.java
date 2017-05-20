package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理添加小组成员的请求
 */
@WebServlet(
        name = "GroupAddUserServlet",
        urlPatterns = {"/group_add_user.do"}
)
public class GroupAddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId"); //小组ID
        String userId = request.getParameter("userId"); //待添加的用户ID

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        String message = "添加失败, 用户或小组不存在";
        //判断小组ID, 用户ID是否合法
        if (groupService.isValidId(groupId) && userService.isValidUserId(userId)) {
            int gId = Integer.valueOf(groupId);
            int uId = Integer.valueOf(userId);
            //判断小组, 用户是否存在
            if (groupService.isExisted(gId) && userService.isUserExisted(uId)) {
                //判断用户是否已经是小组成员
                if (!userService.isUserInGroup(uId, gId)) {
                    groupService.addUser(gId, uId);
                    message = "添加成功";
                } else {
                    message = "添加失败, 用户已经位于小组中";
                }
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
