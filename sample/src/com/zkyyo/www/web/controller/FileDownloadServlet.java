package com.zkyyo.www.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 该Servlet用于处理用户下载指定文件的请求
 */
@WebServlet(
        name = "FileDownloadServlet",
        urlPatterns = {"/file_download.do"}
)
public class FileDownloadServlet extends HttpServlet {
    /**
     * 存放讨论区文件的根目录
     */
    private String TOPIC_DIR;

    public void init() throws ServletException {
        TOPIC_DIR = (String) getServletContext().getAttribute("topicDir");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String relativePath = request.getParameter("relativePath"); //文件的相对路径

        String bathPath = getServletContext().getRealPath(TOPIC_DIR); //讨论区文件根目录在硬盘上的绝对路径
        String absolutePath = bathPath + relativePath; //构建该文件在硬盘上的绝对路径
        File file = new File(absolutePath);
        //判断文件在硬盘上是否存在
        if (!file.exists()) {
            request.setAttribute("message", "文件已被删除");
            request.getRequestDispatcher("message.jsp").forward(request, response);
        } else {
            InputStream in = new FileInputStream(file);
            String shortName = relativePath.substring(relativePath.indexOf("_") + 1);
            shortName = URLEncoder.encode(shortName, "UTF-8");
            response.setHeader("content-disposition", "attachment;fileName=" + shortName);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.close();
            in.close();
        }
    }
}
