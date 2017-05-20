package com.zkyyo.www.bean.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 实体类, 对应于数据库中的reply
 */
public class ReplyPo implements Serializable {
    private int replyId; //回复ID
    private int topicId; //讨论区ID
    private int userId; //用户ID
    private String content; //内容
    private int contentType; //回复类型, 0文本 1图片
    private Timestamp created; //建立时间

    public ReplyPo() {

    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
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
        if (!(obj instanceof ReplyPo)) {
            return false;
        }
        ReplyPo other = (ReplyPo) obj;
        return replyId == other.getReplyId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyId);
    }

    @Override
    public String toString() {
        return "ReplyPo{" +
                "replyId=" + replyId +
                ", topicId=" + topicId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                ", created=" + created +
                '}';
    }
}
