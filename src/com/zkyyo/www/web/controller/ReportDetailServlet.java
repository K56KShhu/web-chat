package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.bean.po.ReplyPo;
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
        name = "ReportDetailServlet",
        urlPatterns = {"/report_detail.do"}
)
public class ReportDetailServlet extends HttpServlet {
    private static final int DETAIL_CHAT = 0;
    private static final int DETAIL_SHARE_IMAGE = 1;
    private static final int DETAIL_SHARE_FILE = 2;

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
            int topicId;
            ReplyPo reply;
            FilePo file;
            ReplyService replyService;
            FileService fileService;
            switch (type) {
                case DETAIL_CHAT:
                    replyService = (ReplyService) getServletContext().getAttribute("replyService");
                    reply = replyService.findReply(id);
                    topicId = reply.getTopicId();
                    response.sendRedirect("topic_chat_info.do?topicId=" + topicId);
                    break;
                case DETAIL_SHARE_IMAGE:
                    fileService = (FileService) getServletContext().getAttribute("fileService");
                    file = fileService.findFile(id);
                    topicId = file.getTopicId();
                    response.sendRedirect("file_list.do?topicId=" + topicId + "&shareType=image");
                    break;
                case DETAIL_SHARE_FILE:
                    fileService = (FileService) getServletContext().getAttribute("fileService");
                    file = fileService.findFile(id);
                    topicId = file.getTopicId();
                    response.sendRedirect("file_list.do?topicId=" + topicId + "&shareType=file");
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
