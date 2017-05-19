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

/**
 * 该Servlet用于处理获取举报内容位置的请求
 */
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
        String contentId = request.getParameter("contentId"); //举报内容ID
        String contentType = request.getParameter("contentType"); //举报类型

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        //判断举报类型
        if (DETAIL_REPLY.equals(contentType)) {
            //举报回复
            //判断回复是否存在
            if (replyService.isValidId(contentId) && replyService.isExisted(Integer.valueOf(contentId))) {
                ReplyPo replyPo = replyService.findReply(Integer.valueOf(contentId));
                response.sendRedirect("topic_chat_info.do?topicId=" + replyPo.getTopicId());
                return;
            }
        } else if (DETAIL_SHARE_IMAGE.equals(contentType)) {
            //举报分享区图片
            //判断分享区图片是否存在
            if (fileService.isValidId(contentId) && fileService.isExisted(Integer.valueOf(contentId))) {
                FilePo filePo = fileService.findFile(Integer.valueOf(contentId));
                response.sendRedirect("file_list.do?topicId=" + filePo.getTopicId() + "&shareType=image");
                return;
            }
        } else if (DETAIL_SHARE_FILE.equals(contentType)) {
            //判断分享区文件
            //判断分享区文件是否存在
            if (fileService.isValidId(contentId) && fileService.isExisted(Integer.valueOf(contentId))) {
                FilePo filePo = fileService.findFile(Integer.valueOf(contentId));
                response.sendRedirect("file_list.do?topicId=" + filePo.getTopicId() + "&shareType=file");
                return;
            }
        }
        response.sendRedirect("index.jsp");
    }
}
