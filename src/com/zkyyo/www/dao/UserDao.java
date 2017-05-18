package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.UserPo;

import java.util.List;
import java.util.Set;

public interface UserDao {
    UserPo selectUserByUserId(int id);

    UserPo selectUserByUsername(String username);

    List<UserPo> selectUsers();

    List<UserPo> selectUsersByStatus(int status);

    List<UserPo> selectUsersByStatus(int startIndex, int rowsOnePage, int status);

    List<UserPo> selectPossibleUsersByUsername(String username);

    List<UserPo> selectUsersByUsername(int startIndex, int rowsOnePage, String username);

    List<UserPo> selectUsersByUsername(int status, int startIndex, int rowsOnePage, String username);

    List<UserPo> selectUsers(int startIndex, int rowsOnePage, int order, boolean isReverse);

    List<UserPo> selectUsers(int status, int startIndex, int rowsOnePage, int order, boolean isReverse);

    List<UserPo> selectUsersByGroup(int groupId);

    Set<String> selectRolesByUserId(int id);

    Set<String> selectRolesByUsername(String username);

    Set<Integer> selectGroupsByUserId(int id);

    Set<Integer> selectGroupsByUsername(String username);

    int getTotalRow(String username);

    int getTotalRowByStatus(int status);

    int getTotalRow();

    int getTotalRow(int status, String username);

    void addUser(UserPo userPo);

    void update(UserPo userPo, List<Integer> updatedTypes);
}
