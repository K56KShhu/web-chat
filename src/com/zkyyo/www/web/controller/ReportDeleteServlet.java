package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ReportDeleteServlet",
        urlPatterns = {"/report_delete.do"}
)
public class ReportDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportId = request.getParameter("reportId");

        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        if (reportService.isValidId(reportId)) {
            int rId = Integer.valueOf(reportId);
            if (reportService.isExisted(rId)) {
                reportService.deleteReport(rId);
                request.getRequestDispatcher("report_manage_info.do").forward(request, response);
            } else {
                request.setAttribute("message", "举报不存在, 或已经被处理");
                request.getRequestDispatcher("message.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
