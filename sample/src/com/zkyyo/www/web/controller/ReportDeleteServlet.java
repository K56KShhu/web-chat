package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理删除举报的请求
 */
@WebServlet(
        name = "ReportDeleteServlet",
        urlPatterns = {"/report_delete.do"}
)
public class ReportDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportId = request.getParameter("reportId"); //举报ID
        //用于在重定向时, 保存分页的状态
        String order = request.getParameter("order"); //举报排序依据
        String page = request.getParameter("page"); //删除该举报时的页数
        String isReverse = request.getParameter("isReverse"); //是否降序

        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        //判断举报ID是否合法
        if (reportService.isValidId(reportId)) {
            int rId = Integer.valueOf(reportId);
            //判断举报是否存在
            if (reportService.isExisted(rId)) {
                reportService.deleteReport(rId);
                String url = "report_manage_info.do?order=" + order + "&page=" + page + "&isReverse=" +isReverse;
                response.sendRedirect(url);
            } else {
                request.setAttribute("message", "举报不存在, 或已经被处理");
                request.getRequestDispatcher("message.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
