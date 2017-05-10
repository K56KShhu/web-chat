package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

public class TopicVo {
    private int topicId;
    private String title;
    private String description;
    private int creatorId;
    private String creatorUsername;
    private int lastModifyId;
    private String lastModifyUsername;
    private int isPrivate;
    private String isPrivateStr;
    private int replyAccount;
    private Timestamp lastTime;
    private Timestamp created;

    public TopicVo() {

    }

    public TopicVo(int topicId, String title, String description, int creatorId, String creatorUsername, int lastModifyId, String lastModifyUsername, int isPrivate, String isPrivateStr, int replyAccount, Timestamp lastTime, Timestamp created) {
        this.topicId = topicId;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.creatorUsername = creatorUsername;
        this.lastModifyId = lastModifyId;
        this.lastModifyUsername = lastModifyUsername;
        this.isPrivate = isPrivate;
        this.isPrivateStr = isPrivateStr;
        this.replyAccount = replyAccount;
        this.lastTime = lastTime;
        this.created = created;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public int getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(int lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    public String getLastModifyUsername() {
        return lastModifyUsername;
    }

    public void setLastModifyUsername(String lastModifyUsername) {
        this.lastModifyUsername = lastModifyUsername;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getIsPrivateStr() {
        return isPrivateStr;
    }

    public void setIsPrivateStr(String isPrivateStr) {
        this.isPrivateStr = isPrivateStr;
    }

    public int getReplyAccount() {
        return replyAccount;
    }

    public void setReplyAccount(int replyAccount) {
        this.replyAccount = replyAccount;
    }

    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "TopicVo{" +
                "topicId=" + topicId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creatorId=" + creatorId +
                ", creatorUsername='" + creatorUsername + '\'' +
                ", lastModifyId=" + lastModifyId +
                ", lastModifyUsername='" + lastModifyUsername + '\'' +
                ", isPrivate=" + isPrivate +
                ", isPrivateStr='" + isPrivateStr + '\'' +
                ", replyAccount=" + replyAccount +
                ", lastTime=" + lastTime +
                ", created=" + created +
                '}';
    }
}
