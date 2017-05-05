package com.zkyyo.www.dao;

import com.zkyyo.www.po.ReplyPo;

import java.util.List;

public interface ReplyDao {
    void addReply(ReplyPo replyPo);
    List<ReplyPo> selectReplys(int topicId);
}
