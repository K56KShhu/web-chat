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
            UserPo user = userDao.selectUser(userPo);
            return user != null && user.getPassword().equals(userPo.getPassword());
        }
        return false;
    }

    public Set<String> getRoles(UserPo userPo) {
        return userDao.selectRoles(userPo);
    }

    public UserPo getUser(UserPo userPo) {
        return userDao.selectUser(userPo);
    }

    public UserPo getUser(String username) {
        UserPo userPo = new UserPo();
        userPo.setUsername(username);
        return this.getUser(userPo);
    }

    /*
    public Map<String, Set<String>> getAccess(UserPo userPo) {
        UserPo user = getUser(userPo);
        Set<String> roles = getRoles(userPo);
        Map<String, Set<String>> access = new HashMap<>();

        if (user != null && roles != null) {
            Set<String> username = new HashSet<>();
            username.add(user.getUsername());
            access.put("username", username);
            access.put("roles", roles);
        }
        return access;
    }
    */

    public Access getAccess(UserPo userPo) {
        String username = userPo.getUsername();
        Set<String> roles = getRoles(userPo);
        //待完善
        Set<String> groups = new HashSet<>();

        return new Access(username, roles, groups);
    }

    public void update(UserPo userPo) {
        UserPo initialUser = userDao.selectUser(userPo);
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

    public Access getAccess(String username) {
        UserPo userPo = new UserPo();
        userPo.setUsername(username);;
        return this.getAccess(userPo);
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

