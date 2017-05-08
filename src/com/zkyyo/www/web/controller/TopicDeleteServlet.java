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

@WebServlet(
        name = "TopicDeleteServlet",
        urlPatterns = {"/topic_delete.do"}
)
public class TopicDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");

        String message = "讨论区不存在";
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            if (topicService.isExisted(tId)) {
                String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
                String topicPath = bathPath + "/topic#" + topicId;
                FileUtils.deleteDirectory(new File(topicPath));
                topicService.deleteTopic(tId);
                message = "讨论区删除成功";
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}