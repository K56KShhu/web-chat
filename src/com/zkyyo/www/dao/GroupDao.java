package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.GroupPo;

import java.util.List;

public interface GroupDao {
    GroupPo selectGroup(int groupId);
    List<GroupPo> selectGroups();
    List<GroupPo> selectGroupsByName(String name);
    List<GroupPo> selectGroupsByTopic(int topicId);
    void removeUserInGroup(int groupId, int userId);
    void removeTopicInGroup(int groupId, int topicId);
    void addUserInGroup(int groupId, int userId);
    void deleteGroup(int groupId);
    void addGroup(GroupPo groupPo);
    void addGroupInTopic(int groupId, int topicId);
}
