package com.zkyyo.www.util;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.*;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.service.ReportService;
import com.zkyyo.www.bean.vo.FileVo;
import com.zkyyo.www.bean.vo.ReplyVo;
import com.zkyyo.www.bean.vo.ReportVo;

import java.sql.Timestamp;
import java.util.List;

public class BeanUtil {

    public static ReplyVo replyPoToVo(ReplyPo replyPo, UserPo userPo) {
        int replyId = replyPo.getReplyId();
        int topicId = replyPo.getTopicId();
        int userId = replyPo.getUserId();
        String content = replyPo.getContent();
        int contentType = replyPo.getContentType();
        Timestamp created = replyPo.getCreated();
        String username = userPo.getUsername();
        String contentTypeStr;
        if (contentType == ReplyService.CONTENT_TEXT) {
            contentTypeStr = "聊天文本";
        } else if (contentType == ReplyService.CONTENT_IMAGE) {
            contentTypeStr = "聊天图片";
        } else {
            contentTypeStr = "unknown";
        }
        return new ReplyVo(replyId, topicId, userId, username, content, contentType, contentTypeStr, created);
    }

    public static FileVo filePoToVo(FilePo filePo, UserPo userPo) {
        int fileId = filePo.getFileId();
        int apply = filePo.getApply();
        int userId = filePo.getUserId();
        int topicId = filePo.getTopicId();
        String path = filePo.getPath();
        Timestamp created = filePo.getCreated();
        String username = userPo.getUsername();
        String shortName = path.substring(path.indexOf("_") + 1);
        String applyStr;
        if (apply == FileService.APPLY_IMAGE) {
            applyStr = "图片分享";
        } else if (apply == FileService.APPLY_FILE) {
            applyStr = "文件分享";
        } else {
            applyStr = "unknown";
        }
        return new FileVo(fileId, apply, applyStr, userId, username, topicId, path, shortName, created);
    }

    public static ReportVo reportPoToVo(ReportPo reportPo, UserPo userPo) {
        int reportId = reportPo.getReportId();
        int userId = reportPo.getUserId();
        String username = userPo.getUsername();
        int contentId = reportPo.getContentId();
        int contentType = reportPo.getContentType(); // 0举报发言, 1举报分享图片, 3举报分享文件
        String reason = reportPo.getReason();
        String contentTypeStr;
        if (contentType == ReportService.CONTENT_TYPE_CHAT) {
            contentTypeStr = "发言";
        } else if (contentType == ReportService.CONTENT_TYPE_SHARE_IMAGE) {
            contentTypeStr = "分享图片";
        } else if (contentType == ReportService.CONTENT_TYPE_SHARE_FILE) {
            contentTypeStr = "分享文件";
        } else {
            contentTypeStr = "unknown";
        }
        return new ReportVo(reportId, userId, username, contentId, contentType, contentTypeStr, reason);
    }

    public static <T, V> PageBean<V> pageBeanListTranslate(PageBean<T> initPage, List<V> newList) {
//        PageBean<V> newPage = new PageBean<>();
//        newPage.setCurrentPage(initPage.getCurrentPage());
//        newPage.setTotalRow(initPage.getTotalRow());
//        newPage.setRowsOnePage(initPage.getRowsOnePage());
//        newPage.setTotalPage(initPage.getTotalPage());
//        newPage.setList(newList);
        int currentPage = initPage.getCurrentPage();
        int totalRow = initPage.getTotalRow();
        int rowsOnePage = initPage.getRowsOnePage();
        PageBean<V> newPage = new PageBean<>(currentPage, totalRow, rowsOnePage);
        newPage.setList(newList);
        return newPage;
    }
}
