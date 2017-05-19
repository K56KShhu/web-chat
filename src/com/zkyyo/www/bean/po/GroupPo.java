package com.zkyyo.www.bean.po;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类, 对应于数据库中的usergroup
 */
public class GroupPo {
    private int groupId; //小组ID
    private String name; //小组名
    private String description; //描述
    private int population; //人数
    private Timestamp created; //创建时间

    public GroupPo() {

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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!(obj instanceof GroupPo)) {
            return false;
        }
        GroupPo other = (GroupPo) obj;
        return groupId == other.getGroupId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public String toString() {
        return "GroupPo{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", population=" + population +
                ", created=" + created +
                '}';
    }
}

