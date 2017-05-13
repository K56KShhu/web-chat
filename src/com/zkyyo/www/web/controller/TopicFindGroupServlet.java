package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "TopicFindGroupServlet",
        urlPatterns = {"/topic_find_group.do"}
)
public class TopicFindGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");

        if (search != null && search.trim().length() > 0) {
            GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
            List<GroupPo> groups = groupService.queryGroups(search);
            request.setAttribute("groups", groups);
            request.setAttribute("search", search);
        }
        request.getRequestDispatcher("topic_find_group.jsp").forward(request, response);
    }
}
