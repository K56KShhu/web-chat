package com.zkyyo.www.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(
        name = "ImageShowServlet",
        urlPatterns = {"/image_show.do"}
)
public class ImageShowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String relativePath = request.getParameter("relativePath");
        String bathPath = getServletContext().getRealPath("/topics");
        String absolutePath = bathPath + relativePath;
        File file = new File(absolutePath);

        OutputStream outputStream = response.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] data = new byte[fileInputStream.available()];
        System.out.println("available: " + fileInputStream.available());
        fileInputStream.read(data);
        fileInputStream.close();
        response.setContentType("image/jpeg");
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }
}
