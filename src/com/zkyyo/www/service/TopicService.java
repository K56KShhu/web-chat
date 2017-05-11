package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.dao.impl.TopicDaoJdbcImpl;

import java.util.*;

public class TopicService {
    public static final int NOT_PRIVATE = 0;
    public static final int IS_PRIVATE = 1;

    private static final int ROWS_ONE_PAGE = 10;

    public static final int ORDER_BY_REPLY_ACCOUNT = 0;
    public static final int ORDER_BY_LAST_TIME = 1;
    public static final int ORDER_BY_CREATED = 2;

    private TopicDao topicDao;

    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    public List<TopicPo> findTopics() {
        return topicDao.selectTopicsByOrder();
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

    public PageBean<TopicPo> queryTopics(int currentPage, String keys) {
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>(); //避免关键字重复
        Collections.addAll(keySet, keys.trim().split(regex)); //将字符串拆分为关键字

        PageBean<TopicPo> pageBean = new PageBean<>(currentPage, topicDao.getTotalRowByTitle(keySet), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        Set<TopicPo> topicSet =  topicDao.selectTopicsByTitle(startIndex, ROWS_ONE_PAGE, keySet);
        List<TopicPo> topicList = new ArrayList<>(topicSet);
        pageBean.setList(topicList);
        return pageBean;
    }

    public PageBean<TopicPo> queryTopics(int currentPage, int order, boolean isReverse) {
        PageBean<TopicPo> pageBean = new PageBean<>(currentPage, topicDao.getTotalRow(), ROWS_ONE_PAGE);
        List<TopicPo> topics;
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        if (ORDER_BY_REPLY_ACCOUNT == order) {
            topics = topicDao.selectTopicsByOrder(startIndex, ROWS_ONE_PAGE, TopicDaoJdbcImpl.ORDER_BY_REPLY_ACCOUNT, isReverse);
        } else if (ORDER_BY_LAST_TIME == order) {
            topics = topicDao.selectTopicsByOrder(startIndex, ROWS_ONE_PAGE, TopicDaoJdbcImpl.ORDER_BY_LAST_TIME, isReverse);
        } else if (ORDER_BY_CREATED == order) {
            topics = topicDao.selectTopicsByOrder(startIndex, ROWS_ONE_PAGE, TopicDaoJdbcImpl.ORDER_BY_CREATED, isReverse);
        } else {
            return null;
        }
        pageBean.setList(topics);
        return pageBean;
    }
}
