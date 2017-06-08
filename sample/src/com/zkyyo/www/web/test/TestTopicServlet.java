package com.zkyyo.www.web.test;

import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TestTopicServlet",
        urlPatterns = {"/test_topic.do"}
)
public class TestTopicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        try {
            //添加大量讨论区
            for (int i = 0; i < 255; i++) {
                TopicPo topic = new TopicPo();
                if (Math.random() > 0.5) {
                    topic.setTitle("公开讨论区分页测试" + i + "号");
                    topic.setIsPrivate(TopicService.ACCESS_PUBLIC);
                } else {
                    topic.setTitle("授权分页测试" + i + "号");
                    topic.setIsPrivate(TopicService.ACCESS_PRIVATE);
                }
                topic.setDescription("");
                topic.setCreatorId(1);
                topic.setLastModifyId(1);
                Thread.sleep((long) (Math.random() * 1000));
                topicService.addTopic(topic);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
