package com.zkyyo.www.view;

import com.zkyyo.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> errors = new ArrayList<>();
        if (!userService.isValidUserId(userId) || !userService.isUserExisted(Integer.valueOf(userId))) {
            errors.add("用户不存在");
        }
        int id = Integer.valueOf(userId);
        if ("true".equals(isApproved)) {
            userService.updateStatus(id, UserService.STATUS_APPROVED);
        } else if ("false".equals(isApproved)) {
            userService.updateStatus(id, UserService.STATUS_NOT_APPROVED);
        } else {
            errors.add("操作无效");
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("admin_audit_info.do").forward(request, response);
    }
}
