package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.UserPo;
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

/**
 * 该Servlet用于处理更新用户信息的请求
 */
@WebServlet(
        name = "UserUpdateServlet",
        urlPatterns = {"/user_update.do"}
)
public class UserUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password"); //密码
        String confirmedPsw = request.getParameter("confirmedPsw"); //二次密码
        String sex = request.getParameter("sex"); //性别
        String email = request.getParameter("email"); //邮箱

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<String> errors = new ArrayList<>();
        UserPo updatedUser = new UserPo();
        //判断是否更改
        if (password != null && password.length() > 0) {
            //判断密码是否合法, 且二次密码是否相同
            if (!userService.isValidPassword(password, confirmedPsw)) {
                errors.add("bad password");
            } else {
                updatedUser.setPassword(password);
            }
        }
        //判断是否更改
        if (sex != null && sex.length() > 0) {
            //判断性别是否合法
            if (!userService.isValidSex(sex)) {
                errors.add("bad sex");
            } else {
                updatedUser.setSex(sex);
            }
        }
        //判断是否更改
        if (email != null && email.length() > 0) {
            //判断邮箱是否合法
            if (!userService.isValidEmail(email)) {
                errors.add("bad email");
            } else {
                updatedUser.setEmail(email);
            }
        }

        //判断输入是否有误
        if (errors.isEmpty()) {
            Access access = (Access) request.getSession().getAttribute("access");
            updatedUser.setUserId(access.getUserId());
            userService.update(updatedUser);
            request.setAttribute("user", updatedUser);
        } else {
            //回显输入信息
            UserPo u = new UserPo();
            u.setSex(sex);
            u.setEmail(email);
            request.setAttribute("user", u);
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("user_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
