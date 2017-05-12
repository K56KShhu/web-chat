package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "GroupRemoveUserServlet",
        urlPatterns = {"/group_remove_user.do"}
)
public class GroupRemoveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String userId = request.getParameter("userId");

        String url = "admin_index.jsp";
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (groupService.isValidId(groupId) && userService.isValidUserId(userId)) {
            int gId = Integer.valueOf(groupId);
            int uId = Integer.valueOf(userId);
            if (groupService.isExisted(gId) && userService.isUserExisted(uId)) {
                groupService.removeUser(gId, uId);
                url = "group_detail.do?groupId=" + groupId;
            }
        }
        response.sendRedirect(url);
    }
}
