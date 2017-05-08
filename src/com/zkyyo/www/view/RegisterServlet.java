package com.zkyyo.www.view;

import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "RegisterServlet",
        urlPatterns = {"/register.do"}
)
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmedPsw = request.getParameter("confirmedPsw");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<String> errors = new ArrayList<>();
        if (!userService.isValidUsername(username)) {
            errors.add("bad username");
        }
        if (!userService.isValidPassword(password, confirmedPsw)) {
            errors.add("bad password");
        }
        if (!userService.isValidSex(sex)) {
            errors.add("bad sex");
        }
        if (!userService.isValidEmail(email)) {
            errors.add("bad email");
        }

        if (errors.isEmpty()) {
            UserPo user = new UserPo();
            user.setUsername(username);
            user.setPassword(password);
            user.setSex(sex);
            user.setEmail(email);
            user.setStatus(UserService.STATUS_AUDIT); //待审核状态
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

    }
}
