package com.zkyyo.www.web;

import java.util.Set;

public class Access {
    private int userId;
    private String username;
    private Set<String> roles;
    private Set<Integer> groups;

    public Access() {

    }

    public Access(int userId, String username, Set<String> roles, Set<Integer> groups) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.groups = groups;
    }

    public boolean isUserInRole(String s) {
        if (roles != null && !roles.isEmpty()) {
            if (roles.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserInGroup(int groupId) {
        if (!groups.isEmpty()) {
            if (groups.contains(groupId)) {
                return true;
            }
        }
        return false;
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
                ", roles=" + roles +
                ", groups=" + groups +
                '}';
    }
}
