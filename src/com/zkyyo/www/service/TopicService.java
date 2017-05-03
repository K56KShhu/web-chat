package com.zkyyo.www.service;

import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.po.TopicPo;

import java.util.List;

public class TopicService {
    private TopicDao topicDao;

    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    public List<TopicPo> findTopics() {
        return topicDao.selectTopics();
    }
    
    public boolean isValidId(String topicId) {
        return true;
    }
    
    public TopicPo findTopic(int id) {
        return topicDao.selectTopicByTopicId(id);
    }
}
