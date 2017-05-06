package com.zkyyo.www.view;

import com.zkyyo.www.po.FilePo;
import com.zkyyo.www.service.FileService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(
        name = "FileUploadServlet",
        urlPatterns = {"/file_upload.do"}
)
public class FileUploadServlet extends HttpServlet {
    private static final int APPLY_CHAT = 0;
    private static final int APPLY_IMAGE = 1;
    private static final int APPLY_FILE = 2;

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

        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setFileSizeMax(30 * 1024 * 1024);
            upload.setSizeMax(80 * 1024 * 1024);
            upload.setHeaderEncoding("UTF-8");

            if (ServletFileUpload.isMultipartContent(request)) {
                FileService fileService = (FileService) getServletContext().getAttribute("fileService");
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list) {
                    if (item.isFormField()) { //普通表单
                        System.out.println("nothing");
                    } else { //上传文件
                        String filename = item.getName();
                        if (filename.trim().isEmpty()) {
                            continue;
                        }
                        filename = makeFilename(filename);
                        String relativePath = makeRelativePath(Integer.valueOf(topicId), shareType);
                        String absolutePath = makeAbsolutePath(relativePath);
                        File file = new File(absolutePath, filename);
                        item.write(file);
                        item.delete();

                        FilePo filePo = new FilePo();
                        filePo.setUserId(userId);
                        filePo.setTopicId(Integer.valueOf(topicId));
                        filePo.setPath(relativePath + "/" + filename);
                        if ("chat".equals(shareType)) {
                            filePo.setApply(APPLY_CHAT);
                        } else if ("image".equals(shareType)) {
                            filePo.setApply(APPLY_IMAGE);
                        } else if ("file".equals(shareType)) {
                            filePo.setApply(APPLY_FILE);
                        }
                        fileService.addFile(filePo);
                    }
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            //单个文件过大
            request.setAttribute("message", "单个文件过大");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        } catch (FileUploadBase.SizeLimitExceededException e) {
            //总文件过大
            request.setAttribute("message", "总文件过大");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        } catch (Exception e) {
            request.setAttribute("message", "上传失败");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        }

        String page = "file_list.do?topicId=" + topicId + "&shareType=" + shareType;
        response.sendRedirect(page);
    }

    private String makeFilename(String filename) {
        String id = UUID.randomUUID().toString();
        return id + "_" + filename;
    }

    private String makeRelativePath(int topicId, String shareType) {
        String typePath;
        if ("chat".equals(shareType)) {
            typePath = "/chat";
        } else if ("image".equals(shareType)) {
            typePath = "/image";
        } else if ("file".equals(shareType)) {
            typePath = "/file";
        } else {
            typePath = "/others";
        }
        String topicPath = "/topic#" + topicId;
        String datePath = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        return topicPath + typePath + datePath;
    }

    private String makeAbsolutePath(String relativePath) {
        String bathPath = getServletContext().getRealPath("/WEB-INF/topics");
//        String bathPath = getServletContext().getRealPath("/topics");
        String absolutePath = bathPath + relativePath;

        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return absolutePath;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
