package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AdminAuditServlet",
        urlPatterns = {"/admin_audit.do"}
)
public class AdminAuditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String isApproved = request.getParameter("isApproved");

        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (!userService.isValidUserId(userId) || !userService.isUserExisted(Integer.valueOf(userId))) {
            response.sendRedirect("admin_index.jsp");
            return;
        }
        int id = Integer.valueOf(userId);
        if ("true".equals(isApproved)) {
            userService.updateStatus(id, UserService.STATUS_NORMAL);
        } else if ("false".equals(isApproved)) {
            userService.updateStatus(id, UserService.STATUS_NOT_APPROVED);
        } else {
            response.sendRedirect("admin_index.jsp");
            return;
        }
        request.getRequestDispatcher("admin_audit_info.do").forward(request, response);
    }
}
