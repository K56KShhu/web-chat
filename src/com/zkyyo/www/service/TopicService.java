package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.dao.impl.TopicDaoJdbcImpl;
import com.zkyyo.www.util.CheckUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopicService {
    public static final int ACCESS_PUBLIC = 0;
    public static final int ACCESS_PRIVATE = 1;
    public static final int ACCESS_ALL = 2;

    public static final int ORDER_BY_REPLY_ACCOUNT = 0;
    public static final int ORDER_BY_LAST_TIME = 1;
    public static final int ORDER_BY_CREATED = 2;
    public static final int ORDER_BY_ACCESS = 3;

    private static final int ROWS_ONE_PAGE = 10;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_TITLE_LENGTH = 60;
    private static final int MIN_TITLE_LENGTH = 1;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final int MIN_DESCRIPTION_LENGTH = 0;

    private TopicDao topicDao;

    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    /*
    public List<TopicPo> findTopics() {
        return topicDao.selectTopicsByOrder();
    }
    */

    public void deleteTopic(int topicId) {
        topicDao.deleteTopicByTopicId(topicId);
    }

    public boolean isValidId(String topicId) {
        return CheckUtil.isValidId(topicId, MAX_ID_LENGTH);
    }

    public boolean isExisted(int topicId) {
        return findTopic(topicId) != null;
    }

    public boolean isValidTitle(String title) {
        return CheckUtil.isValidString(title, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);
    }

    public boolean isValidDescription(String desc) {
        return CheckUtil.isValidString(desc, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
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

    /*
    public List<TopicPo> findTopicsByKeys(String keys) {
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>(); //避免关键字重复
        Collections.addAll(keySet, keys.trim().split(regex)); //将字符串拆分为关键字
        return topicDao.selectPossibleTopicsByTitle(keySet);
    }
    */

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
        } else if (ACCESS_ALL == type) {
            type = TopicDaoJdbcImpl.ACCESS_ALL;
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
        } else if (ACCESS_ALL == type) {
            accessType = TopicDaoJdbcImpl.ACCESS_ALL;
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
        } else if (ORDER_BY_ACCESS == order) {
            orderType = TopicDaoJdbcImpl.ORDER_BY_ACCESS;
        } else {
            return null;
        }

        //分页系统
        PageBean<TopicPo> pageBean = new PageBean<>(currentPage, topicDao.getTotalRow(accessType), ROWS_ONE_PAGE);
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

    public boolean isPrivate(int topicId) {
        TopicPo topic = findTopic(topicId);
        return topic.getIsPrivate() == TopicDaoJdbcImpl.ACCESS_PRIVATE;
    }

}
