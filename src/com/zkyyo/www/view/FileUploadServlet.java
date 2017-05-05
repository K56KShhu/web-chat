package com.zkyyo.www.view;

import com.zkyyo.www.service.TopicService;
import com.zkyyo.www.web.Access;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(
        name = "FileUploadServlet",
        urlPatterns = {"/file_upload.do"}
)
public class FileUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId");
        String shareType = request.getParameter("shareType");
        Access access = (Access) request.getSession().getAttribute("access");
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<String> errors = new ArrayList<>();
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setFileSizeMax(30 * 1024 * 1024);
            upload.setSizeMax(80 * 1024 * 1024);
            upload.setHeaderEncoding("UTF-8");

            if (ServletFileUpload.isMultipartContent(request)) {
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list) {
                    if (item.isFormField()) { //普通表单
                        System.out.println("nothing");
                    } else { //上传文件
                        String filename = item.getName();
                        if (filename.trim().isEmpty()) {
                            continue;
                        }
                        filename = makeFilename(userId, filename);
                        String path = makePath(Integer.valueOf(topicId), shareType);
                        File file = new File(path, filename);
                        item.write(file);
                        item.delete();
                    }
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            //单个文件过大
            e.printStackTrace();
            errors.add("one file too large");
            return;
        } catch (FileUploadBase.SizeLimitExceededException e) {
            //总文件过大
            e.printStackTrace();
            errors.add("total files too large");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("errors", errors);

        response.sendRedirect("file_list.do");
    }

    private String makeFilename(int userId, String filename) {
        String id = UUID.randomUUID().toString();
        return userId + "#" + id + "_" + filename;
    }

    private String makePath(int topicId, String shareType) {
        String typePath;
        if ("file".equals(shareType)) {
            typePath = "/file";
        } else if ("image".equals(shareType)) {
            typePath = "/image";
        } else {
            typePath = "/others";
        }
        String bathPath = getServletContext().getRealPath("/WEB-INF/upload/topics");
        String topicPath = "/topic#" + topicId;
        String datePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String path = bathPath + topicPath + typePath + "/" + datePath;
        System.out.println(path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
