package com.zkyyo.www.service;

import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.bean.po.TopicPo;

import java.util.*;

public class TopicService {
    public static final int NOT_PRIVATE = 0;
    public static final int IS_PRIVATE = 1;
    private TopicDao topicDao;

    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    public List<TopicPo> findTopics() {
        return topicDao.selectTopics();
    }

    public void deleteTopic(int topicId) {
        topicDao.deleteTopicByTopicId(topicId);
    }

    public boolean isValidId(String topicId) {
        return true;
    }

    public boolean isExisted(int topicId) {
        return true;
    }

    public boolean isValidTitle(String title) {
        return true;
    }

    public boolean isValidDescription(String desc) {
        return true;
    }

    public void addTopic(TopicPo topicPo) {
        topicDao.addTopic(topicPo);
    }

    public TopicPo findTopic(int id) {
        return topicDao.selectTopicByTopicId(id);
    }

    public void updateTopic(TopicPo topicPo) {
        topicDao.updateTopic(topicPo);
    }

    public List<TopicPo> findTopicsByKeys(String keys) {
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>(); //避免关键字重复
        Collections.addAll(keySet, keys.trim().split(regex)); //将字符串拆分为关键字
        return topicDao.selectPossibleTopicsByTitle(keySet);
    }
}
