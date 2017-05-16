package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.ReplyDao;
import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

public class ReplyService {
    public static final int CONTENT_TEXT = 1;
    public static final int CONTENT_IMAGE = 2;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_CONTENT_LENGTH = 255;
    private static final int MIN_CONTENT_LENGTH = 1;

    private static final int ROWS_ONE_PAGE = 10;

    private ReplyDao replyDao;

    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }

    public boolean isValidId(String id) {
        return CheckUtil.isValidId(id, MAX_ID_LENGTH);
    }

    public boolean isValidContent(String content) {
        return CheckUtil.isValidString(content, MIN_CONTENT_LENGTH, MAX_CONTENT_LENGTH);
    }

    public boolean isExisted(int id) {
        return findReply(id) != null;
    }

    public void addReply(ReplyPo replyPo) {
        replyDao.addReply(replyPo);
    }

    public void deleteReply(int replyId) {
        replyDao.deleteReply(replyId);
    }

    public ReplyPo findReply(int replyId) {
        return replyDao.selectReplyByReplyId(replyId);
    }

    public PageBean<ReplyPo> findReplys(int topicId, int currentPage) {
        PageBean<ReplyPo> pageBean = new PageBean<>(currentPage, replyDao.getTotalRow(topicId), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        List<ReplyPo> replys = replyDao.selectReplysByTopicId(startIndex, ROWS_ONE_PAGE, topicId);
        pageBean.setList(replys);
        return pageBean;
    }
}
