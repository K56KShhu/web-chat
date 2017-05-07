package com.zkyyo.www.view;

import com.zkyyo.www.po.FilePo;
import com.zkyyo.www.po.TopicPo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.TopicService;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileId = request.getParameter("fileId");

        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        //检测文件ID是否合法且存在
        if (fileService.isValidId(fileId)) {
            int fId = Integer.valueOf(fileId);
            if (fileService.isExisted(fId)) {
                FilePo file = fileService.findFile(fId);
                System.out.println(file);
                String relativePath = file.getPath();
                String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
                String absolutePath = bathPath + relativePath;
                File f = new File(absolutePath);
                //检测文件在硬盘上是否存在
                boolean isDeleted;
                if (f.exists()) {
                    isDeleted = f.delete();
                    //检测是否删除成功
                    if (isDeleted) {
                        fileService.deleteFile(fId); //在硬盘上删除成功, 则删除数据库中的信息
                        request.setAttribute("message", "删除文件成功");
                        request.getRequestDispatcher("success.jsp").forward(request, response);
                    } else {
                        request.setAttribute("message", "删除文件失败");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("message", "该文件不存在");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        } else {
            request.setAttribute("message", "该文件不存在");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
