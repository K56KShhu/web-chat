package com.zkyyo.www.po;

import java.sql.Timestamp;

public class UserPo {
    private int userId;
    private String username;
    private String password;
    private String sex;
    private String email;
    private int status;
    private Timestamp created;

    public UserPo() {

    }

    public UserPo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //注册用
    public UserPo(String username, String password, String sex, String email) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
    }

    //获取数据
    public UserPo(int userId, String username, String password, String sex, String email, int status, Timestamp created) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.status = status;
        this.created = created;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "UserPo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", created=" + created +
                '}';
    }
}
