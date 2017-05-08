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
        name = "UserUpdateServlet",
        urlPatterns = {"/user_update.do"}
)
public class UserUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String confirmedPsw = request.getParameter("confirmedPsw");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<String> errors = new ArrayList<>();
        UserPo updatedUser = new UserPo();
        if (password != null && password.length() > 0) {
            if (!userService.isValidPassword(password, confirmedPsw)) {
                errors.add("bad password");
            } else {
                updatedUser.setPassword(password);
            }
        }
        if (sex != null && sex.length() > 0) {
            if (!userService.isValidSex(sex)) {
                errors.add("bad sex");
            } else {
                updatedUser.setSex(sex);
            }
        }
        if (email != null && email.length() > 0) {
            if (!userService.isValidEmail(email)) {
                errors.add("bad email");
            } else {
                updatedUser.setEmail(email);
            }
        }

        if (!errors.isEmpty()) {
            UserPo u = new UserPo();
            u.setSex(sex);
            u.setEmail(email);
            request.setAttribute("user", u);
        } else {
            Access access = (Access) request.getSession().getAttribute("access");
            updatedUser.setUserId(access.getUserId());
            userService.update(updatedUser);
            request.setAttribute("user", updatedUser);
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("user_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
