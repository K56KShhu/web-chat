package com.zkyyo.www.service;

import com.zkyyo.www.dao.UserDao;
import com.zkyyo.www.dao.UserDaoJdbcImpl;
import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.web.Access;

import java.util.*;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean checkLogin(UserPo userPo) {
        if (userPo.getUsername() != null && userPo.getPassword() != null) {
            UserPo user = getUser(userPo.getUsername());
            return user != null && user.getPassword().equals(userPo.getPassword());
        }
        return false;
    }

    public Set<String> getRoles(int userId) {
        return userDao.selectRolesByUserId(userId);
    }

    public Set<String> getRoles(String username) {
        return userDao.selectRolesByUsername(username);
    }

    public Set<String> getGroups(int userId) {
        return userDao.selectGroupsByUserId(userId);
    }

    public Set<String> getGroups(String username) {
        return userDao.selectGroupsByUsername(username);
    }

    public UserPo getUser(int userId) {
        return userDao.selectUserByUserId(userId);
    }

    public UserPo getUser(String username) {
        return userDao.selectUserByUsername(username);
    }

    public Access getAccess(int userId) {
        UserPo user = userDao.selectUserByUserId(userId);
        Set<String> roles = getRoles(userId);
        Set<String> groups = new HashSet<>(); //待完善
        return new Access(userId, user.getUsername(), roles, groups);
    }

    public Access getAccess(String username) {
        UserPo user = userDao.selectUserByUsername(username);
        Set<String> roles = getRoles(username);
        Set<String> groups = new HashSet<>(); //待完善
        return new Access(user.getUserId(), username, roles, groups);
    }

    public void update(UserPo userPo) {
        UserPo initialUser = userDao.selectUserByUserId(userPo.getUserId());
        List<Integer> updatedTypes = new ArrayList<>();

        if (userPo.getSex() != null && !userPo.getSex().equals(initialUser.getSex())) {
            updatedTypes.add(UserDaoJdbcImpl.UPDATE_SEX);
        }
        if (userPo.getEmail() != null && !userPo.getEmail().equals(initialUser.getEmail())) {
            updatedTypes.add(UserDaoJdbcImpl.UPDATE_EMAIL);
        }
        if (userPo.getPassword() != null && !userPo.getPassword().equals(initialUser.getPassword())) {
            updatedTypes.add(UserDaoJdbcImpl.UPDATE_PASSWORD);
        }
        userDao.update(userPo, updatedTypes);
    }

    public boolean isValidUserName(String username) {
        return true;
    }

    public boolean isValidPassword(String psw, String cpsw) {
        return true;
    }

    public boolean isValidEmail(String email) {
        return true;
    }

    public boolean isValidSex(String sex) {
        return true;
    }

    public void addUser(UserPo userPo) {
        userDao.addUser(userPo);
    }
}

