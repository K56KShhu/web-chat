package com.zkyyo.www.web;

import com.zkyyo.www.service.UserService;

import java.util.Set;

/**
 * 该类封装了用户的核心信息以及所有权限
 */
public class Access {
    private int userId; //用户ID
    private String username; //用户名
    private int status; //用户状态 -2被封印, -1审核不通过, 0审核中, 1审核通过
    private Set<String> roles; //角色 user普通用户, admin管理员, root超级管理员
    private Set<Integer> groups; //用户所在小组

    public Access() {

    }

    public Access(int userId, String username, int status, Set<String> roles, Set<Integer> groups) {
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.roles = roles;
        this.groups = groups;
    }

    /**
     * 判断用户是否属于指定角色
     *
     * @param s 角色
     * @return true属于, false不属于
     */
    public boolean isUserInRole(String s) {
        if (roles != null && !roles.isEmpty()) {
            if (roles.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否位于指定小组中
     *
     * @param groupId 指定小组ID
     * @return true位于指定小组, false不位于
     */
    public boolean isUserInGroup(int groupId) {
        if (!groups.isEmpty()) {
            if (groups.contains(groupId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否位于指定的多个小组的某一个
     *
     * @param groupSet 指定的多个小组ID集合
     * @return true位于其中任意一个小组, false不位于任何小组
     */
    public boolean isUserInGroups(Set<Integer> groupSet) {
        for (int topicGroup : groupSet) {
            if (isUserInGroup(topicGroup)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否属于多个角色之一, 或者位于指定的多个小组的某一个
     *
     * @param groupSet 指定的多个小组ID集合
     * @param roles    多个角色
     * @return true属于其中一个角色或位于其中一个小组, false都不属于
     */
    public boolean isUserApproved(Set<Integer> groupSet, String... roles) {
        //判断用户是否是指定角色
        for (String role : roles) {
            if (isUserInRole(role)) {
                return true;
            }
        }
        //判断用户是否处于授权小组中
        return isUserInGroups(groupSet);
    }

    /**
     * 判断用户的状态是否为正常
     *
     * @return true正常 false其他状态
     */
    public boolean isNormal() {
        return status == UserService.STATUS_NORMAL;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<Integer> getGroups() {
        return groups;
    }

    public void setGroups(Set<Integer> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Access{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", roles=" + roles +
                ", groups=" + groups +
                '}';
    }
}
