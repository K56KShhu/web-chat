package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet用于处理删除举报内容的请求
 */
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
        String contentId = request.getParameter("contentId"); //举报内容ID
        String contentType = request.getParameter("contentType"); //举报类型

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        //判断举报类型, 从而重定向到处理该类型的Servlet上
        if (DELETE_CHAT.equals(contentType)) {
            //删除回复信息
            //判断回复是否存在
            if (replyService.isValidId(contentId) && replyService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("reply_delete.do?replyId=" + contentId);
                return;
            }
        } else if (DELETE_SHARE_IMAGE.equals(contentType) || DELETE_SHARE_FILE.equals(contentType)) {
            //删除分享区图片/文件
            //判断分享区图片/文件是否存在
            if (fileService.isValidId(contentId) && fileService.isExisted(Integer.valueOf(contentId))) {
                response.sendRedirect("file_delete.do?fileId=" + contentId);
                return;
            }
        }
        response.sendRedirect("index.jsp");
    }
}
