package com.zkyyo.www.service;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.dao.GroupDao;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

public class GroupService {
    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_DESCRIPTION_LENGTH = 100;
    private static final int MIN_DESCRIPTION_LENGTH = 0;
    private static final int MAX_NAME_LENGTH = 16;
    private static final int MIN_NAME_LENGTH = 1;

    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public boolean isValidId(String groupId) {
        return CheckUtil.isValidId(groupId, MAX_ID_LENGTH);
    }

    public boolean isValidName(String name) {
        return CheckUtil.isValidString(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    public boolean isValidDesc(String desc) {
        return CheckUtil.isValidString(desc, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    public boolean isExisted(int groupId) {
        return findGroup(groupId) != null;
    }

    public void addGroup(GroupPo groupPo) {
        groupDao.addGroup(groupPo);
    }

    public void addUser(int groupId, int userId) {
        groupDao.addUserInGroup(groupId, userId);
    }

    public void addTopic(int groupId, int topicId) {
        groupDao.addGroupInTopic(groupId, topicId);
    }

    public void deleteGroup(int groupId) {
        groupDao.deleteGroup(groupId);
    }

    public void removeUser(int groupId, int userId) {
        groupDao.removeUserInGroup(groupId, userId);
    }

    public void removeTopic(int groupId, int userId) {
        groupDao.removeTopicInGroup(groupId, userId);
    }

    public GroupPo findGroup(int groupId) {
        return groupDao.selectGroup(groupId);
    }

    public List<GroupPo> queryGroups(String name) {
        return groupDao.selectGroupsByName(name);
    }

    public List<GroupPo> queryGroups() {
        return groupDao.selectGroups();
    }
}
