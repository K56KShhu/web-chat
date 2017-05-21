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
        name = "TestUserServlet",
        urlPatterns = {"/test_user.do"}
)
public class TestUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");

        //添加大量user账号
        for (int i = 0; i < 255; i++) {
            UserPo user = new UserPo();
            user.setUsername("aaa12345" + i);
            user.setPassword("abc");
            user.setSex("male");
            if (Math.random() > 0.5) {
                user.setSex("female");
            }
            user.setEmail("aaa12345" + i + "@zan.com");
            userService.addUser(user);
            userService.updateStatus(userService.findUser("aaa12345" + i).getUserId(), UserService.STATUS_NORMAL);
        }
        request.setAttribute("message", "finish");
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
