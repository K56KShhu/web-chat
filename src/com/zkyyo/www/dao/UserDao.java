package com.zkyyo.www.dao;

import com.zkyyo.www.po.UserPo;

import java.util.List;
import java.util.Set;

public interface UserDao {
    UserPo selectUser(UserPo userPo);
    Set<String> selectRoles(UserPo userPo);
    Set<String> selectGroups(UserPo userPo);
    void addUser(UserPo userPo);
    void update(UserPo userPo, List<Integer> updatedTypes);
}
