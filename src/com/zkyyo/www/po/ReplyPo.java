package com.zkyyo.www.po;

import java.sql.Timestamp;

public class ReplyPo {
    private int replyId;
    private int topicId;
    private int userId;
    private String content;
    private Timestamp created;

    public ReplyPo() {

    }

    public ReplyPo(int replyId, int topicId, int userId, String content, Timestamp created) {
        this.replyId = replyId;
        this.topicId = topicId;
        this.userId = userId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ReplyPo{" +
                "replyId=" + replyId +
                ", topicId=" + topicId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", created=" + created +
                '}';
    }
}
