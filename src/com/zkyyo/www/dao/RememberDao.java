package com.zkyyo.www.dao;

/**
 * 定义与登录Cookie相关, 涉及数据库操作的接口
 */
public interface RememberDao {
    /**
     * 向数据库中插入Cookie信息
     *
     * @param uuid     待插入的Cookie Value
     * @param username 关联此Cookie的用户名
     */
    void addRemember(String uuid, String username);

    /**
     * 删除数据库中指定用户名的Cookie信息
     *
     * @param username 待删除的用户名
     */
    void delete(String username);

    /**
     * 获取数据库中指定Cookie Value的用户名
     *
     * @param uuid 指定的Cookie Value
     * @return 存在则返回用户名, 否则返回null;
     */
    String selectUsername(String uuid);

}
