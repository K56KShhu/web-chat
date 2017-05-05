package com.zkyyo.www.service;

import com.zkyyo.www.dao.ReplyDao;
import com.zkyyo.www.po.ReplyPo;

import java.util.List;

public class ReplyService {
    private ReplyDao replyDao;

    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }

    public boolean isValidId(String id) {
        return true;
    }

    public boolean isExisted(int id) {
        return true;
    }

    public boolean isValidContent(String content) {
        return true;
    }

    public void addReply(ReplyPo replyPo) {
        replyDao.addReply(replyPo);
    }

    public List<ReplyPo> findReplys(int topicId) {
        return replyDao.selectReplys(topicId);
    }
}
