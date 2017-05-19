package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.ReplyDao;
import com.zkyyo.www.bean.po.ReplyPo;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

/**
 * 提供与回复信息相关, 用于逻辑处理的代码
 * 该类包含Cookie信息的增删查方法, 校验方法
 */
public class ReplyService {
    /**
     * 回复类型标识, 表示回复文本
     */
    public static final int CONTENT_TEXT = 0;
    /**
     * 回复类型标识, 表示回复图片
     */
    public static final int CONTENT_IMAGE = 1;

    /**
     * 回复唯一标识ID最大长度
     */
    private static final int MAX_ID_LENGTH = 10;
    /**
     * 回复文本最大长度
     */
    private static final int MAX_CONTENT_LENGTH = 255;
    /**
     * 回复文本最小长度
     */
    private static final int MIN_CONTENT_LENGTH = 1;

    /**
     * 分页系统, 每一页显示的行数
     */
    private static final int ROWS_ONE_PAGE = 10;

    /**
     * 数据库操作相关接口
     */
    private ReplyDao replyDao;

    /**
     * 构建对象
     *
     * @param replyDao 传入数据库操作接口
     */
    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }

    /**
     * 校验回复ID是否合法
     *
     * @param id 待校验的回复ID
     * @return true合法, false非法
     */
    public boolean isValidId(String id) {
        return CheckUtil.isValidId(id, MAX_ID_LENGTH);
    }

    /**
     * 校验回复内容是否合法
     *
     * @param content 待校验的内容
     * @return true合法, false非法
     */
    public boolean isValidContent(String content) {
        return CheckUtil.isValidString(content, MIN_CONTENT_LENGTH, MAX_CONTENT_LENGTH);
    }

    /**
     * 校验回复是否存在
     *
     * @param id 待校验的回复ID
     * @return true存在, false不存在
     */
    public boolean isExisted(int id) {
        return findReply(id) != null;
    }

    /**
     * 添加回复信息
     *
     * @param replyPo 待添加的回复对象
     */
    public void addReply(ReplyPo replyPo) {
        replyDao.addReply(replyPo);
    }

    /**
     * 删除回复信息
     *
     * @param replyId 待删除的回复ID
     */
    public void deleteReply(int replyId) {
        replyDao.deleteReply(replyId);
    }

    /**
     * 通过回复ID精确查询回复
     *
     * @param replyId 待查询的回复ID
     * @return 存在返回回复对象, 不存在返回null
     */
    public ReplyPo findReply(int replyId) {
        return replyDao.selectReplyByReplyId(replyId);
    }

    /**
     * 查询指定讨论区下的所有回复, 同时进行分页
     *
     * @param topicId     指定讨论区ID
     * @param currentPage 当前页数
     * @return 封装的分页对象
     */
    public PageBean<ReplyPo> findReplys(int topicId, int currentPage) {
        PageBean<ReplyPo> pageBean = new PageBean<>(currentPage, replyDao.getTotalRow(topicId), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        List<ReplyPo> replys = replyDao.selectReplysByTopicId(startIndex, ROWS_ONE_PAGE, topicId);
        pageBean.setList(replys);
        return pageBean;
    }
}
