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
 * 该Servlet用于处理移除小组成员的请求
 */
@WebServlet(
        name = "GroupRemoveUserServlet",
        urlPatterns = {"/group_remove_user.do"}
)
public class GroupRemoveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId"); //小组ID
        String userId = request.getParameter("userId"); //待移除的用户ID

        String url = "admin_index.jsp";
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //判断小组ID, 用户ID是否存在
        if (groupService.isValidId(groupId) && userService.isValidUserId(userId)) {
            int gId = Integer.valueOf(groupId);
            int uId = Integer.valueOf(userId);
            //判断小组是否存在
            if (groupService.isExisted(gId)) {
                url = "group_detail.do?groupId=" + groupId;
                //判断用户是否存在
                if (userService.isUserExisted(uId)) {
                    groupService.removeUser(gId, uId);
                }
            }
        }
        response.sendRedirect(url);
    }
}
