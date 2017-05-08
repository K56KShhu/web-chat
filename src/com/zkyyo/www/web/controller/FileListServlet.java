package com.zkyyo.www.web.controller;

import com.zkyyo.www.po.FilePo;
import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.BeanUtil;
import com.zkyyo.www.vo.FileVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(
        name = "FileListServlet",
        urlPatterns = {"/file_list.do"}
)
public class FileListServlet extends HttpServlet {
    private static final String SHARE_IMAGE = "image";
    private static final String SHARE_FILE = "file";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String shareType = request.getParameter("shareType");

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (!topicService.isValidId(topicId) && !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        int tId = Integer.valueOf(topicId);
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<FilePo> filePos;
        List<FileVo> fileVos;
        switch (shareType) {
            case SHARE_IMAGE:
                filePos = fileService.findFiles(tId, FileService.APPLY_IMAGE);
                fileVos = new ArrayList<>();
                for (FilePo filePo : filePos) {
                    UserPo userPo = userService.getUser(filePo.getUserId());
                    fileVos.add(BeanUtil.FilePoToVo(filePo, userPo));
                }
                request.setAttribute("images", fileVos);
                request.getRequestDispatcher("image_list.jsp").forward(request, response);
                break;
            case SHARE_FILE:
                filePos = fileService.findFiles(tId, FileService.APPLY_FILE);
                fileVos = new ArrayList<>();
                for (FilePo filePo : filePos) {
                    UserPo userPo = userService.getUser(filePo.getUserId());
                    fileVos.add(BeanUtil.FilePoToVo(filePo, userPo));
                }
                request.setAttribute("files", fileVos);
                request.getRequestDispatcher("file_list.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }
}
