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

/**
 * 该Servlet用于处理root用户精确查询用户的请求
 */
@WebServlet(
        name = "AdminFindServlet",
        urlPatterns = {"/admin_find.do"}
)
public class AdminFindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); //精确查询的用户名
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo userPo = userService.findUser(username);
        //判断用户是否存在
        if (userPo != null) {
            //判断用户是否已经是admin
            if (userService.isUserInRole(userPo.getUserId(), "admin")) {
                request.setAttribute("isAdmin", true);
            }
            //将UserPo转换为UserVo
            UserVo userVo = BeanUtil.userPoToVo(userPo);
            request.setAttribute("user", userVo);
        }
        request.setAttribute("username", username);
        request.getRequestDispatcher("admin_find.jsp").forward(request, response);
    }
}
