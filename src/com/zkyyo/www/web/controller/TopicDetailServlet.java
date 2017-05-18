package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.bean.vo.TopicVo;
import com.zkyyo.www.service.GroupService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "TopicDetailServlet",
        urlPatterns = {"/topic_detail.do"}
)
public class TopicDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        int tId = Integer.valueOf(topicId);
        TopicPo topicPo = topicService.findTopic(tId);
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        UserPo creator = userService.getUser(topicPo.getCreatorId());
        UserPo modifier = userService.getUser(topicPo.getLastModifyId());
        TopicVo topicVo = BeanUtil.topicPoToVo(topicPo, creator, modifier);

        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        List<GroupPo> groups = groupService.queryGroupsByTopic(tId);

        request.setAttribute("topic", topicVo);
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("topic_detail.jsp").forward(request, response);
    }
}
