package com.zkyyo.www.web.controller;

import com.zkyyo.www.po.UserPo;
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

@WebServlet(
        name = "Login",
        urlPatterns = {"/login.do"}
)
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        List<String> errors = new ArrayList<>();
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //检查信息合法性
        if (!userService.isValidUsername(username) || !userService.isValidPassword(password)) {
            errors.add("用户名或密码输入有误");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        //若信息输入合法, 开始比对数据库中信息
        UserPo user = new UserPo(username, password);
        if (userService.checkLogin(user)) {
            Access access = userService.getAccess(username);
            if (access.getStatus() == UserService.STATUS_APPROVED) {
                request.getSession().setAttribute("access", access);
                response.sendRedirect("index.jsp");
            } else if (access.getStatus() == UserService.STATUS_AUDIT) {
                errors.add("该账号正在审核中");
            } else if (access.getStatus() == UserService.STATUS_NOT_APPROVED) {
                errors.add("该账号审核不通过");
            }
        } else {
            errors.add("用户名或密码输入有误");
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
