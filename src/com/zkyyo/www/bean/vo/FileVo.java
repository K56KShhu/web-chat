package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

public class FileVo {
    private int fileId;
    private int apply; // 1图片分享 2文件分享
    private String applyStr; //vo
    private int userId;
    private String username; //vo
    private int topicId;
    private String path;
    private String shortName; //vo
    private Timestamp created;

    public FileVo() {

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

    public String getApplyStr() {
        return applyStr;
    }

    public void setApplyStr(String applyStr) {
        this.applyStr = applyStr;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "FileVo{" +
                "fileId=" + fileId +
                ", apply=" + apply +
                ", applyStr='" + applyStr + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", topicId=" + topicId +
                ", path='" + path + '\'' +
                ", shortName='" + shortName + '\'' +
                ", created=" + created +
                '}';
    }
}
