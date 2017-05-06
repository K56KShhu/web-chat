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

        List<FilePo> fileList;
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        if ("image".equals(shareType)) {
            fileList = fileService.findFiles(Integer.valueOf(topicId), FileService.APPLY_IMAGE);
            Map<String, FilePo> imageMap = new HashMap<>();
            for (FilePo f : fileList) {
                String relativePath = f.getPath();
                imageMap.put(relativePath, f);
            }
            request.setAttribute("images", imageMap);
            request.getRequestDispatcher("image_list.jsp").forward(request, response);
        } else if ("file".equals(shareType)) {
            fileList = fileService.findFiles(Integer.valueOf(topicId), FileService.APPLY_FILE);
            Map<String, String> fileMap = new HashMap<>();
            for (FilePo f : fileList) {
                String relativePath = f.getPath();
                String shortName = relativePath.substring(relativePath.indexOf("_") + 1);
                fileMap.put(relativePath, shortName);
            }
            request.setAttribute("files", fileMap);
            request.getRequestDispatcher("file_list.jsp").forward(request, response);
        }
    }
}
