package com.zkyyo.www.dao;

import com.zkyyo.www.po.TopicPo;

import java.util.List;

public interface TopicDao {
    List<TopicPo> selectTopics();
    TopicPo selectTopicByTopicId(int id);
    void addTopic(TopicPo topicPo);
    void deleteTopicByTopicId(int topicId);
    void updateTopic(TopicPo topicPo);
}
