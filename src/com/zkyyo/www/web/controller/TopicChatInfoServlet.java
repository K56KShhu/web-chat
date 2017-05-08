package com.zkyyo.www.web.controller;

import com.zkyyo.www.po.ReplyPo;
import com.zkyyo.www.po.TopicPo;
import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.vo.ReplyVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "TopicChatInfoServlet",
        urlPatterns = {"/topic_chat_info.do"}
)
public class TopicChatInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            if (topicService.isExisted(tId)) {
                //获取主题信息
                TopicPo topic = topicService.findTopic(Integer.valueOf(topicId));
                //获取回复信息
                List<ReplyPo> replyPos = replyService.findReplys(tId);
                List<ReplyVo> replyVos = new ArrayList<>();
                UserPo userPo;
                for (ReplyPo replyPo : replyPos) {
                    userPo = userService.getUser(replyPo.getUserId());
                    replyVos.add(BeanUtil.ReplyPoToVo(replyPo, userPo));
                }
                request.setAttribute("topic", topic);
                request.setAttribute("replys", replyVos);
                request.getRequestDispatcher("topic_chat.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
