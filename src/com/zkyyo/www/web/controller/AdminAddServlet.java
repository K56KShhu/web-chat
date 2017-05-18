package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.UserService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AdminAddServlet",
        urlPatterns = {"/admin_add.do"}
)
public class AdminAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String rootPwd = request.getParameter("root");
        Access access = (Access) request.getSession().getAttribute("access");

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (!userService.isValidUserId(userId) || !userService.isUserExisted(Integer.valueOf(userId))) {
            response.sendRedirect("admin_index.jsp");
            return;
        }

        int uId = Integer.valueOf(userId);
        boolean isAdded = userService.addAdmin(access.getUserId(), rootPwd, uId);
        String message;
        if (isAdded) {
            message = "加冕成功";
        } else {
            message = "加冕失败";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
