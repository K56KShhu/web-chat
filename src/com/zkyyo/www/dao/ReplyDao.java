package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReplyPo;

import java.util.List;

public interface ReplyDao {
    void addReply(ReplyPo replyPo);
    List<ReplyPo> selectReplysByTopicId(int topicId);
    List<ReplyPo> selectReplysByTopicId(int startIndex, int rowsOnePage, int topicId);
    ReplyPo selectReplyByReplyId(int replyId);
    void deleteReply(int replyId);
    int getTotalRow(int topicId);
}
