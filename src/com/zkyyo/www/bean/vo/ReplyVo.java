package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

/**
 * 用于页面显示的类, 将一些用于数据库储存的内容转换为合适内容
 */
public class ReplyVo {
    private int replyId; //回复ID
    private int topicId; //讨论区ID
    private int userId; //用户ID
    private String username; //vo特有 用户名
    private String content; //回复内容
    private int contentType; //回复类型 0文本 1图片
    private String contentTypeStr; //vo特有 回复类型的字符串表示
    private Timestamp created; //回复时间

    public ReplyVo() {

    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getContentTypeStr() {
        return contentTypeStr;
    }

    public void setContentTypeStr(String contentTypeStr) {
        this.contentTypeStr = contentTypeStr;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ReplyVo{" +
                "replyId=" + replyId +
                ", topicId=" + topicId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                ", contentTypeStr='" + contentTypeStr + '\'' +
                ", created=" + created +
                '}';
    }
}
