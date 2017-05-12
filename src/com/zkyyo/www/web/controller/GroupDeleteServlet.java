package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "GroupDeleteServlet",
        urlPatterns = {"/group_delete.do"}
)
public class GroupDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        if (groupService.isValidId(groupId)) {
            int gId = Integer.valueOf(groupId);
            groupService.deleteGroup(gId);
        }
        response.sendRedirect("group_manage_info.do");
    }
}
