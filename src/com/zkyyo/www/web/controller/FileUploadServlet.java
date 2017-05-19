package com.zkyyo.www.web.controller;

import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.dao.impl.FileDaoJdbcImpl;
import com.zkyyo.www.dao.impl.ReplyDaoJdbcImpl;
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
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 该Servlet用于处理用户上传文件的请求
 */
@WebServlet(
        name = "FileUploadServlet",
        urlPatterns = {"/file_upload.do"},
        initParams = {
                @WebInitParam(name = "FILE_SIZE_MAX", value = "31457280"), //30 * 1024 * 1024
                @WebInitParam(name = "FILE_TOTAL_SIZE_MAX", value = "104857600") //100 * 1024 * 1024
        }
)
public class FileUploadServlet extends HttpServlet {
    /**
     * 上传文件标识符, 表示聊天区的图片
     */
    private static final String CHAT_IMAGE = "chat";
    /**
     * 上传文件标识符, 表示分享区的图片
     */
    private static final String SHARE_IMAGE = "image";
    /**
     * 上传文件标识符, 表示分享区的文件
     */
    private static final String SHARE_FILE = "file";

    /**
     * 存放讨论区文件的根目录
     */
    private String TOPIC_DIR;

    /**
     * 上传文件的最大大小
     */
    private long fileSizeMax;
    /**
     * 上传总文件的最大大小
     */
    private long fileTotalSizeMax;

    public void init() throws ServletException {
        TOPIC_DIR = (String) getServletContext().getAttribute("topicDir");
        fileSizeMax = Long.valueOf(getInitParameter("FILE_SIZE_MAX"));
        fileTotalSizeMax = Long.valueOf(getInitParameter("FILE_TOTAL_SIZE_MAX"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topicId = request.getParameter("topicId"); //讨论区ID
        String shareType = request.getParameter("shareType"); //分享类型
        Access access = (Access) request.getSession().getAttribute("access"); //操作者权限对象
        int userId = access.getUserId();

        TopicService topicService = (TopicService) getServletContext().getAttribute("topicService");
        //判断讨论区是否存在
        if (!topicService.isValidId(topicId) || !topicService.isExisted(Integer.valueOf(topicId))) {
            response.sendRedirect("index.jsp");
            return;
        }

        //判断用户是否授权访问讨论区
        int tId = Integer.valueOf(topicId);
        Set<Integer> groups = topicService.getGroups(tId);
        if (topicService.isPrivate(tId) && !access.isUserApprovedInTopic("admin", groups)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String page = "index.jsp";
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setFileSizeMax(fileSizeMax);
            upload.setSizeMax(fileTotalSizeMax);
            upload.setHeaderEncoding("UTF-8");

            //判断是否为上传文件
            if (ServletFileUpload.isMultipartContent(request)) {
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list) {
                    if (!item.isFormField()) {
                        //判断分享类型
                        if (CHAT_IMAGE.equals(shareType)) {
                            //处理讨论区聊天图片
                            processChatImage(userId, tId, shareType, item);
                            page = "topic_chat_info.do?topicId=" + topicId;
                            response.sendRedirect(page);
                            return;
                        } else if (SHARE_IMAGE.equals(shareType)) {
                            //处理讨论区分享图片
                            processShareFile(userId, tId, shareType, item);
                            page = "file_list.do?topicId=" + topicId + "&shareType=" + shareType;
                            response.sendRedirect(page);
                            return;
                        } else if (SHARE_FILE.equals(shareType)) {
                            //处理讨论区分享文件
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
            request.getRequestDispatcher("message.jsp").forward(request, response);
            e.printStackTrace();
            return;
        } catch (FileUploadBase.SizeLimitExceededException e) {
            //总文件过大
            request.setAttribute("message", "总文件过大");
            request.getRequestDispatcher("message.jsp").forward(request, response);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            request.setAttribute("message", "上传失败");
            request.getRequestDispatcher("message.jsp").forward(request, response);
            e.printStackTrace();
            return;
        }
        response.sendRedirect(page);
    }

    /**
     * 处理聊天区图片
     *
     * @param userId    上传者用户ID
     * @param topicId   讨论区ID
     * @param shareType 分享类型
     * @param item      图片文件
     * @throws Exception 文件写入发生异常时抛出异常
     */
    private void processChatImage(int userId, int topicId, String shareType, FileItem item) throws Exception {
        String filename = item.getName();
        if (filename.trim().isEmpty()) {
            return;
        }
        filename = makeFilename(filename);
        //构建相对路径
        String relativePath = makeRelativePath(topicId, shareType);
        //构建绝对路径
        String absolutePath = makeAbsolutePath(relativePath);
        File file = new File(absolutePath, filename);
        item.write(file);
        item.delete();

        ReplyService replyService = (ReplyService) getServletContext().getAttribute("replyService");
        ReplyPo replyPo = new ReplyPo();
        replyPo.setTopicId(topicId);
        replyPo.setUserId(userId);
        replyPo.setContent(relativePath + "/" + filename);
        replyPo.setContentType(ReplyDaoJdbcImpl.CONTENT_TYPE_IMAGE);
        replyService.addReply(replyPo);
    }

    /**
     * 处理分享区文件
     *
     * @param userId    上传者用户ID
     * @param topicId   讨论区ID
     * @param shareType 分享类型
     * @param item      分享文件
     * @throws Exception 文件写入发生错误时抛出异常
     */
    private void processShareFile(int userId, int topicId, String shareType, FileItem item) throws Exception {
        String filename = item.getName();
        //判断是否上传空文件
        if (filename.trim().isEmpty()) {
            return;
        }
        filename = makeFilename(filename);
        //构建相对路径
        String relativePath = makeRelativePath(topicId, shareType);
        //构建绝对路径
        String absolutePath = makeAbsolutePath(relativePath);
        File file = new File(absolutePath, filename);
        item.write(file);
        item.delete();

        FilePo filePo = new FilePo();
        filePo.setUserId(userId);
        filePo.setTopicId(topicId);
        filePo.setPath(relativePath + "/" + filename);
        //设置数据库储存类型
        if (SHARE_IMAGE.equals(shareType)) {
            filePo.setApply(FileDaoJdbcImpl.APPLY_IMAGE);
        } else if (SHARE_FILE.equals(shareType)) {
            filePo.setApply(FileDaoJdbcImpl.APPLY_FILE);
        }
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        fileService.addFile(filePo);
    }

    /**
     * 构建唯一文件名
     *
     * @param filename 待构建的文件名
     * @return 构建完成的文件名
     */
    private String makeFilename(String filename) {
        //获取唯一标识符
        String id = UUID.randomUUID().toString();
        return id + "_" + filename;
    }

    /**
     * 根据讨论区和分享类型构建相对路径
     *
     * @param topicId   讨论区ID
     * @param shareType 分享类型
     * @return 构建完成的相对路径
     */
    private String makeRelativePath(int topicId, String shareType) {
        String typePath;
        //判断分享类型
        if (CHAT_IMAGE.equals(shareType)) {
            typePath = "/chat";
        } else if (SHARE_IMAGE.equals(shareType)) {
            typePath = "/image";
        } else if (SHARE_FILE.equals(shareType)) {
            typePath = "/file";
        } else {
            typePath = "/others";
        }
        //构建讨论区路径
        String topicPath = "/topic#" + topicId;
        //讨论区内按照日期构建路径
        String datePath = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        return topicPath + typePath + datePath;
    }

    /**
     * 根据相对路径, 构建出本机硬盘上的绝对路径
     * @param relativePath 相对路径
     * @return 构建完成的绝对路径
     */
    private String makeAbsolutePath(String relativePath) {
        String bathPath = getServletContext().getRealPath(TOPIC_DIR);
        String absolutePath = bathPath + relativePath;

        File file = new File(absolutePath);
        //判断绝对路径是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        return absolutePath;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
