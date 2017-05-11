package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.dao.UserDao;
import com.zkyyo.www.dao.impl.UserDaoJdbcImpl;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.web.Access;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserService {
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_AUDIT = 0;
    public static final int STATUS_NOT_APPROVED = -1;
    public static final int STATUS_FORBIDDEN = -2;

    public static final int ORDER_BY_SEX = 0;
    public static final int ORDER_BY_CREATED = 1;
    public static final int ORDER_BY_STATUS = 2;

    private static final int ROWS_ONE_PAGE = 15;

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
        if (STATUS_AUDIT == status) {
            users = userDao.selectUsersByStatus(UserDaoJdbcImpl.STATUS_AUDIT);
        } else if (STATUS_NORMAL == status) {
            users = userDao.selectUsersByStatus(UserDaoJdbcImpl.STATUS_NORMAL);
        } else if (STATUS_NOT_APPROVED == status) {
            users = userDao.selectUsersByStatus(UserDaoJdbcImpl.STATUS_NOT_APPROVED);
        }
        return users; //用户输入不存在的status时会返回size为0的列表, 而不是null
    }

    public void updateStatus(int userId, int status) {
        UserPo user = new UserPo();
        user.setUserId(userId);

        if (STATUS_NORMAL == status) {
            user.setStatus(UserDaoJdbcImpl.STATUS_NORMAL);
        } else if (STATUS_AUDIT == status) {
            user.setStatus(UserDaoJdbcImpl.STATUS_AUDIT);
        } else if (STATUS_NOT_APPROVED == status) {
            user.setStatus(UserDaoJdbcImpl.STATUS_NOT_APPROVED);
        } else if (STATUS_FORBIDDEN == status) {
            user.setStatus(UserDaoJdbcImpl.STATUS_FORBIDDEN);
        } else {
            return;
        }
        List<Integer> updateTypes = new ArrayList<>();
        updateTypes.add(UserDaoJdbcImpl.UPDATE_STATUS);
        userDao.update(user, updateTypes);
    }

    public PageBean<UserPo> queryUsers(int currentPage, String username) {
        PageBean<UserPo> pageBean = new PageBean<>(currentPage, userDao.getTotalRow(username), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        List<UserPo> users = userDao.selectUsersByUsername(startIndex, ROWS_ONE_PAGE, username);
        pageBean.setList(users);
        return pageBean;
    }

    public PageBean<UserPo> queryUsers(int currentPage, int order, boolean isReverse) {
        PageBean<UserPo> pageBean = new PageBean<>(currentPage, userDao.getTotalRow(), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        List<UserPo> users;
        if (ORDER_BY_SEX == order) {
            users = userDao.selectUsers(startIndex, ROWS_ONE_PAGE, UserDaoJdbcImpl.ORDER_BY_SEX, isReverse);
        } else if (ORDER_BY_CREATED == order) {
            users = userDao.selectUsers(startIndex, ROWS_ONE_PAGE, UserDaoJdbcImpl.ORDER_BY_CREATED, isReverse);
        } else if (ORDER_BY_STATUS == order) {
            users = userDao.selectUsers(startIndex, ROWS_ONE_PAGE, UserDaoJdbcImpl.ORDER_BY_STATUS, isReverse);
        } else {
            return null;
        }
        pageBean.setList(users);
        return pageBean;
    }

    public List<UserPo> getUsers() {
        return userDao.selectUsers();
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

    public boolean isValidStatus(String status) {
        return true;
    }

    public void addUser(UserPo userPo) {
//        for (int i = 0; i < 200; i++) {
//            if (Math.random() > 0.5) {
//                userPo.setSex("male");
//            } else {
//                userPo.setSex("female");
//            }
//            userPo.setUserId(1234 + i);
//            userDao.addUser(userPo);
//            System.out.println("done");
//        }
        userDao.addUser(userPo);
    }

    public List<UserPo> fuzzySearchUsers(String search) {
        return userDao.selectPossibleUsersByUsername(search.trim());
    }

}

