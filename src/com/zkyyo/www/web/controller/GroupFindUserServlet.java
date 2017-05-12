package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "GroupFindUserServlet",
        urlPatterns = {"/group_find_user.do"}
)
public class GroupFindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");

        if (search != null && search.trim().length() > 0) {
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            List<UserPo> users = userService.fuzzySearchUsers(search);
            request.setAttribute("search", search);
            request.setAttribute("users", users);
        }
        request.getRequestDispatcher("group_find_user.jsp").forward(request, response);
    }
}
