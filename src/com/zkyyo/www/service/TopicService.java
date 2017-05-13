package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.dao.impl.TopicDaoJdbcImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopicService {
    public static final int ACCESS_PUBLIC = 0;
    public static final int ACCESS_PRIVATE = 1;

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

    public PageBean<TopicPo> queryTopics(int type, String keys, int currentPage) {
        //处理关键字
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>(); //避免关键字重复
        Collections.addAll(keySet, keys.trim().split(regex)); //将字符串拆分为关键字

        //根据讨论区类型查询
        if (ACCESS_PUBLIC == type) {
            type = TopicDaoJdbcImpl.ACCESS_PUBLIC;
        } else if (ACCESS_PRIVATE == type) {
            type = TopicDaoJdbcImpl.ACCESS_PRIVATE;
        } else {
            return null;
        }

        //分页系统
        PageBean<TopicPo> pageBean = new PageBean<>(currentPage, topicDao.getTotalRowByTitle(keySet), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        Set<TopicPo> topicSet = topicDao.selectTopicsByTitle(type, keySet, startIndex, ROWS_ONE_PAGE);
        List<TopicPo> topicList = new ArrayList<>(topicSet);
        pageBean.setList(topicList);
        return pageBean;
    }

    public PageBean<TopicPo> queryTopics(int type, int currentPage, int order, boolean isReverse) {
        //根据讨论区类型查询
        int accessType;
        if (ACCESS_PUBLIC == type) {
            accessType = TopicDaoJdbcImpl.ACCESS_PUBLIC;
        } else if (ACCESS_PRIVATE == type) {
            accessType = TopicDaoJdbcImpl.ACCESS_PRIVATE;
        } else {
            return null;
        }

        //获取排序依据
        int orderType;
        if (ORDER_BY_REPLY_ACCOUNT == order) {
            orderType = TopicDaoJdbcImpl.ORDER_BY_REPLY_ACCOUNT;
        } else if (ORDER_BY_LAST_TIME == order) {
            orderType = TopicDaoJdbcImpl.ORDER_BY_LAST_TIME;
        } else if (ORDER_BY_CREATED == order) {
            orderType = TopicDaoJdbcImpl.ORDER_BY_CREATED;
        } else {
            return null;
        }

        //分页系统
        PageBean<TopicPo> pageBean = new PageBean<>(currentPage, topicDao.getTotalRow(), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        List<TopicPo> topics = topicDao.selectTopicsByOrder(accessType, startIndex, ROWS_ONE_PAGE, orderType, isReverse);
        pageBean.setList(topics);
        return pageBean;
    }

    public List<TopicPo> queryTopicsByGroup(int groupId) {
        return topicDao.selectTopicsByGroup(groupId);
    }

    public Set<TopicPo> queryTopicsByGroups(Set<Integer> groupIds) {
        return topicDao.selectTopicsByGroups(groupIds);
    }

    public Set<Integer> getGroups(int topicId) {
        return topicDao.selectGroupsByTopicId(topicId);
    }

}
