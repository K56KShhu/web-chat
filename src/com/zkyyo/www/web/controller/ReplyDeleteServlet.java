package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.service.ReplyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(
        name = "ReplyDeleteServlet",
        urlPatterns = {"/reply_delete.do"}
)
public class ReplyDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String replyId = request.getParameter("replyId");

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        String message = "讨论区信息不存在";
        if (replyService.isValidId(replyId)) {
            int rId = Integer.valueOf(replyId);
            if (replyService.isExisted(rId)) {
                ReplyPo reply = replyService.findReply(rId);
                System.out.println(reply);
                int contentType = reply.getContentType();
                switch (contentType) {
                    case ReplyService.CONTENT_TEXT:
                        replyService.deleteReply(reply.getReplyId());
                        message = "删除讨论区信息成功";
                        break;
                    case ReplyService.CONTENT_IMAGE:
                        String relativePath = reply.getContent();
                        String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
                        String absolutePath = bathPath + relativePath;
                        File f = new File(absolutePath);
                        if (f.exists()) {
                            boolean isDeleted = f.delete();
                            if (isDeleted) {
                                replyService.deleteReply(reply.getReplyId());
                                message = "删除讨论区信息成功";
                            } else {
                                message = "删除讨论区信息失败";
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
