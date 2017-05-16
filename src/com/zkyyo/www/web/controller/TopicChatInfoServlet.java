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

@WebServlet(
        name = "TopicChatInfoServlet",
        urlPatterns = {"/topic_chat_info.do"}
)
public class TopicChatInfoServlet extends HttpServlet {
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String page = request.getParameter("page");
        if (topicId == null) {
            topicId = (String) request.getAttribute("topicId");
        }
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }

        //检查讨论区是否存在
        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (!topicService.isValidDescription(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }
        int tId = Integer.valueOf(topicId);

        //验证用户权限
        Access access = (Access) request.getSession().getAttribute("access");
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApprovedInTopic("admin", groups)) {
            response.sendRedirect("index.jsp");
            return;
        }
        //获取讨论区信息
        TopicPo topic = topicService.findTopic(Integer.valueOf(topicId));
        //获取回复信息
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        PageBean<ReplyPo> pageBeanPo = replyService.findReplys(tId, currentPage);
        List<ReplyPo> replyPos = pageBeanPo.getList();
        List<ReplyVo> replyVos = new ArrayList<>();
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        for (ReplyPo replyPo : replyPos) {
            UserPo userPo = userService.getUser(replyPo.getUserId());
            replyVos.add(BeanUtil.replyPoToVo(replyPo, userPo));
        }
        PageBean<ReplyVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, replyVos);
        request.setAttribute("topic", topic);
        request.setAttribute("pageBean", pageBeanVo);
        request.getRequestDispatcher("topic_chat.jsp").forward(request, response);

        /*
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            Access access = (Access) request.getSession().getAttribute("access");
            Set<Integer> groups = topicService.getGroups(tId);
            if (!access.isUserApprovedInTopic("admin", groups)) {
                response.sendRedirect("index.jsp");
                return;
            }

            if (topicService.isExisted(tId)) {
                //获取主题信息
                TopicPo topic = topicService.findTopic(Integer.valueOf(topicId));
                //获取回复信息
                PageBean<ReplyPo> pageBeanPo = replyService.findReplys(tId, currentPage);
                List<ReplyPo> replyPos = pageBeanPo.getList();
                List<ReplyVo> replyVos = new ArrayList<>();
                UserPo userPo;
                for (ReplyPo replyPo : replyPos) {
                    userPo = userService.getUser(replyPo.getUserId());
                    replyVos.add(BeanUtil.replyPoToVo(replyPo, userPo));
                }
                PageBean<ReplyVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, replyVos);
                request.setAttribute("topic", topic);
                request.setAttribute("pageBean", pageBeanVo);
                request.getRequestDispatcher("topic_chat.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("index.jsp");
        }
        */
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}
