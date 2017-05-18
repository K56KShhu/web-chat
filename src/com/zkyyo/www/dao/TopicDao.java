package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.TopicPo;

import java.util.List;
import java.util.Set;

public interface TopicDao {
    void addTopic(TopicPo topicPo);

    void deleteTopicByTopicId(int topicId);

    TopicPo selectTopicByTopicId(int id);

    List<TopicPo> selectTopicsByOrder(int type, int startIndex, int ROWS_ONE_PAGE, int order, boolean isReverse);

    Set<TopicPo> selectTopicsByTitle(int type, Set<String> keys, int startIndex, int rowsOnePage);

    List<TopicPo> selectTopicsByGroup(int groupId);

    Set<TopicPo> selectTopicsByGroups(Set<Integer> groupIds);

    Set<Integer> selectGroupsByTopicId(int topicId);

    void updateTopic(TopicPo topicPo);

    int getTotalRow(int accessType);

    int getTotalRowByTitle(Set<String> keys);
}
