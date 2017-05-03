package com.zkyyo.www.web;

import java.util.HashSet;
import java.util.Set;

public class Access {
    private String username;
    private Set<String> roles;
    private Set<String> groups;

    public Access() {

    }

    public Access(String username, Set<String> roles, Set<String> groups) {
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

    public boolean isUserInGroups(String s) {
        if (groups != null && !groups.isEmpty()) {
            if (groups.contains(s)) {
                return true;
            }
        }
        return false;
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

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Access{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                ", groups=" + groups +
                '}';
    }

    public static void main(String[] args) {
        Set<String> roles = new HashSet<>();
        roles.add("user");
        Set<String> groups = new HashSet<>();
        groups.add("male");
        Access access = new Access("101", roles, groups);
        System.out.println(access.isUserInRole("user"));
        System.out.println(access.isUserInGroups("male"));
    }
}
