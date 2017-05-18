package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.bean.vo.UserVo;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AdminFindServlet",
        urlPatterns = {"/admin_find.do"}
)
public class AdminFindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.getUser(username);
        if (userPo != null) {
            UserVo userVo = BeanUtil.userPoToVo(userPo);
            request.setAttribute("user", userVo);
        }
        request.getRequestDispatcher("admin_find.jsp").forward(request, response);
    }
}
