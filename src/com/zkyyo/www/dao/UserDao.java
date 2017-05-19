package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.UserPo;

import java.util.List;
import java.util.Set;

public interface UserDao {
    /**
     * 向数据库中插入用户与角色的关联
     *
     * @param userId 待关联的用户ID
     * @param role   待关联的角色
     */
    void addRole(int userId, String role);

    /**
     * 向数据库中插入用户信息
     *
     * @param userPo 待插入的用户对象
     */
    void addUser(UserPo userPo);

    /**
     * 移除数据库中用户与角色的关联
     *
     * @param userId 待移除的用户ID
     * @param role   待移除的角色
     */
    void deleteRoleInUser(int userId, String role);

    /**
     * 获取数据库中指定用户ID的用户信息
     *
     * @param id 待获取的用户ID
     * @return 存在返回用户对象, 否则返回null
     */
    UserPo selectUserByUserId(int id);

    /**
     * 获取数据库中指定用户名的用户信息
     *
     * @param username 待获取的用户名
     * @return 存在返回用户对象, 否则返回null
     */
    UserPo selectUserByUsername(String username);

    /**
     * 获取数据库中与指定用户名相关的多个用户信息, 同时进行分页
     *
     * @param status      用户状态
     * @param startIndex  起始下标
     * @param rowsOnePage 获取总数
     * @param username    用户名
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    List<UserPo> selectUsersByUsername(int status, int startIndex, int rowsOnePage, String username);

    /**
     * 获取数据库中的多个用户信息, 同时进行分页和排序
     *
     * @param status      用户状态
     * @param startIndex  起始下标
     * @param rowsOnePage 获取总数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    List<UserPo> selectUsersByOrder(int status, int startIndex, int rowsOnePage, int order, boolean isReverse);

    /**
     * 获取数据库中与指定角色关联的多个用户信息
     *
     * @param role 指定角色
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    List<UserPo> selectUsersByRole(String role);

    /**
     * 获取数据库中与指定小组关联的多个用户信息
     *
     * @param groupId 指定小组ID
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    List<UserPo> selectUsersByGroup(int groupId);

    /**
     * 获取数据库中指定用户ID的角色
     *
     * @param id 指定用户ID
     * @return 包含多个角色的集合, 不包含任何角色则返回size为0的集合
     */
    Set<String> selectRolesByUserId(int id);

    /**
     * 获取数据库中指定用户名的角色
     *
     * @param username 指定用户名
     * @return 包含多个角色的集合, 不包含任何角色则返回size为0的集合
     */
    Set<String> selectRolesByUsername(String username);

    /**
     * 获取数据库中与指定用户关联的多个小组信息
     *
     * @param id 指定用户ID
     * @return 包含多个小组ID的集合, 不包含任何小组则返回size为0的集合
     */
    Set<Integer> selectGroupsByUserId(int id);

    /**
     * 获取数据库中与指定用户关联的多个小组信息
     *
     * @param username 指定用户名
     * @return 包含多个小组ID的集合, 不包含任何小组则返回size为0的集合
     */
    Set<Integer> selectGroupsByUsername(String username);

    /**
     * 更新数据库中的部分用户信息
     *
     * @param userPo       包含最新信息的用户对象
     * @param updatedTypes 待更新部分的标识符列表
     */
    void update(UserPo userPo, List<Integer> updatedTypes);

    /**
     * 获取数据库中与指定用户名相关的用户总数
     *
     * @param username 指定用户名
     * @return 相关的用户总数
     */
    int getTotalRow(String username);

    /**
     * 获取数据库中指定状态的用户总数
     *
     * @param status 状态
     * @return 相关的用户总数
     */
    int getTotalRowByStatus(int status);

    /**
     * 获取数据库中所有用户的总数
     *
     * @return 所有用户总数
     */
    int getTotalRow();

    /**
     * 获取数据库中指定用户状态, 且与指定用户名相关的用户总数
     *
     * @param status   状态
     * @param username 指定用户名
     * @return 相关的用户总数
     */
    int getTotalRow(int status, String username);
}
