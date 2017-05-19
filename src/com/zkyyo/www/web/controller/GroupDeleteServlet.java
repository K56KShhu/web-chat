package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理删除小组信息的请求
 */
@WebServlet(
        name = "GroupDeleteServlet",
        urlPatterns = {"/group_delete.do"}
)
public class GroupDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId"); //小组ID

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        //判断小组ID是否合法
        if (groupService.isValidId(groupId)) {
            int gId = Integer.valueOf(groupId);
            groupService.deleteGroup(gId);
        }
        response.sendRedirect("group_manage_info.do");
    }
}
