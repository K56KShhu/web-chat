package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.GroupPo;

import java.util.List;

public interface GroupDao {
    List<GroupPo> selectGroups();
    GroupPo selectGroup(int groupId);
    List<GroupPo> selectGroupsByName(String name);
    void removeUserInGroup(int groupId, int userId);
    void addUserInGroup(int groupId, int userId);
    void deleteGroup(int groupId);
    void addGroup(GroupPo groupPo);
    void addGroupInTopic(int groupId, int topicId);
}
