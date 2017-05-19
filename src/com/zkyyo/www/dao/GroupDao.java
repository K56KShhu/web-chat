package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.GroupPo;

import java.util.List;

/**
 * 定义与用户组相关, 涉及数据库操作的接口
 */
public interface GroupDao {
    /**
     * 向数据库中插入小组
     *
     * @param groupPo 待插入的小组
     */
    void addGroup(GroupPo groupPo);

    /**
     * 删除数据库中的指定小组
     *
     * @param groupId 待删除的小组ID
     */
    void deleteGroup(int groupId);

    /**
     * 获取数据库中指定小组ID的小组信息
     *
     * @param groupId 待获取的小组ID
     * @return 存在则返回小组对象, 否则返回null
     */
    GroupPo selectGroup(int groupId);

    /**
     * 获取数据库中的所有小组
     *
     * @return 包含所有小组的列表, 不包含任何小组则返回size为0的列表
     */
    List<GroupPo> selectGroups();

    /**
     * 获取数据库中指定小组名的所有小组
     *
     * @param name 待获取的小组名
     * @return 包含指定小组的列表, 不包含任何小组则返回size为0的列表
     */
    List<GroupPo> selectGroupsByName(String name);

    /**
     * 获取数据库中指定讨论区ID的所有小组
     *
     * @param topicId 指定的讨论区ID
     * @return 包含指定小组的列表, 不包含任何小组则返回size为0的列表
     */
    List<GroupPo> selectGroupsByTopic(int topicId);

    /**
     * 向数据库中插入用户与小组的关联
     *
     * @param groupId 待关联的小组ID
     * @param userId  待关联的用户ID
     */
    void addUserInGroup(int groupId, int userId);

    /**
     * 移除数据库中用户与小组的关联
     *
     * @param groupId 待移除关联的小组ID
     * @param userId  待移除关联的用户ID
     */
    void removeUserInGroup(int groupId, int userId);

    /**
     * 向数据库中插入小组与讨论区的关联
     *
     * @param groupId 待关联的小组ID
     * @param topicId 待关联的讨论区ID
     */
    void addGroupInTopic(int groupId, int topicId);

    /**
     * 移除数据库中小组与讨论区的关联
     *
     * @param groupId 待移除关联的小组ID
     * @param topicId 待移除关联的讨论区ID
     */
    void removeGroupInTopic(int groupId, int topicId);
}
