package com.zkyyo.www.bean.vo;

import java.sql.Timestamp;

/**
 * 用于页面显示的类, 将一些用于数据库储存的内容转换为合适内容
 */
public class UserVo {
    private int userId; //用户ID
    private String username; //用户名
    private String sex; //vo特有 英文sex的中文表示
    private String email; //邮箱
    private int status; //状态 -2被封印, -1审核不通过, 0审核中, 1正常
    private String statusStr; //vo特有 状态的字符串表示
    private Timestamp created; //注册时间

    public UserVo() {

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", created=" + created +
                '}';
    }
}
