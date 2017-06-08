package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReplyPo;

import java.util.List;

/**
 * 定义与讨论区回复相关, 涉及数据库操作的接口
 */
public interface ReplyDao {
    /**
     * 向数据库中插入回复信息
     *
     * @param replyPo 待插入的回复对象
     */
    void addReply(ReplyPo replyPo);

    /**
     * 删除数据库中指定回复ID的回复信息
     *
     * @param replyId 待删除的回复ID
     */
    void deleteReply(int replyId);

    /**
     * 获取数据库中指定回复ID的回复信息
     *
     * @param replyId 待获取的回复ID
     * @return 存在返回回复对象, 否则返回null
     */
    ReplyPo selectReplyByReplyId(int replyId);

    /**
     * 获取数据库中指定讨论区ID的所有回复信息, 同时进行分页操作
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的回复总数
     * @param topicId     指定讨论区的ID
     * @return 包含回复信息的列表, 不包含任何回复信息则返回一个size为0的列表
     */
    List<ReplyPo> selectReplysByTopicId(int startIndex, int rowsOnePage, int topicId);

    /**
     * 获取数据库中指定讨论区ID的所有回复信息的行数
     *
     * @param topicId 指定讨论区的ID
     * @return 回复数量
     */
    int getTotalRow(int topicId);
}
