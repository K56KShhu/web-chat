package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 该Servlet用于处理注册的请求
 */
@WebServlet(
        name = "RegisterServlet",
        urlPatterns = {"/register.do"}
)
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); //用户名
        String password = request.getParameter("password"); //密码
        String confirmedPsw = request.getParameter("confirmedPsw"); //二次密码
        String sex = request.getParameter("sex"); //性别
        String email = request.getParameter("email"); //邮箱
        boolean isAgreed = "true".equals(request.getParameter("isAgreed")); //是否同意条约 true同意, false不同意

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<String> errors = new ArrayList<>();
        //判断用户名是否合法
        if (!userService.isValidUsername(username)) {
            errors.add("用户名不符合格式, 长度至少3位");
        } else {
            //判断用户名是否存在
            if (userService.isUserExisted(username)) {
                errors.add("用户名已被注册");
            }
        }
        //判断密码是否合法
        if (!userService.isValidPassword(password)) {
            errors.add("密码长度不正确");
        } else {
            //判断两次密码是否一致
            if (!userService.isSamePassword(password, confirmedPsw)) {
                errors.add("两次输入的密码不一致");
            }
        }
        //判断性别是否合法
        if (!userService.isValidSex(sex)) {
            errors.add("性别输入有误");
        }
        //判断邮箱是否合法
        if (!userService.isValidEmail(email)) {
            errors.add("邮箱格式有误");
        }
        if (!isAgreed) {
            errors.add("我们的约定呢?");
        }

        //判断是否输入有误
        if (errors.isEmpty()) {
            UserPo user = new UserPo();
            user.setUsername(username);
            user.setPassword(password);
            user.setSex(sex);
            user.setEmail(email);
            userService.addUser(user);
        } else {
            request.setAttribute("username", username);
            request.setAttribute("sex", sex);
            request.setAttribute("email", email);
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
