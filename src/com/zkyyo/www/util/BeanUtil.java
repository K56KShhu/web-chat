package com.zkyyo.www.util;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.bean.po.ReportPo;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.bean.vo.FileVo;
import com.zkyyo.www.bean.vo.ReplyVo;
import com.zkyyo.www.bean.vo.ReportVo;
import com.zkyyo.www.bean.vo.TopicVo;
import com.zkyyo.www.bean.vo.UserVo;
import com.zkyyo.www.dao.impl.FileDaoJdbcImpl;
import com.zkyyo.www.dao.impl.ReplyDaoJdbcImpl;
import com.zkyyo.www.dao.impl.ReportDaoJdbcImpl;
import com.zkyyo.www.dao.impl.TopicDaoJdbcImpl;
import com.zkyyo.www.dao.impl.UserDaoJdbcImpl;

import java.sql.Timestamp;
import java.util.List;

/**
 * 该类封装了对象转换的方法
 */
public class BeanUtil {
    /**
     * 将replyPo转换为replyVo
     * @param replyPo 原对象
     * @param userPo 提供用户名信息的用户对象
     * @return 新对象
     */
    public static ReplyVo replyPoToVo(ReplyPo replyPo, UserPo userPo) {
        int replyId = replyPo.getReplyId();
        int topicId = replyPo.getTopicId();
        int userId = replyPo.getUserId();
        String content = replyPo.getContent();
        int contentType = replyPo.getContentType();
        String contentTypeStr;
        Timestamp created = replyPo.getCreated();
        String username = userPo.getUsername();
        if (ReplyDaoJdbcImpl.CONTENT_TYPE_TEXT == contentType) {
            contentTypeStr = "聊天文本";
        } else if (ReplyDaoJdbcImpl.CONTENT_TYPE_IMAGE == contentType) {
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

    /**
     * 将filePo转换为fileVo
     * @param filePo 原文件对象
     * @param userPo 辅助用户对象
     * @return 新文件对象
     */
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
        if (FileDaoJdbcImpl.APPLY_IMAGE == apply) {
            applyStr = "图片分享";
        } else if (FileDaoJdbcImpl.APPLY_FILE == apply) {
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

    /**
     * 将reportPo转换为reportVo
     * @param reportPo 原举报对象
     * @param userPo 辅助用户对象
     * @return 新举报对象
     */
    public static ReportVo reportPoToVo(ReportPo reportPo, UserPo userPo) {
        int reportId = reportPo.getReportId();
        int userId = reportPo.getUserId();
        String username = userPo.getUsername();
        int contentId = reportPo.getContentId();
        int contentType = reportPo.getContentType(); // 0举报发言, 1举报分享图片, 3举报分享文件
        String reason = reportPo.getReason();
        Timestamp created = reportPo.getCreated();
        String contentTypeStr;
        if (ReportDaoJdbcImpl.CONTENT_TYPE_REPLY == contentType) {
            contentTypeStr = "发言";
        } else if (ReportDaoJdbcImpl.CONTENT_TYPE_SHARE_IMAGE == contentType) {
            contentTypeStr = "分享图片";
        } else if (ReportDaoJdbcImpl.CONTENT_TYPE_SHARE_FILE == contentType) {
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

    /**
     * 将topicPo转换为topicVo, 转换所有信息, 用于详细信息展示
     * @param topicPo 原讨论区对象
     * @param creator 辅助创建者用户对象
     * @param modifier 辅助最后修改者用户对象
     * @return 新讨论区对象
     */
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
        if (TopicDaoJdbcImpl.ACCESS_PRIVATE == topicPo.getIsPrivate()) {
            isPrivateStr = "授权";
        } else if (TopicDaoJdbcImpl.ACCESS_PUBLIC == topicPo.getIsPrivate()) {
            isPrivateStr = "公开";
        } else {
            return null;
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

    /**
     * 将topicPo转换为topicVo, 仅转换部分核心信息, 用于列表展示
     * @param topicPo 原讨论区对象
     * @return 新讨论区对象
     */
    public static TopicVo topicPoToVoForList(TopicPo topicPo) {
        int topicId = topicPo.getTopicId();
        String title = topicPo.getTitle();
        String description = topicPo.getDescription();
        int isPrivate = topicPo.getIsPrivate();
        String isPrivateStr;
        int replyAccount = topicPo.getReplyAccount();
        Timestamp lastTime = topicPo.getLastTime();
        Timestamp created = topicPo.getCreated();
        if (TopicDaoJdbcImpl.ACCESS_PRIVATE == isPrivate) {
            isPrivateStr = "授权";
        } else if (TopicDaoJdbcImpl.ACCESS_PUBLIC == isPrivate) {
            isPrivateStr = "公开";
        } else {
            return null;
        }
        TopicVo topicVo = new TopicVo();
        topicVo.setTopicId(topicId);
        topicVo.setTitle(title);
        topicVo.setDescription(description);
        topicVo.setIsPrivate(isPrivate);
        topicVo.setIsPrivateStr(isPrivateStr);
        topicVo.setReplyAccount(replyAccount);
        topicVo.setLastTime(lastTime);
        topicVo.setCreated(created);
        return topicVo;
    }

    /**
     * 将userPo转换为userVo
     * @param userPo 原用户对象
     * @return 新用户对象
     */
    public static UserVo userPoToVo(UserPo userPo) {
        int userId = userPo.getUserId();
        String username = userPo.getUsername();
        String sex = userPo.getSex();
        String email = userPo.getEmail();
        int status = userPo.getStatus();
        Timestamp created = userPo.getCreated();
        if ("male".equals(sex)) {
            sex = "男";
        } else if ("female".equals(sex)) {
            sex = "女";
        } else if ("secret".equals(sex)) {
            sex = "秘密";
        } else {
            return null;
        }
        String statusStr;
        if (UserDaoJdbcImpl.STATUS_NORMAL == status) {
            statusStr = "正常";
        } else if (UserDaoJdbcImpl.STATUS_AUDIT == status) {
            statusStr = "审核中";
        } else if (UserDaoJdbcImpl.STATUS_NOT_APPROVED == status) {
            statusStr = "审核不通过";
        } else if (UserDaoJdbcImpl.STATUS_FORBIDDEN == status) {
            statusStr = "封印";
        } else {
            return null;
        }
        UserVo userVo = new UserVo();
        userVo.setUserId(userId);
        userVo.setUsername(username);
        userVo.setSex(sex);
        userVo.setEmail(email);
        userVo.setStatus(status);
        userVo.setStatusStr(statusStr);
        userVo.setCreated(created);
        return userVo;
    }

    /**
     * 替换分页对象中的List类型
     * @param initPage 原分页对象
     * @param newList 待替换的新List
     * @param <T> 原分页对象类型
     * @param <V> 新分页对象类型
     * @return 新分页对象
     */
    public static <T, V> PageBean<V> pageBeanListTranslate(PageBean<T> initPage, List<V> newList) {
        int currentPage = initPage.getCurrentPage();
        int totalRow = initPage.getTotalRow();
        int rowsOnePage = initPage.getRowsOnePage();
        PageBean<V> newPage = new PageBean<>(currentPage, totalRow, rowsOnePage);
        newPage.setList(newList);
        return newPage;
    }
}
