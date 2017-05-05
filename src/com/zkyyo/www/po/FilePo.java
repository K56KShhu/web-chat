package com.zkyyo.www.po;

import java.sql.Timestamp;

public class FilePo {
    private int fileId;
    private int apply; // 1图片分享 2文件分享
    private int userId;
    private int topicId;
    private String path;
    private Timestamp created;

    public FilePo() {

    }

    public FilePo(int fileId, int apply, int userId, int topicId, String path, Timestamp created) {
        this.fileId = fileId;
        this.apply = apply;
        this.userId = userId;
        this.topicId = topicId;
        this.path = path;
        this.created = created;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "FilePo{" +
                "fileId=" + fileId +
                ", apply=" + apply +
                ", userId=" + userId +
                ", topicId=" + topicId +
                ", path='" + path + '\'' +
                ", created=" + created +
                '}';
    }
}
