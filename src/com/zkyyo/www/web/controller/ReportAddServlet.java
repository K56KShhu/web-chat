package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.ReportPo;
import com.zkyyo.www.dao.impl.ReportDaoJdbcImpl;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;
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

        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        int id;
        int type;
        if ("reply".equals(contentType)) {
            if (!replyService.isValidId(contentId) || !replyService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("index.jsp");
                return;
            } else {
                id = Integer.valueOf(contentId);
                type = ReportDaoJdbcImpl.CONTENT_TYPE_REPLY;
            }
        } else if ("image".equals(contentType)) {
            if (!fileService.isValidId(contentId) || !fileService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("index.jsp");
                return;
            } else {
                id = Integer.valueOf(contentId);
                type = ReportDaoJdbcImpl.CONTENT_TYPE_SHARE_IMAGE;
            }
        } else if ("file".equals(contentType)) {
            if (!fileService.isValidId(contentId) || !fileService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("index.jsp");
                return;
            } else {
                id = Integer.valueOf(contentId);
                type = ReportDaoJdbcImpl.CONTENT_TYPE_SHARE_FILE;
            }
        } else {
            response.sendRedirect("index.jsp");
            return;
        }

        List<String> errors = new ArrayList<>();
        if (!reportService.isValidReason(reason)) {
            errors.add("bad reason");
        }
        if (errors.isEmpty()) {
            ReportPo report = new ReportPo();
            report.setContentId(id);
            report.setContentType(type);
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
