package com.zkyyo.www.po;

import java.sql.Timestamp;

public class TopicPo {
    private int topicId;
    private String title;
    private String description;
    private int creatorId;
    private int lastModifyId;
    private int isPrivate;
    private int replyAccount;
    private Timestamp lastTime;
    private Timestamp created;

    public TopicPo() {

    }

    public TopicPo(int topicId, String title, String description, int creatorId, int lastModifyId, int isPrivate, int replyAccount, Timestamp lastTime, Timestamp created) {
        this.topicId = topicId;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.lastModifyId = lastModifyId;
        this.isPrivate = isPrivate;
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

    public int getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(int lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
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
}
