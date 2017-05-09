package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "UserManageServlet",
        urlPatterns = {"/user_manage.do"}
)
public class UserManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (userService.isValidStatus(status)) {
            if (userService.isValidUserId(userId) && userService.isUserExisted(userId)) {
                int uStatus = Integer.valueOf(status);
                int id = Integer.valueOf(userId);
                if (uStatus == UserService.STATUS_NORMAL) {
                    userService.updateStatus(id, UserService.STATUS_NORMAL);
                    response.sendRedirect("user_manage_info.do");
                    return;
                } else if (uStatus == UserService.STATUS_NOT_APPROVED) {
                    userService.updateStatus(id, UserService.STATUS_NOT_APPROVED);
                    response.sendRedirect("user_manage_info.do");
                    return;
                } else if (uStatus == UserService.STATUS_FORBIDDEN) {
                    userService.updateStatus(id, UserService.STATUS_FORBIDDEN);
                    response.sendRedirect("user_manage_info.do");
                    return;
                } else {
                    response.sendRedirect("admin_index.jsp");
                    return;
                }
            }
        }
        response.sendRedirect("admin_index.jsp");
    }
}
