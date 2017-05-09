package com.zkyyo.www.web.controller;

import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "UserManageInfoServlet",
        urlPatterns = {"/user_manage_info.do"}
)
public class UserManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<UserPo> users = userService.getUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("user_manage.jsp").forward(request, response);
    }
}
