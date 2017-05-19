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

/**
 * 该Servlet用于处理添加举报的请求
 */
@WebServlet(
        name = "ReportAddServlet",
        urlPatterns = {"/report_add.do"}
)
public class ReportAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentId = request.getParameter("contentId"); //举报内容ID
        String contentType = request.getParameter("contentType"); //举报类型
        String reason = request.getParameter("reason"); //举报原因
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象
        int userId = access.getUserId();

        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        int id;
        int type;
        //判断举报类型
        if ("reply".equals(contentType)) {
            //举报回复信息
            //判断回复是否存在
            if (!replyService.isValidId(contentId) || !replyService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("index.jsp");
                return;
            } else {
                id = Integer.valueOf(contentId);
                type = ReportDaoJdbcImpl.CONTENT_TYPE_REPLY;
            }
        } else if ("image".equals(contentType)) {
            //举报分享区图片
            //判断分享区图片是否存在
            if (!fileService.isValidId(contentId) || !fileService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("index.jsp");
                return;
            } else {
                id = Integer.valueOf(contentId);
                type = ReportDaoJdbcImpl.CONTENT_TYPE_SHARE_IMAGE;
            }
        } else if ("file".equals(contentType)) {
            //举报分享区文件
            //判断分享区文件是否存在
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
        //判断举报原因是否合法
        if (!reportService.isValidReason(reason)) {
            errors.add("bad reason");
        }
        //判断输入是否有误
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
