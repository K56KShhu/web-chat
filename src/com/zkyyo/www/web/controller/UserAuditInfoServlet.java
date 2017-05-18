/*
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

//暂时不使用该Servlet: UserManageServlet已有类似功能
@WebServlet(
        name = "UserAuditInfoServlet"
//        urlPatterns = {"/user_audit_info.do"}
)
public class UserAuditInfoServlet extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        PageBean<UserPo> pageBean = userService.queryUsersByStatus(currentPage, UserService.STATUS_AUDIT);
        request.setAttribute("pageBean", pageBean);
        request.getRequestDispatcher("user_audit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}
*/
