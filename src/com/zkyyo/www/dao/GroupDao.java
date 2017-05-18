package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.GroupPo;

import java.util.List;

public interface GroupDao {
    void addGroup(GroupPo groupPo);

    void deleteGroup(int groupId);

    GroupPo selectGroup(int groupId);

    List<GroupPo> selectGroups();

    List<GroupPo> selectGroupsByName(String name);

    List<GroupPo> selectGroupsByTopic(int topicId);

    void addUserInGroup(int groupId, int userId);

    void removeUserInGroup(int groupId, int userId);

    void addGroupInTopic(int groupId, int topicId);

    void removeGroupInTopic(int groupId, int topicId);
}
