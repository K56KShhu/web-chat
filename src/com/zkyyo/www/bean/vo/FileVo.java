package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

/**
 * 用于页面显示的类, 将一些用于数据库储存的内容转换为合适内容
 */
public class FileVo {
    private int fileId; //文件ID
    private int apply; //文件类型 0图片分享 1文件分享
    private String applyStr; //vo特有 文件类型的字符串表示
    private int userId; //文件上传者ID
    private String username; //vo特有 文件上传者用户名
    private int topicId; //讨论区ID
    private String path; //相对路径
    private String shortName; //vo特有 文件名
    private Timestamp created; //上传时间

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
