package com.zkyyo.www.web.controller;

import com.zkyyo.www.service.TopicService;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 该Servlet用于处理删除讨论区的请求
 */
@WebServlet(
        name = "TopicDeleteServlet",
        urlPatterns = {"/topic_delete.do"}
)
public class TopicDeleteServlet extends HttpServlet {
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
        String topicId = request.getParameter("topicId"); //讨论区ID

        String message = "讨论区不存在";
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        //判断讨论区ID是否合法
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            //判断讨论区是否存在
            if (topicService.isExisted(tId)) {
                String bathPath = getServletContext().getRealPath(TOPIC_DIR); //存放讨论区文件的根目录
                String topicPath = bathPath + "/topic#" + topicId; //该讨论区的绝对路径
                //删除该讨论区在硬盘的一切数据
                FileUtils.deleteDirectory(new File(topicPath));
                //删除该讨论区在数据库的数据
                topicService.deleteTopic(tId);
                message = "讨论区删除成功";
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
