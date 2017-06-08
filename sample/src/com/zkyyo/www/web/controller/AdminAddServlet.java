package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.UserService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理添加admin的请求
 */
@WebServlet(
        name = "AdminAddServlet",
        urlPatterns = {"/admin_add.do"}
)
public class AdminAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId"); //待提升为admin的用户ID
        String rootPwd = request.getParameter("root"); //root用户的密码
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //判断用户是否存在
        if (!userService.isValidUserId(userId) || !userService.isUserExisted(Integer.valueOf(userId))) {
            response.sendRedirect("admin_index.jsp");
            return;
        }

        int uId = Integer.valueOf(userId);
        String message;
        //判断用户是否已经是admin
        if (userService.isUserInRole(uId, "admin")) {
            message = "加冕失败, 该用户已是管理员";
        } else {
            boolean isAdded = userService.addAdmin(access.getUserId(), rootPwd, uId);
            //判断是否添加成功
            if (isAdded) {
                message = "加冕成功";
            } else {
                message = "加冕失败, root输入有误";
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
