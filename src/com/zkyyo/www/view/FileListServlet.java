package com.zkyyo.www.view;

import com.zkyyo.www.po.FilePo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.TopicService;

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

        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        List<FilePo> filesList = new ArrayList<>();
        if ("image".equals(shareType)) {
            filesList = fileService.findFiles(Integer.valueOf(topicId), FileService.APPLY_IMAGE);
        } else if ("file".equals(shareType)) {
            filesList = fileService.findFiles(Integer.valueOf(topicId), FileService.APPLY_FILE);
        }

        Map<String, String> filesMap = new HashMap<>();
        for (FilePo f : filesList) {
            String path = f.getPath();
            String shortName = path.substring(path.indexOf("_") + 1);
            filesMap.put(path, shortName);
        }

        request.setAttribute("files", filesMap);
        request.getRequestDispatcher("file_list.jsp").forward(request, response);
    }
}
