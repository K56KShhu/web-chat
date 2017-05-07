package com.zkyyo.www.view;

import com.zkyyo.www.po.ReplyPo;
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
        if (replyService.isValidId(replyId)) {
            int rId = Integer.valueOf(replyId);
            if (replyService.isExisted(rId)) {
                ReplyPo reply = replyService.findReply(rId);
                System.out.println(reply);
                int contentType = reply.getContentType();
                if (contentType == ReplyService.CONTENT_TEXT) {
                    replyService.deleteReply(reply.getReplyId());
                    request.setAttribute("message", "删除讨论区信息成功");
                        request.getRequestDispatcher("success.jsp").forward(request, response);
                } else if (contentType == ReplyService.CONTENT_IMAGE) {
                    String relativePath = reply.getContent();
                    String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
                    String absolutePath = bathPath + relativePath;
                    File f = new File(absolutePath);
                    if (f.exists()) {
                        boolean isDeleted = f.delete();
                        if (isDeleted) {
                            replyService.deleteReply(reply.getReplyId());
                            request.setAttribute("message", "删除讨论区信息成功");
                            request.getRequestDispatcher("success.jsp").forward(request, response);
                        } else {
                            request.setAttribute("message", "删除讨论区信息失败");
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("message", "讨论区信息不存在");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                }
            } else {
                request.setAttribute("message", "讨论区信息不存在");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "讨论区信息不存在");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }
    }
}
