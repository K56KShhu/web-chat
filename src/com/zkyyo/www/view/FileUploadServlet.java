package com.zkyyo.www.view;

import com.zkyyo.www.po.FilePo;
import com.zkyyo.www.po.ReplyPo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;
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
    private static final String CHAT_IMAGE = "chat";
    private static final String SHARE_IMAGE = "image";
    private static final String SHARE_FILE = "file";

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

        String page = "index.jsp";
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setFileSizeMax(30 * 1024 * 1024);
            upload.setSizeMax(80 * 1024 * 1024);
            upload.setHeaderEncoding("UTF-8");

            if (ServletFileUpload.isMultipartContent(request)) {
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list) {
                    if (!item.isFormField()) {
                        int tId = Integer.valueOf(topicId);
                        //处理类型
                        if (CHAT_IMAGE.equals(shareType)) {
                            processChatImage(userId, tId, shareType, item);
                            page = "topic_chat_info.do?topicId=" + topicId;
                            response.sendRedirect(page);
                            return;
                        } else if (SHARE_IMAGE.equals(shareType)) {
                            processShareFile(userId, tId, shareType, item);
                            page = "file_list.do?topicId=" + topicId + "&shareType=" + shareType;
                            response.sendRedirect(page);
                            return;
                        } else if (SHARE_FILE.equals(shareType)) {
                            processShareFile(userId, tId, shareType, item);
                            page = "file_list.do?topicId=" + topicId + "&shareType=" + shareType;
                            response.sendRedirect(page);
                            return;
                        }
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
        response.sendRedirect(page);
    }

    //处理聊天图片
    private void processChatImage(int userId, int topicId, String shareType, FileItem item) throws Exception {
        String filename = item.getName();
        if (filename.trim().isEmpty()) {
            return;
        }
        filename = makeFilename(filename);
        String relativePath = makeRelativePath(topicId, shareType);
        String absolutePath = makeAbsolutePath(relativePath);
        File file = new File(absolutePath, filename);
        item.write(file);
        item.delete();

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        ReplyPo replyPo = new ReplyPo();
        replyPo.setTopicId(topicId);
        replyPo.setUserId(userId);
        replyPo.setContent(relativePath + "/" + filename);
        replyPo.setContentType(2);
        replyService.addReply(replyPo);
    }

    //处理图片, 文件分享
    private void processShareFile(int userId, int topicId, String shareType, FileItem item) throws Exception {
        String filename = item.getName();
        if (filename.trim().isEmpty()) {
            return;
        }
        filename = makeFilename(filename);
        String relativePath = makeRelativePath(topicId, shareType);
        String absolutePath = makeAbsolutePath(relativePath);
        File file = new File(absolutePath, filename);
        item.write(file);
        item.delete();

        FilePo filePo = new FilePo();
        filePo.setUserId(userId);
        filePo.setTopicId(topicId);
        filePo.setPath(relativePath + "/" + filename);
        if (SHARE_IMAGE.equals(shareType)) {
            filePo.setApply(FileService.APPLY_IMAGE);
        } else if (SHARE_FILE.equals(shareType)) {
            filePo.setApply(FileService.APPLY_FILE);
        }
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        fileService.addFile(filePo);
    }

    private String makeFilename(String filename) {
        String id = UUID.randomUUID().toString();
        return id + "_" + filename;
    }

    private String makeRelativePath(int topicId, String shareType) {
        String typePath;
        if (CHAT_IMAGE.equals(shareType)) {
            typePath = "/chat";
        } else if (SHARE_IMAGE.equals(shareType)) {
            typePath = "/image";
        } else if (SHARE_FILE.equals(shareType)) {
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
