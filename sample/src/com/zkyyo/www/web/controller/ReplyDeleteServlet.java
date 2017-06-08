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

/**
 * 该Servlet用于处理删除回复信息的请求
 */
@WebServlet(
        name = "ReplyDeleteServlet",
        urlPatterns = {"/reply_delete.do"}
)
public class ReplyDeleteServlet extends HttpServlet {
    /**
     * 存放讨论区文件的根目录
     */
    private String TOPIC_DIR;

    public void init() throws ServletException {
        TOPIC_DIR = (String) getServletContext().getAttribute("topicDir");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String replyId = request.getParameter("replyId"); //待删除的回复ID

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        String message = "讨论区信息不存在";
        //判断回复ID是否合法
        if (replyService.isValidId(replyId)) {
            int rId = Integer.valueOf(replyId);
            //判断回复是否存在
            if (replyService.isExisted(rId)) {
                ReplyPo reply = replyService.findReply(rId);
                int contentType = reply.getContentType();
                //判断该回复的类型
                switch (contentType) {
                    case ReplyService.CONTENT_TEXT:
                        //删除文本
                        replyService.deleteReply(reply.getReplyId());
                        message = "删除讨论区信息成功";
                        break;
                    case ReplyService.CONTENT_IMAGE:
                        //删除图片
                        String relativePath = reply.getContent(); //图片的相对路径
                        String bathPath = getServletContext().getRealPath(TOPIC_DIR);
                        String absolutePath = bathPath + relativePath; //构建图片的绝对路径
                        File f = new File(absolutePath);
                        //判断该图片在硬盘上是否存在
                        if (f.exists()) {
                            boolean isDeleted = f.delete();
                            //判断是否删除成功
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
