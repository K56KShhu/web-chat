package com.zkyyo.www.service;

import com.zkyyo.www.dao.UserDao;
import com.zkyyo.www.dao.impl.UserDaoJdbcImpl;
import com.zkyyo.www.po.UserPo;
import com.zkyyo.www.web.Access;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserService {
    public static final int STATUS_NOT_APPROVED = -1;
    public static final int STATUS_AUDIT = 0;
    public static final int STATUS_APPROVED = 1;
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

    public Set<Integer> getGroups(int userId) {
        return userDao.selectGroupsByUserId(userId);
    }

    public Set<Integer> getGroups(String username) {
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
        Set<Integer> groups = getGroups(userId);
        return new Access(userId, user.getUsername(), user.getStatus(), roles, groups);
    }

    public Access getAccess(String username) {
        UserPo user = userDao.selectUserByUsername(username);
        Set<String> roles = getRoles(username);
        Set<Integer> groups = getGroups(username);
        return new Access(user.getUserId(), username, user.getStatus(), roles, groups);
    }

    //用户修改信息
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

    public List<UserPo> getUsersByStatus(int status) {
        List<UserPo> users = new ArrayList<>();
        if (status == STATUS_AUDIT) {
            users = userDao.selectUsersByStatus(UserDaoJdbcImpl.STATUS_AUDIT);
        } else if (status == STATUS_APPROVED) {
            users = userDao.selectUsersByStatus(UserDaoJdbcImpl.STATUS_APPROVED);
        } else if (status == STATUS_NOT_APPROVED) {
            users = userDao.selectUsersByStatus(UserDaoJdbcImpl.STATUS_NOT_APPROVED);
        }
        return users; //用户输入不存在的status时会返回size为0的列表, 而不是null
    }

    public void updateStatus(int userId, int status) {
        UserPo user = new UserPo();
        user.setUserId(userId);
        user.setStatus(status);
        List<Integer> updateTypes = new ArrayList<>();
        updateTypes.add(UserDaoJdbcImpl.UPDATE_STATUS);
        userDao.update(user, updateTypes);
    }

    public boolean isUserExisted(int userId) {
        return true;
    }

    public boolean isUserExisted(String username) {
        return true;
    }

    public boolean isValidUserId(String userId) {
        return true;
    }

    public boolean isValidUsername(String username) {
        return true;
    }

    public boolean isValidPassword(String psw, String cpsw) {
        return true;
    }

    public boolean isValidPassword(String pwd) {
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

