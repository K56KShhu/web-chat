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
    /**
     * 讨论区类型的标识符, 表示公开讨论区
     */
    public static final int ACCESS_PUBLIC = 0;
    /**
     * 讨论区类型的标识符, 表示授权讨论区
     */
    public static final int ACCESS_PRIVATE = 1;
    /**
     * 讨论区类型的标识符, 表示所有讨论区
     */
    public static final int ACCESS_ALL = 2;

    /**
     * 排序标识符, 排序依据为回复数量
     */
    public static final int ORDER_BY_REPLY_ACCOUNT = 0;
    /**
     * 排序标识符, 排序依据为最后回复时间
     */
    public static final int ORDER_BY_LAST_TIME = 1;
    /**
     * 排序标识符, 排序依据为创建时间
     */
    public static final int ORDER_BY_CREATED = 2;
    /**
     * 排序标识符, 排序依据为讨论区类型
     */
    public static final int ORDER_BY_ACCESS = 3;

    /**
     * 分页系统, 每一页显示的行数
     */
    private static final int ROWS_ONE_PAGE = 10;

    /**
     * 讨论区唯一标识ID的最大长度
     */
    private static final int MAX_ID_LENGTH = 10;
    /**
     * 讨论区标题最大长度
     */
    private static final int MAX_TITLE_LENGTH = 60;
    /**
     * 讨论区标题最小长度
     */
    private static final int MIN_TITLE_LENGTH = 1;
    /**
     * 讨论区描述最大长度
     */
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    /**
     * 讨论区描述最小长度
     */
    private static final int MIN_DESCRIPTION_LENGTH = 0;

    /**
     * 数据库操作相关接口
     */
    private TopicDao topicDao;

    /**
     * 构建对象
     *
     * @param topicDao 传入的数据库操作接口
     */
    public TopicService(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    /**
     * 校验讨论区ID是否合法
     *
     * @param topicId 待校验的讨论区ID
     * @return true合法, false不合法
     */
    public boolean isValidId(String topicId) {
        return CheckUtil.isValidId(topicId, MAX_ID_LENGTH);
    }

    /**
     * 校验讨论区是否存在
     *
     * @param topicId 待校验的讨论区ID
     * @return true存在, false不存在
     */
    public boolean isExisted(int topicId) {
        return findTopic(topicId) != null;
    }

    /**
     * 校验标题是否合法
     *
     * @param title 待校验的标题
     * @return true合法, false不合法
     */
    public boolean isValidTitle(String title) {
        return CheckUtil.isValidString(title, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);
    }

    /**
     * 校验描述是否合法
     *
     * @param desc 待校验的描述
     * @return true合法, false不合法
     */
    public boolean isValidDescription(String desc) {
        return CheckUtil.isValidString(desc, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    /**
     * 添加讨论区信息
     *
     * @param topicPo 待添加的讨论区对象
     */
    public void addTopic(TopicPo topicPo) {
        topicDao.addTopic(topicPo);
    }

    /**
     * 删除讨论区信息
     *
     * @param topicId 待删除的讨论区ID
     */
    public void deleteTopic(int topicId) {
        topicDao.deleteTopicByTopicId(topicId);
    }

    /**
     * 通过讨论区ID精确查询讨论区
     *
     * @param id 待查询的讨论区ID
     * @return 存在返回讨论区对象, 不存在返回null
     */
    public TopicPo findTopic(int id) {
        return topicDao.selectTopicByTopicId(id);
    }

    /**
     * 通过标题关键字, 查询指定类型的所有讨论区, 同时进行分页
     *
     * @param type        讨论区类型
     * @param currentPage 当前页数
     * @param keys        关键字集合
     * @return 封装的分页对象, 信息输入不合法时返回null
     */
    public PageBean<TopicPo> queryTopics(int type, int currentPage, String keys) {
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

        //将搜索语句拆分为关键词集合
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>();
        Collections.addAll(keySet, keys.trim().split(regex));

        //分页系统
        PageBean<TopicPo> pageBean = new PageBean<>(currentPage, topicDao.getTotalRowByTitle(type, keySet), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        Set<TopicPo> topicSet = topicDao.selectTopicsByTitle(type, keySet, startIndex, ROWS_ONE_PAGE);
        List<TopicPo> topicList = new ArrayList<>(topicSet);
        pageBean.setList(topicList);
        return pageBean;
    }

    /**
     * 查询指定类型的所有讨论区, 同时进行分页和排序
     *
     * @param type        讨论区类型
     * @param currentPage 当前页数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 封装的分页对象, 信息输入不合法时返回null
     */
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

    /**
     * 查询授权指定小组的所有讨论区
     *
     * @param groupId 指定小组ID
     * @return 返回一个size可为0的讨论区列表
     */
    public List<TopicPo> queryTopicsByGroup(int groupId) {
        return topicDao.selectTopicsByGroup(groupId);
    }

    /**
     * 查询授权多个小组的所有讨论区
     *
     * @param groupIds 多个小组ID的集合
     * @return 返回一个size可为0的讨论区集合
     */
    public Set<TopicPo> queryTopicsByGroups(Set<Integer> groupIds) {
        return topicDao.selectTopicsByGroups(groupIds);
    }

    public List<Integer> queryTopicsBeforeDaysAboutLastReply(int type, int days) {
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

        return topicDao.selectTopicsBeforeDaysAboutLastReply(accessType, days);
    }

    /**
     * 更新讨论区信息
     *
     * @param topicPo 包含最新信息的讨论区对象
     */
    public void updateTopic(TopicPo topicPo) {
        topicDao.updateTopic(topicPo);
    }

    /**
     * 校验讨论区是否为授权类型
     *
     * @param topicId 待校验的讨论区ID
     * @return true为授权类型, false为公开类型
     */
    public boolean isPrivate(int topicId) {
        TopicPo topic = findTopic(topicId);
        return topic.getIsPrivate() == TopicDaoJdbcImpl.ACCESS_PRIVATE;
    }

    /**
     * 获得指定讨论区授权的所有小组ID集合
     *
     * @param topicId 指定讨论区ID
     * @return 返回一个size可以0的小组ID集合
     */
    public Set<Integer> getGroups(int topicId) {
        return topicDao.selectGroupsByTopicId(topicId);
    }

    /**
     * 校验指定讨论区是否授权某一小组
     *
     * @param groupId 指定的讨论区ID
     * @param topicId 小组ID
     * @return true授权, false未授权
     */
    public boolean isTopicHasGroup(int groupId, int topicId) {
        Set<Integer> groups = getGroups(topicId);
        for (int group : groups) {
            if (groupId == group) {
                return true;
            }
        }
        return false;
    }
}
