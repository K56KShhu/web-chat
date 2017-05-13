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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String page = request.getParameter("page");

        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if (topicService.isValidId(topicId)) {
            int tId = Integer.valueOf(topicId);
            /*
            Access access = (Access) request.getSession().getAttribute("access");
            boolean isApproved = false;
            if (access.isUserInRole("admin")) {
                isApproved = true;
            } else {
                Set<Integer> groups = topicService.getGroups(tId);
                if (access.isUserInGroups(groups)) {
                    isApproved = true;
                }
            }
            if (!isApproved) {
                response.sendRedirect("index.jsp");
                return;
            }
            */
            Access access = (Access) request.getSession().getAttribute("access");
            Set<Integer> groups = topicService.getGroups(tId);
            if (!access.isUserApproved("admin", groups)) {
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
    }
}
