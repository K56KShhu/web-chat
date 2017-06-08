package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.TopicPo;

import java.util.List;
import java.util.Set;

/**
 * 定义与讨论区相关, 涉及数据库操作的接口
 */
public interface TopicDao {
    /**
     * 向数据库中插入讨论区信息
     *
     * @param topicPo 待插入的讨论区对象
     */
    void addTopic(TopicPo topicPo);

    /**
     * 删除数据库中指定讨论区ID的讨论区信息
     *
     * @param topicId 待删除的讨论区ID
     */
    void deleteTopicByTopicId(int topicId);

    /**
     * 获取数据库中指定讨论区ID的讨论区信息
     *
     * @param topicId 待获取的讨论区ID
     * @return 存在返回讨论区对象, 否则返回null
     */
    TopicPo selectTopicByTopicId(int topicId);

    /**
     * 获取数据库中指定类型的多个讨论区信息, 同时进行分页和排序
     *
     * @param type          讨论区类型
     * @param startIndex    起始下标
     * @param ROWS_ONE_PAGE 获取总数
     * @param order         排序依据
     * @param isReverse     是否降序
     * @return 包含多个讨论区信息的列表, 不包含任何讨论区则返回size为0的列表
     */
    List<TopicPo> selectTopicsByOrder(int type, int startIndex, int ROWS_ONE_PAGE, int order, boolean isReverse);

    /**
     * 获取数据库中指定类型, 且标题符合关键词的多个讨论区信息, 同时进行分页
     *
     * @param type        讨论区类型
     * @param keys        关于标题的关键词集合
     * @param startIndex  起始下标
     * @param rowsOnePage 获取总数
     * @return 包含多个讨论区信息的列表, 不包含任何讨论区则返回size为0的列表
     */
    Set<TopicPo> selectTopicsByTitle(int type, Set<String> keys, int startIndex, int rowsOnePage);

    /**
     * 获取数据库中与指定小组关联的所有讨论区信息
     *
     * @param groupId 指定小组的ID
     * @return 包含多个讨论区信息的列表, 不包含任何讨论区则返回size为0的列表
     */
    List<TopicPo> selectTopicsByGroup(int groupId);

    /**
     * 获取数据库中与多个小组关联的所有讨论区信息
     *
     * @param groupIds 多个小组的ID集合
     * @return 包含多个讨论区信息的集合, 不包含任何讨论区则返回size为0的集合
     */
    Set<TopicPo> selectTopicsByGroups(Set<Integer> groupIds);

    /**
     * 获取数据库中指定类型讨论区中, 最后回复时间距离现在多于特定天数的讨论区ID
     * @param accessType 指定类型
     * @param days 相隔天数
     * @return 包含符合条件的所有讨论区ID的集合, 不包含任何讨论区ID则返回size为0的列表
     */
    List<Integer> selectTopicsBeforeDaysAboutLastReply(int accessType, int days);

    /**
     * 更新数据库中讨论区信息
     *
     * @param topicPo 包含最新信息的讨论区对象
     */
    void updateTopic(TopicPo topicPo);

    /**
     * 获取数据库中与指定讨论区有关的所有小组ID
     *
     * @param topicId 指定讨论区的ID
     * @return 包含所有小组ID的集合, 不包含任何小组ID则返回size为0的集合
     */
    Set<Integer> selectGroupsByTopicId(int topicId);

    /**
     * 获取数据库中指定讨论区类型的所有讨论区数量
     *
     * @param accessType 讨论区类型
     * @return 总数量
     */
    int getTotalRow(int accessType);

    /**
     * 获取数据库中指定讨论区类型, 且标题符合关键词的所有讨论区数量
     *
     * @param accessType 讨论区类型
     * @param keys       关于标题的关键词集合
     * @return 总数量
     */
    int getTotalRow(int accessType, Set<String> keys);
}
