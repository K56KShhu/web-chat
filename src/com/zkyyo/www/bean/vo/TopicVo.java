package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

/**
 * 用于页面显示的类, 将一些用于数据库储存的内容转换为合适内容
 */
public class TopicVo {
    private int topicId; //讨论区ID
    private String title; //讨论区名字
    private String description; //描述
    private int creatorId; //创建者ID
    private String creatorUsername; //vo特有 创建者用户名
    private int lastModifyId; //最后修改者ID
    private String lastModifyUsername; //vo特有 最后修改者用户名
    private int isPrivate; //是否为授权讨论区 0不是, 1是
    private String isPrivateStr; //vo特有 讨论区类型的字符串表示
    private int replyAccount; //回复总数
    private Timestamp lastTime; //最后回复时间
    private Timestamp created; //创建时间

    public TopicVo() {

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
