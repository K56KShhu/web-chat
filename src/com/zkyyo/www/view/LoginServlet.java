package com.zkyyo.www.view;

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

//        List<String> errors = new ArrayList<>();
//        String page = "login.jsp";
//        UserPo user = new UserPo(username, password);
//        UserService userService = (UserService) getServletContext().getAttribute("userService");
//        if (userService.checkLogin(user)) {
//            Access access = userService.getAccess(username);
//            request.getSession().setAttribute("access", access);
//            page = "index.jsp";
//        } else {
//            errors.add("用户名或密码输入有误");
//        }
//        request.getRequestDispatcher(page).forward(request, response);
        List<String> errors = new ArrayList<>();
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (!userService.isValidUsername(username) || !userService.isValidPassword(password)) {
            errors.add("用户名或密码输入有误");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        UserPo user = new UserPo(username, password);
        if (userService.checkLogin(user)) {
            Access access = userService.getAccess(username);
            request.getSession().setAttribute("access", access);
            response.sendRedirect("index.jsp");
        } else {
            errors.add("用户名或密码输入有误");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
