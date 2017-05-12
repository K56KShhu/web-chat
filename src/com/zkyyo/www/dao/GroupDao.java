package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.GroupPo;

import java.util.List;

public interface GroupDao {
    List<GroupPo> selectGroups();
    GroupPo selectGroup(int groupId);
    void removeUserInGroup(int groupId, int userId);
    void addUserInGroup(int groupId, int userId);
}
