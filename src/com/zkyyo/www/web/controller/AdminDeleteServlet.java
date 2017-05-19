package com.zkyyo.www.web.controller;

import com.zkyyo.www.dao.impl.UserDaoJdbcImpl;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理移除用户admin角色的请求
 */
@WebServlet(
        name = "AdminDeleteServlet",
        urlPatterns = {"/admin_delete.do"}
)
public class AdminDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId"); //待移除admin的用户ID
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //判断用户是否存在
        if (!userService.isValidUserId(userId) || !userService.isUserExisted(Integer.valueOf(userId))) {
            response.sendRedirect("admin_index.jsp");
            return;
        }

        int uId = Integer.valueOf(userId);
        //判断操作者操作对象是否为自己
        if (access.getUserId() != uId) {
            userService.removeRoleInUser(uId, UserDaoJdbcImpl.ROLE_ADMIN);
        }
        response.sendRedirect("admin_manage_info.do");
    }
}
