package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.TopicPo;

import java.util.List;
import java.util.Set;

public interface TopicDao {
    List<TopicPo> selectTopics();
    List<TopicPo> selectPossibleTopicsByTitle(Set<String> keys);
    TopicPo selectTopicByTopicId(int id);
    void addTopic(TopicPo topicPo);
    void deleteTopicByTopicId(int topicId);
    void updateTopic(TopicPo topicPo);
}
