package com.zkyyo.www.web.controller;

import com.zkyyo.www.po.ReportPo;
import com.zkyyo.www.service.ReportService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "ReportAddServlet",
        urlPatterns = {"/report_add.do"}
)
public class ReportAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentId = request.getParameter("contentId");
        String contentType = request.getParameter("contentType");
        String reason = request.getParameter("reason");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        List<String> errors = new ArrayList<>();
        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        //待检查输入的contentId合法性
        if (!reportService.isValidContentType(contentType) || !reportService.isValidId(contentId)) {
            response.sendRedirect("index.jsp");
            return;
        }
        if (!reportService.isValidReason(reason)) {
            errors.add("bad reason");
        }
        if (errors.isEmpty()) {
            ReportPo report = new ReportPo();
            report.setContentId(Integer.valueOf(contentId));
            report.setContentType(Integer.valueOf(contentType));
            report.setReason(reason);
            report.setUserId(userId);
            reportService.addReport(report);
        } else {
            request.setAttribute("reason", reason);
            request.setAttribute("contentId", contentId);
            request.setAttribute("contentType", contentType);
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("report_add.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
