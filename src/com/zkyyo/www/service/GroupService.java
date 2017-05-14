package com.zkyyo.www.service;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.dao.GroupDao;

import java.util.List;

public class GroupService {
    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<GroupPo> queryGroups() {
        return groupDao.selectGroups();
    }

    public boolean isValidId(String groupId) {
        return true;
    }

    public boolean isExisted(int groupId) {
        return true;
    }

    public GroupPo findGroup(int groupId) {
        return groupDao.selectGroup(groupId);
    }

    public void removeUser(int groupId, int userId) {
        groupDao.removeUserInGroup(groupId, userId);
    }

    public void removeTopic(int groupId, int userId) {
        groupDao.removeTopicInGroup(groupId, userId);
    }

    public boolean isUserInGroup(int groupId, int userId) {
        return true;
    }

    public void addUser(int groupId, int userId) {
        groupDao.addUserInGroup(groupId, userId);
    }

    public void deleteGroup(int groupId) {
        groupDao.deleteGroup(groupId);
    }

    public boolean isValidName(String name) {
        return true;
    }

    public boolean isValidDesc(String desc) {
        return true;
    }

    public void addGroup(GroupPo groupPo) {
        groupDao.addGroup(groupPo);
    }

    public List<GroupPo> queryGroups(String name) {
        return groupDao.selectGroupsByName(name);
    }

    public void addTopic(int groupId, int topicId) {
        groupDao.addGroupInTopic(groupId, topicId);
    }
}
