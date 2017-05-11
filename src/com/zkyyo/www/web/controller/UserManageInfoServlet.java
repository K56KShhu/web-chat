package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "UserManageInfoServlet",
        urlPatterns = {"/user_manage_info.do"}
)
public class UserManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String search = request.getParameter("search");
//        UserService userService = (UserService) getServletContext().getAttribute("userService");
//        List<UserPo> users;
//        if (search != null && search.trim().length() > 0) {
//            users = userService.getUsers();
//        } else {
//            users = userService.fuzzySearchUsers(search);
//    }
//        request.setAttribute("users", users);
//        request.getRequestDispatcher("user_manage.jsp").forward(request, response);
        String page = request.getParameter("page");
        String search = request.getParameter("search");
        String order = request.getParameter("order");
        String isReverseStr = request.getParameter("isReverse");

        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        Boolean isReverse = "true".equals(isReverseStr);
        PageBean<UserPo> pageBean;
        if (search != null && search.trim().length() > 0) {
            pageBean = userService.queryUsers(currentPage, search);
        } else {
            if ("sex".equals(order)) {
                pageBean = userService.queryUsers(currentPage, UserService.ORDER_BY_SEX, isReverse);
            } else if ("created".equals(order)) {
                pageBean = userService.queryUsers(currentPage, UserService.ORDER_BY_CREATED, isReverse);
            } else if ("status".equals(order)) {
                pageBean = userService.queryUsers(currentPage, UserService.ORDER_BY_STATUS, isReverse);
            } else {
                //默认显示最新加入的用户
                pageBean = userService.queryUsers(currentPage, UserService.ORDER_BY_CREATED, true);
            }
        }
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("isReverse", isReverse);
        request.setAttribute("pageBean", pageBean);
        request.getRequestDispatcher("user_manage.jsp").forward(request, response);
    }
}
