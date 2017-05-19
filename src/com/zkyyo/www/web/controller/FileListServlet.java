package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.bean.vo.FileVo;
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
        name = "FileListServlet",
        urlPatterns = {"/file_list.do"}
)
public class FileListServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String shareTypeStr = request.getParameter("shareType");
        String page = request.getParameter("page");
        String order = request.getParameter("order");
        boolean isReverse = "true".equals(request.getParameter("isReverse"));

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        int tId = Integer.valueOf(topicId);
        Access access = (Access) request.getSession().getAttribute("access");
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApprovedInTopic("admin", groups)) {
            response.sendRedirect("index.jsp");
            return;
        }

        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        int shareType;
        String url;
        if ("image".equals(shareTypeStr)) {
            shareType = FileService.APPLY_IMAGE;
            url = "image_list.jsp";
        } else if ("file".equals(shareTypeStr)) {
            shareType = FileService.APPLY_FILE;
            url = "file_list.jsp";
        } else {
            response.sendRedirect("index.jsp");
            return;
        }

        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        PageBean<FilePo> pageBeanPo = fileService.queryFiles(currentPage, FileService.ORDER_BY_CREATED, isReverse, tId, shareType);
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<FilePo> filePos = pageBeanPo.getList();
        List<FileVo> fileVos = new ArrayList<>();
        for (FilePo filePo : filePos) {
            UserPo userPo = userService.findUser(filePo.getUserId());
            fileVos.add(BeanUtil.filePoToVo(filePo, userPo));
        }
        PageBean<FileVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, fileVos);

        request.setAttribute("topicId", topicId);
        request.setAttribute("pageBean", pageBeanVo);
        request.setAttribute("shareType", shareTypeStr);
        request.setAttribute("order", order);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
