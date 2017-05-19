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
        //大号
        UserPo user = new UserPo();
        user.setUsername("101");
        user.setPassword("abc");
        user.setSex("male");
        user.setEmail("z@o.com");
        userService.addUser(user);
        userService.updateStatus(userService.findUser("101").getUserId(), UserService.STATUS_NORMAL);

        //小号
        for (int i = 0; i < 131; i++) {
            user = new UserPo();
            user.setUsername("aaa12345" + i);
            user.setPassword("abc");
            user.setSex("male");
            user.setEmail("z@o.com");
            userService.addUser(user);
            userService.updateStatus(userService.findUser("aaa12345" + i).getUserId(), UserService.STATUS_NORMAL);
        }
        request.setAttribute("message", "finish");
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
