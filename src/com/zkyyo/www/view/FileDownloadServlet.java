package com.zkyyo.www.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet(
        name = "FileDownloadServlet",
        urlPatterns = {"/file_download.do"}
)
public class FileDownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String relativePath = request.getParameter("relativePath");
//        relativePath = new String(relativePath.getBytes("ISO-8859-1"), "UTF-8");

        String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
        String absolutePath = bathPath + relativePath;
        File file = new File(absolutePath);
        if (!file.exists()) {
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
