package com.zkyyo.www.bean.po;

import java.util.List;

public class GroupPo {
    private int groupId;
    private String name;
    private String description;
    private List<Integer> members;

    public GroupPo() {

    }

    public GroupPo(int groupId, String name, String description, List<Integer> members) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.members = members;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupPo{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", members=" + members +
                '}';
    }
}
