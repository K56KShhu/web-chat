package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "GroupAddServlet",
        urlPatterns = {"/group_add.do"}
)
public class GroupAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        List<String> errors = new ArrayList<>();
        if (!groupService.isValidName(name)) {
            errors.add("bad name");
        }
        if (!groupService.isValidDesc(description)) {
            errors.add("bad description");
        }
        if (errors.isEmpty()) {
            GroupPo group = new GroupPo();
            group.setName(name);
            group.setDescription(description);
            groupService.addGroup(group);
        } else {
            request.setAttribute("name", name);
            request.setAttribute("description", description);
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("group_add.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
