package com.zkyyo.www.view;

import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        Access access = (Access) request.getSession().getAttribute("access");
//        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            if (topicService.isExisted(tId)) {
                topicService.deleteTopic(tId);
                request.setAttribute("message", "删除讨论区成功");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "该讨论区不存在");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        response.sendRedirect("index.jsp");
    }
}
