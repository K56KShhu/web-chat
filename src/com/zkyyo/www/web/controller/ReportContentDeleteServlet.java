package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;

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
    private static final String DELETE_CHAT = "0";
    private static final String DELETE_SHARE_IMAGE = "1";
    private static final String DELETE_SHARE_FILE = "2";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentId = request.getParameter("contentId");
        String contentType = request.getParameter("contentType");

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        if (DELETE_CHAT.equals(contentType)) {
            if (replyService.isValidId(contentId) && replyService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("reply_delete.do?replyId=" + contentId);
                return;
            }
        } else if (DELETE_SHARE_IMAGE.equals(contentType) || DELETE_SHARE_FILE.equals(contentType)) {
            if (fileService.isValidId(contentId) && fileService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("file_delete.do?fileId=" + contentId);
                return;
            }
        }
        response.sendRedirect("index.jsp");
    }
}
