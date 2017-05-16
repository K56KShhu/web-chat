package com.zkyyo.www.web.test;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AddUserTestServlet",
        urlPatterns = {"/add_user.do"}
)
public class AddUserTestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        for (int i = 0; i < 134; i++) {
            UserPo user = new UserPo();
            user.setUsername("1234567" + i);
            user.setPassword("abc");
            user.setSex("male");
            user.setEmail("z@o.com");
            userService.addUser(user);
            userService.updateStatus(userService.getUser("101").getUserId(), UserService.STATUS_NORMAL);
        }
        request.setAttribute("message", "finish");
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}