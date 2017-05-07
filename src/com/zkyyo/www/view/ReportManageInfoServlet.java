package com.zkyyo.www.view;

import com.zkyyo.www.po.ReportPo;
import com.zkyyo.www.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "ReportManageInfoServlet",
        urlPatterns = {"/report_manage_info.do"}
)
public class ReportManageInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        List<ReportPo> reports = reportService.findReports();
        request.setAttribute("reports", reports);
        request.getRequestDispatcher("report_manage.jsp").forward(request, response);
    }
}
