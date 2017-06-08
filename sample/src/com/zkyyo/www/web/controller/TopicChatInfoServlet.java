package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.bean.vo.ReplyVo;
import com.zkyyo.www.util.CheckUtil;
import com.zkyyo.www.web.Access;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 该Servlet用于获取讨论区聊天信息的请求
 */
@WebServlet(
        name = "TopicChatInfoServlet",
        urlPatterns = {"/topic_chat_info.do"}
)
public class TopicChatInfoServlet extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId"); //讨论区ID
        String page = request.getParameter("page"); //请求页数
        if (topicId == null) {
            topicId = (String) request.getAttribute("topicId");
        }
        int currentPage = 1;
        if (CheckUtil.isValidInteger(CheckUtil.NUMBER_POSITIVE, page, 10)) {
            currentPage = Integer.valueOf(page);
        }

        //判断讨论区是否存在
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (!topicService.isValidDescription(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }
        int tId = Integer.valueOf(topicId);

        //检查用户是否有权限访问文件列表, 以下用户有权限: 位于授权小组的普通用户, admin, root
        Access access = (Access) request.getSession().getAttribute("access");
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApproved(groups, "admin", "root")) {
            response.sendRedirect("index.jsp");
            return;
        }

        //获取讨论区信息
        TopicPo topic = topicService.findTopic(Integer.valueOf(topicId));
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        //获取封装回复信息的分页对象
        PageBean<ReplyPo> pageBeanPo = replyService.findReplys(tId, currentPage);
        //将ReplyPo转换为ReplyVo
        List<ReplyPo> replyPos = pageBeanPo.getList();
        List<ReplyVo> replyVos = new ArrayList<>();
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        for (ReplyPo replyPo : replyPos) {
            UserPo userPo = userService.findUser(replyPo.getUserId());
            replyVos.add(BeanUtil.replyPoToVo(replyPo, userPo));
        }
        //重构分页对象
        PageBean<ReplyVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, replyVos);

        request.setAttribute("topic", topic);
        request.setAttribute("pageBean", pageBeanVo);
        request.getRequestDispatcher("topic_chat.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}
