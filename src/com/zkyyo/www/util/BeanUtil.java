package com.zkyyo.www.util;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.*;
import com.zkyyo.www.bean.vo.TopicVo;
import com.zkyyo.www.service.FileService;
import com.zkyyo.www.service.ReplyService;
import com.zkyyo.www.service.ReportService;
import com.zkyyo.www.bean.vo.FileVo;
import com.zkyyo.www.bean.vo.ReplyVo;
import com.zkyyo.www.bean.vo.ReportVo;
import com.zkyyo.www.service.TopicService;

import java.sql.Timestamp;
import java.util.List;

public class BeanUtil {

    public static ReplyVo replyPoToVo(ReplyPo replyPo, UserPo userPo) {
        int replyId = replyPo.getReplyId();
        int topicId = replyPo.getTopicId();
        int userId = replyPo.getUserId();
        String content = replyPo.getContent();
        int contentType = replyPo.getContentType();
        String contentTypeStr;
        Timestamp created = replyPo.getCreated();
        String username = userPo.getUsername();
        if (ReplyService.CONTENT_TEXT == contentType) {
            contentTypeStr = "聊天文本";
        } else if (ReplyService.CONTENT_IMAGE == contentType) {
            contentTypeStr = "聊天图片";
        } else {
            return null;
        }
        ReplyVo replyVo = new ReplyVo();
        replyVo.setReplyId(replyId);
        replyVo.setTopicId(topicId);
        replyVo.setUserId(userId);
        replyVo.setContent(content);
        replyVo.setContentType(contentType);
        replyVo.setContentTypeStr(contentTypeStr);
        replyVo.setCreated(created);
        replyVo.setUsername(username);
        return replyVo;
    }

    public static FileVo filePoToVo(FilePo filePo, UserPo userPo) {
        int fileId = filePo.getFileId();
        int apply = filePo.getApply();
        String applyStr;
        int userId = filePo.getUserId();
        String username = userPo.getUsername();
        int topicId = filePo.getTopicId();
        String path = filePo.getPath();
        String shortName = path.substring(path.indexOf("_") + 1);
        Timestamp created = filePo.getCreated();
        if (FileService.APPLY_IMAGE == apply) {
            applyStr = "图片分享";
        } else if (FileService.APPLY_FILE == apply) {
            applyStr = "文件分享";
        } else {
            return null;
        }
        FileVo fileVo = new FileVo();
        fileVo.setFileId(fileId);
        fileVo.setApply(apply);
        fileVo.setApplyStr(applyStr);
        fileVo.setUserId(userId);
        fileVo.setUsername(username);
        fileVo.setTopicId(topicId);
        fileVo.setPath(path);
        fileVo.setShortName(shortName);
        fileVo.setCreated(created);
        return fileVo;
    }

    public static ReportVo reportPoToVo(ReportPo reportPo, UserPo userPo) {
        int reportId = reportPo.getReportId();
        int userId = reportPo.getUserId();
        String username = userPo.getUsername();
        int contentId = reportPo.getContentId();
        int contentType = reportPo.getContentType(); // 0举报发言, 1举报分享图片, 3举报分享文件
        String reason = reportPo.getReason();
        Timestamp created = reportPo.getCreated();
        String contentTypeStr;
        if (ReportService.CONTENT_TYPE_REPLY == contentType) {
            contentTypeStr = "发言";
        } else if (ReportService.CONTENT_TYPE_SHARE_IMAGE == contentType) {
            contentTypeStr = "分享图片";
        } else if (ReportService.CONTENT_TYPE_SHARE_FILE == contentType) {
            contentTypeStr = "分享文件";
        } else {
            return null;
        }
        ReportVo reportVo = new ReportVo();
        reportVo.setReportId(reportId);
        reportVo.setUserId(userId);
        reportVo.setUsername(username);
        reportVo.setContentId(contentId);
        reportVo.setContentType(contentType);
        reportVo.setReason(reason);
        reportVo.setCreated(created);
        reportVo.setContentTypeStr(contentTypeStr);
        return reportVo;
    }

    public static TopicVo topicPoToVo(TopicPo topicPo, UserPo creator, UserPo modifier) {
        int topicId = topicPo.getTopicId();
        String title = topicPo.getTitle();
        String description = topicPo.getDescription();
        int creatorId = topicPo.getCreatorId();
        String creatorUsername = creator.getUsername();
        int lastModifyId = topicPo.getLastModifyId();
        String lastModifyUsername = modifier.getUsername();
        int isPrivate = topicPo.getIsPrivate();
        String isPrivateStr;
        int replyAccount = topicPo.getReplyAccount();
        Timestamp lastTime = topicPo.getLastTime();
        Timestamp created = topicPo.getCreated();
        if (TopicService.ACCESS_PRIVATE == topicPo.getIsPrivate()) {
            isPrivateStr = "授权";
        } else {
            isPrivateStr = "公开";
        }
        TopicVo topicVo = new TopicVo();
        topicVo.setTopicId(topicId);
        topicVo.setTitle(title);
        topicVo.setDescription(description);
        topicVo.setCreatorId(creatorId);
        topicVo.setCreatorUsername(creatorUsername);
        topicVo.setLastModifyId(lastModifyId);
        topicVo.setLastModifyUsername(lastModifyUsername);
        topicVo.setIsPrivate(isPrivate);
        topicVo.setIsPrivateStr(isPrivateStr);
        topicVo.setReplyAccount(replyAccount);
        topicVo.setLastTime(lastTime);
        topicVo.setCreated(created);
        return topicVo;
    }

    public static <T, V> PageBean<V> pageBeanListTranslate(PageBean<T> initPage, List<V> newList) {
        int currentPage = initPage.getCurrentPage();
        int totalRow = initPage.getTotalRow();
        int rowsOnePage = initPage.getRowsOnePage();
        PageBean<V> newPage = new PageBean<>(currentPage, totalRow, rowsOnePage);
        newPage.setList(newList);
        return newPage;
    }
}
