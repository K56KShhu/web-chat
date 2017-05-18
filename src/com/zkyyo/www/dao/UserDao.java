package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.UserPo;

import java.util.List;
import java.util.Set;

public interface UserDao {
    void addUser(UserPo userPo);

    UserPo selectUserByUserId(int id);

    UserPo selectUserByUsername(String username);

    List<UserPo> selectUsersByUsername(int status, int startIndex, int rowsOnePage, String username);

    List<UserPo> selectUsersByOrder(int status, int startIndex, int rowsOnePage, int order, boolean isReverse);

    List<UserPo> selectUsersByGroup(int groupId);

    Set<String> selectRolesByUserId(int id);

    Set<String> selectRolesByUsername(String username);

    Set<Integer> selectGroupsByUserId(int id);

    Set<Integer> selectGroupsByUsername(String username);

    void update(UserPo userPo, List<Integer> updatedTypes);

    int getTotalRow(String username);

    int getTotalRowByStatus(int status);

    int getTotalRow();

    int getTotalRow(int status, String username);
}
