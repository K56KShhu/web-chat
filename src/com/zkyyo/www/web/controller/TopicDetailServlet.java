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

/**
 * 该Servlet用于处理获取讨论区细节信息的请求
 */
@WebServlet(
        name = "TopicDetailServlet",
        urlPatterns = {"/topic_detail.do"}
)
public class TopicDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId"); //讨论区ID

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        //判断讨论区是否存在
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        int tId = Integer.valueOf(topicId);
        //获取讨论区信息
        TopicPo topicPo = topicService.findTopic(tId);
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //获取创建者用户信息
        UserPo creator = userService.findUser(topicPo.getCreatorId());
        //获取最后修改者用户信息
        UserPo modifier = userService.findUser(topicPo.getLastModifyId());
        //将TopicPo转换为TopicVo
        TopicVo topicVo = BeanUtil.topicPoToVo(topicPo, creator, modifier);
        GroupService groupService = (GroupService) getServletContext().getAttribute("groupService");
        //获取该讨论区授权的所有小组信息
        List<GroupPo> groups = groupService.queryGroupsByTopic(tId);

        request.setAttribute("topic", topicVo);
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("topic_detail.jsp").forward(request, response);
    }
}
