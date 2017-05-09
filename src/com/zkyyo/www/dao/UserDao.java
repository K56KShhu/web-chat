package com.zkyyo.www.dao;

import com.zkyyo.www.po.UserPo;

import java.util.List;
import java.util.Set;

public interface UserDao {
    UserPo selectUserByUserId(int id);
    UserPo selectUserByUsername(String username);
    List<UserPo> selectUsers();
    List<UserPo> selectUsersByStatus(int status);
    Set<String> selectRolesByUserId(int id);
    Set<String> selectRolesByUsername(String username);
    Set<Integer> selectGroupsByUserId(int id);
    Set<Integer> selectGroupsByUsername(String username);
    void addUser(UserPo userPo);
    void update(UserPo userPo, List<Integer> updatedTypes);
}
