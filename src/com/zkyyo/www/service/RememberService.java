package com.zkyyo.www.service;

import com.zkyyo.www.dao.RememberDao;

/**
 * 提供与Cookie自动登录相关, 用于逻辑处理的代码
 * 该类包含Cookie信息的增删查方法, 不包含修改方法
 */
public class RememberService {
    /**
     * 数据库操作相关接口
     */
    private RememberDao rememberDao;

    /**
     * 构造函数
     *
     * @param rememberDao 传入的数据库操作接口
     */
    public RememberService(RememberDao rememberDao) {
        this.rememberDao = rememberDao;
    }

    /**
     * 储存Cookie的Value和生成该Cookie的用户名
     *
     * @param uuid     Cookie Value
     * @param username Cookie拥有者用户名
     */
    public void save(String uuid, String username) {
        rememberDao.addRemember(uuid, username);
    }

    /**
     * 删除指定用户名的所有Cookie
     *
     * @param username 指定用户名
     */
    public void delete(String username) {
        rememberDao.delete(username);
    }

    /**
     * 通过Cookie的Vallue查询生成该Cookie的用户名
     *
     * @param uuid Cookie Value
     * @return 存在返回用户名, 不存在返回null
     */
    public String find(String uuid) {
        return rememberDao.selectUsername(uuid);
    }

}
