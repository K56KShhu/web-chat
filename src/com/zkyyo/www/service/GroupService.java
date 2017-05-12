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

    public boolean isUserInGroup(int groupId, int userId) {
        return true;
    }

    public void addUser(int groupId, int userId) {
        groupDao.addUserInGroup(groupId, userId);
    }
}
