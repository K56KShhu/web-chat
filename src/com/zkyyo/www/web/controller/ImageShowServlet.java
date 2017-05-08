package com.zkyyo.www.web.controller;

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
        String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
        String absolutePath = bathPath + relativePath;
        File file = new File(absolutePath);

        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
        fileInputStream.close();

        OutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }
}
