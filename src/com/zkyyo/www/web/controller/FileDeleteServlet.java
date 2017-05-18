package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(
        name = "FileDeleteServlet",
        urlPatterns = {"/file_delete.do"}
)
public class FileDeleteServlet extends HttpServlet {
    private String TOPIC_DIR;

    public void init() throws ServletException {
        TOPIC_DIR = (String) getServletContext().getAttribute("topicDir");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileId = request.getParameter("fileId");

        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        String message = "文件不存在";
        //检测文件ID是否合法且存在
        if (fileService.isValidId(fileId)) {
            int fId = Integer.valueOf(fileId);
            if (fileService.isExisted(fId)) {
                FilePo file = fileService.findFile(fId);
                String relativePath = file.getPath();
                String bathPath = getServletContext().getRealPath(TOPIC_DIR);
                String absolutePath = bathPath + relativePath;
                File f = new File(absolutePath);
                //检测文件在硬盘上是否存在
                boolean isDeleted;
                if (f.exists()) {
                    isDeleted = f.delete();
                    //检测是否删除成功
                    if (isDeleted) {
                        fileService.deleteFile(fId); //在硬盘上删除成功, 则删除数据库中的信息
                        message = "文件删除成功";
                    } else {
                        message = "文件删除失败";
                    }
                }
            }
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}
