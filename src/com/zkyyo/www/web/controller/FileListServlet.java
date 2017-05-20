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

/**
 * 该Servlet用于处理获取文件列表的请求
 */
@WebServlet(
        name = "FileListServlet",
        urlPatterns = {"/file_list.do"}
)
public class FileListServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId"); //讨论区ID
        String shareTypeStr = request.getParameter("shareType"); //分享类型
        String page = request.getParameter("page"); //请求页数
        String order = request.getParameter("order"); //排序依据
        boolean isReverse = "true".equals(request.getParameter("isReverse")); //是否降序 true降序, false升序

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        //判断讨论区是否存在
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        //检查用户是否有权限发表回复, 以下用户有权限: 位于授权小组的普通用户, admin, root
        int tId = Integer.valueOf(topicId);
        Access access = (Access) request.getSession().getAttribute("access");
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApproved(groups, "admin", "root")) {
            response.sendRedirect("index.jsp");
            return;
        }


        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        int shareType;
        String url;
        //判断分享类型
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
        //获取分页对象
        PageBean<FilePo> pageBeanPo = fileService.queryFiles(currentPage, FileService.ORDER_BY_CREATED, isReverse, tId, shareType);
        //获取文件上传者用户信息
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //将FilePo转换为FileVo
        List<FilePo> filePos = pageBeanPo.getList();
        List<FileVo> fileVos = new ArrayList<>();
        for (FilePo filePo : filePos) {
            UserPo userPo = userService.findUser(filePo.getUserId());
            fileVos.add(BeanUtil.filePoToVo(filePo, userPo));
        }
        //重构分页对象
        PageBean<FileVo> pageBeanVo = BeanUtil.pageBeanListTranslate(pageBeanPo, fileVos);

        request.setAttribute("topicId", topicId);
        request.setAttribute("pageBean", pageBeanVo);
        request.setAttribute("shareType", shareTypeStr);
        request.setAttribute("order", order);
        request.setAttribute("isReverse", isReverse);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
