package com.zkyyo.www.dao;

import com.zkyyo.www.po.ReplyPo;

import java.util.List;

public interface ReplyDao {
    void addReply(ReplyPo replyPo);
    List<ReplyPo> selectReplysByTopicId(int topicId);
    ReplyPo selectReplyByReplyId(int replyId);
    void deleteReply(int replyId);
}
