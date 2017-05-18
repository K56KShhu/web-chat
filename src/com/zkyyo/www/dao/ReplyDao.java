package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReplyPo;

import java.util.List;

public interface ReplyDao {
    void addReply(ReplyPo replyPo);

    void deleteReply(int replyId);

    ReplyPo selectReplyByReplyId(int replyId);

    List<ReplyPo> selectReplysByTopicId(int startIndex, int rowsOnePage, int topicId);

    int getTotalRow(int topicId);
}
