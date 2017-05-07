package com.zkyyo.www.view;

import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ReportContentDeleteServlet",
        urlPatterns = {"/report_content_delete.do"}
)
public class ReportContentDeleteServlet extends HttpServlet {
    private static final int DELETE_CHAT = 0;
    private static final int DELETE_SHARE_IMAGE = 1;
    private static final int DELETE_SHARE_FILE = 2;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentId = request.getParameter("contentId");
        String contentType = request.getParameter("contentType");

        ReportService reportService = (ReportService) getServletContext().getAttribute("reportService");
        //待修改!!!!
        if (reportService.isValidId(contentId) && reportService.isValidContentType(contentType)) {
            int id = Integer.valueOf(contentId);
            int type = Integer.valueOf(contentType);
            switch (type) {
                case DELETE_CHAT:
                    response.sendRedirect("reply_delete.do?replyId=" + id);
                    break;
                case DELETE_SHARE_IMAGE:
                case DELETE_SHARE_FILE:
                    response.sendRedirect("file_delete.do?fileId=" + id);
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
