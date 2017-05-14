package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;

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
    private static final String DETAIL_REPLY = "0";
    private static final String DETAIL_SHARE_IMAGE = "1";
    private static final String DETAIL_SHARE_FILE = "2";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentId = request.getParameter("contentId");
        String contentType = request.getParameter("contentType");

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        if (DETAIL_REPLY.equals(contentType)) {
            if (replyService.isValidId(contentId) && replyService.isExisted(Integer.valueOf(contentId))) {
                ReplyPo replyPo = replyService.findReply(Integer.valueOf(contentId));
                response.sendRedirect("topic_chat_info.do?topicId=" + replyPo.getTopicId());
                return;
            }
        } else if (DETAIL_SHARE_IMAGE.equals(contentType)) {
            if (fileService.isValidId(contentId) && fileService.isExisted(Integer.valueOf(contentId))) {
                FilePo filePo = fileService.findFile(Integer.valueOf(contentId));
                response.sendRedirect("file_list.do?topicId=" + filePo.getTopicId() + "&shareType=image");
                return;
            }
        } else if (DETAIL_SHARE_FILE.equals(contentType)) {
            if (fileService.isValidId(contentId) && fileService.isExisted(Integer.valueOf(contentId))) {
                FilePo filePo = fileService.findFile(Integer.valueOf(contentId));
                response.sendRedirect("file_list.do?topicId=" + filePo.getTopicId() + "&shareType=file");
                return;
            }
        }
        response.sendRedirect("index.jsp");

        /*
        if (contentId == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        if (DETAIL_REPLY.equals(contentType)) {
            response.sendRedirect("topic_chat_info.do?topicId=" + contentId);
        } else if (DETAIL_SHARE_IMAGE.equals(contentType)) {
            response.sendRedirect("file_list.do?topicId=" + contentId + "&shareType=image");
        } else if (DETAIL_SHARE_FILE.equals(contentType)) {
            response.sendRedirect("file_list.do?topicId=" + contentId + "&shareType=file");
        } else {
            response.sendRedirect("index.jsp");
        }
        */
        /*
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
        */
    }
}
