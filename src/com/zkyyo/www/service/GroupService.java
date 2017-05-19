package com.zkyyo.www.service;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.dao.GroupDao;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

/**
 * 提供与小组信息相关, 用于逻辑处理的代码
 * 该类包含增删查改方法和校验方法
 */
public class GroupService {
    /**
     * 小组唯一标识ID的最大长度
     */
    private static final int MAX_ID_LENGTH = 10;
    /**
     * 小组名的最大长度
     */
    private static final int MAX_NAME_LENGTH = 16;
    /**
     * 小组名的最小长度
     */
    private static final int MIN_NAME_LENGTH = 1;
    /**
     * 小组描述信息的最大长度
     */
    private static final int MAX_DESCRIPTION_LENGTH = 100;
    /**
     * 小组描述信息的最小长度
     */
    private static final int MIN_DESCRIPTION_LENGTH = 0;

    /**
     * 数据库操作相关接口
     */
    private GroupDao groupDao;

    /**
     * 构建对象
     *
     * @param groupDao 传入的数据库操作接口
     */
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    /**
     * 校验小组ID是否合法
     *
     * @param groupId 待校验的小组ID
     * @return true合法, false不合法
     */
    public boolean isValidId(String groupId) {
        return CheckUtil.isValidId(groupId, MAX_ID_LENGTH);
    }

    /**
     * 校验小组名是否合法
     *
     * @param name 待校验的小组名
     * @return true合法, false不合法
     */
    public boolean isValidName(String name) {
        return CheckUtil.isValidString(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    /**
     * 校验小组描述是否合法
     *
     * @param desc 待校验的小组描述
     * @return true合法, false不合法
     */
    public boolean isValidDesc(String desc) {
        return CheckUtil.isValidString(desc, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
    }

    /**
     * 校验小组是否存在
     *
     * @param groupId 待校验的小组ID
     * @return true存在, false不存在
     */
    public boolean isExisted(int groupId) {
        return findGroup(groupId) != null;
    }

    /**
     * 添加小组信息
     *
     * @param groupPo 待添加的小组对象
     */
    public void addGroup(GroupPo groupPo) {
        groupDao.addGroup(groupPo);
    }

    /**
     * 向小组中添加中成员
     *
     * @param groupId 小组ID
     * @param userId  待添加的用户ID
     */
    public void addUser(int groupId, int userId) {
        groupDao.addUserInGroup(groupId, userId);
    }

    /**
     * 讨论区中授权小组
     *
     * @param groupId 小组ID
     * @param topicId 讨论区ID
     */
    public void addTopic(int groupId, int topicId) {
        groupDao.addGroupInTopic(groupId, topicId);
    }

    /**
     * 删除指定小组
     *
     * @param groupId 待删除的小组ID
     */
    public void deleteGroup(int groupId) {
        groupDao.deleteGroup(groupId);
    }

    /**
     * 删除小组中的指定成员
     *
     * @param groupId 小组ID
     * @param userId  待添加的用户ID
     */
    public void removeUser(int groupId, int userId) {
        groupDao.removeUserInGroup(groupId, userId);
    }

    /**
     * 取消讨论区对小组的授权
     *
     * @param groupId 小组ID
     * @param userId  讨论区ID
     */
    public void removeTopic(int groupId, int userId) {
        groupDao.removeGroupInTopic(groupId, userId);
    }

    /**
     * 通过小组ID精确查询小组
     *
     * @param groupId 待查询的小组ID
     * @return 存在返回小组对象, 不存在返回null
     */
    public GroupPo findGroup(int groupId) {
        return groupDao.selectGroup(groupId);
    }

    /**
     * 获得所有小组信息
     *
     * @return 包含所有小组对象的列表
     */
    public List<GroupPo> queryGroups() {
        return groupDao.selectGroups();
    }

    /**
     * 通过小组名模糊查询小组
     *
     * @param name 小组模糊名
     * @return 包含相关小组对象的列表
     */
    public List<GroupPo> queryGroups(String name) {
        return groupDao.selectGroupsByName(name);
    }

    /**
     * 查询被某一指定讨论区授权的所有小组
     *
     * @param topicId 指定讨论区ID
     * @return 包含相关小组对象的列表
     */
    public List<GroupPo> queryGroupsByTopic(int topicId) {
        return groupDao.selectGroupsByTopic(topicId);
    }
}
