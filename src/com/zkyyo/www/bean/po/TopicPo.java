package com.zkyyo.www.bean.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class TopicPo implements Serializable {
    private int topicId;
    private String title;
    private String description;
    private int creatorId;
    private int lastModifyId;
    private int isPrivate;
    private int replyAccount;
    private Timestamp lastTime;
    private Timestamp created;

    public TopicPo() {

    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(int lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    public int getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(int isPrivate) {
        this.isPrivate = isPrivate;
    }

    public int getReplyAccount() {
        return replyAccount;
    }

    public void setReplyAccount(int replyAccount) {
        this.replyAccount = replyAccount;
    }

    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
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
        if (!(obj instanceof TopicPo)) {
            return false;
        }
        TopicPo other = (TopicPo) obj;
        return topicId == other.getTopicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId);
    }

    @Override
    public String toString() {
        return "TopicPo{" +
                "topicId=" + topicId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creatorId=" + creatorId +
                ", lastModifyId=" + lastModifyId +
                ", isPrivate=" + isPrivate +
                ", replyAccount=" + replyAccount +
                ", lastTime=" + lastTime +
                ", created=" + created +
                '}';
    }
}
