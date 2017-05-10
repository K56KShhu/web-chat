package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

public class ReplyVo {
    private int replyId;
    private int topicId;
    private int userId;
    private String username; //vo
    private String content;
    private int contentType; // 0文本 1图片
    private String contentTypeStr; // 0文本 1图片
    private Timestamp created;

    public ReplyVo() {

    }

    public ReplyVo(int replyId, int topicId, int userId, String username, String content, int contentType, String contentTypeStr, Timestamp created) {
        this.replyId = replyId;
        this.topicId = topicId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.contentType = contentType;
        this.contentTypeStr = contentTypeStr;
        this.created = created;
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
