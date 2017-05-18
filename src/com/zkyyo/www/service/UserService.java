package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.dao.UserDao;
import com.zkyyo.www.dao.impl.UserDaoJdbcImpl;
import com.zkyyo.www.util.CheckUtil;
import com.zkyyo.www.util.Pbkdf2Util;
import com.zkyyo.www.web.Access;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_AUDIT = 0;
    public static final int STATUS_NOT_APPROVED = -1;
    public static final int STATUS_FORBIDDEN = -2;

    public static final int ORDER_BY_SEX = 0;
    public static final int ORDER_BY_CREATED = 1;
    public static final int ORDER_BY_STATUS = 2;

    private static final int ROWS_ONE_PAGE = 15;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_USERNAME_LENGTH = 16;
    private static final int MIN_USERNAME_LENGTH = 8;

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean checkLogin(UserPo checkUser) {
        if (checkUser.getUsername() != null && checkUser.getPassword() != null) {
            UserPo user = getUser(checkUser.getUsername());
            if (user != null) {
                String correctPwd = user.getPassword();
                try {
                    return Pbkdf2Util.validatePassword(checkUser.getPassword(), correctPwd);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean isValidUserId(String userId) {
        return CheckUtil.isValidId(userId, MAX_ID_LENGTH);
    }

    public boolean isValidUsername(String username) {
        return CheckUtil.isValidString(username, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
    }

    public boolean isValidPassword(String psw, String cpsw) {
        if (psw == null || cpsw == null) {
            return false;
        }
        if (psw.length() >= 8 && psw.length() <= 16) {
            if (psw.equals(cpsw)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[\\w.+-]+@[\\w.+-]+\\.[\\w.+-]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidSex(String sex) {
        return "male".equals(sex)
                || "female".equals(sex)
                || "secret".equals(sex);
    }

    public boolean isValidStatus(String status) {
        return Integer.toString(STATUS_NORMAL).equals(status)
                || Integer.toString(STATUS_AUDIT).equals(status)
                || Integer.toString(STATUS_NOT_APPROVED).equals(status)
                || Integer.toString(STATUS_FORBIDDEN).equals(status);
    }

    public boolean isUserExisted(int userId) {
        return getUser(userId) != null;
    }

    public boolean isUserExisted(String username) {
        return getUser(username) != null;
    }

    public void addUser(UserPo userPo) {
        String originalPwd = userPo.getPassword();
        try {
            String hashPwd = Pbkdf2Util.createHash(originalPwd);
            userPo.setPassword(hashPwd);
            userDao.addUser(userPo);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public UserPo getUser(int userId) {
        return userDao.selectUserByUserId(userId);
    }

    public UserPo getUser(String username) {
        return userDao.selectUserByUsername(username);
    }

    public PageBean<UserPo> queryUsers(String username, int currentPage) {
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

    public List<UserPo> queryUsersByStatus(int status) {
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

    public PageBean<UserPo> queryUsersByStatus(int currentPage, int status) {
        PageBean<UserPo> pageBean = new PageBean<>(currentPage, userDao.getTotalRowByStatus(status), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        List<UserPo> users;
        if (STATUS_AUDIT == status) {
            users = userDao.selectUsersByStatus(startIndex, ROWS_ONE_PAGE, UserDaoJdbcImpl.STATUS_AUDIT);
        } else if (STATUS_NORMAL == status) {
            users = userDao.selectUsersByStatus(startIndex, ROWS_ONE_PAGE, UserDaoJdbcImpl.STATUS_NORMAL);
        } else if (STATUS_NOT_APPROVED == status) {
            users = userDao.selectUsersByStatus(startIndex, ROWS_ONE_PAGE, UserDaoJdbcImpl.STATUS_NOT_APPROVED);
        } else {
            return null;
        }
        pageBean.setList(users);
        return pageBean;
    }

    public List<UserPo> queryUsersByGroup(int groupId) {
        return userDao.selectUsersByGroup(groupId);
    }

    public boolean isUserInGroup(int groupId, int userId) {
        Set<Integer> groups = getGroups(userId);
        for (int group : groups) {
            if (groupId == group) {
                return true;
            }
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
        try {
            UserPo initialUser = userDao.selectUserByUserId(userPo.getUserId());
            List<Integer> updatedTypes = new ArrayList<>();

            if (userPo.getSex() != null && !userPo.getSex().equals(initialUser.getSex())) {
                updatedTypes.add(UserDaoJdbcImpl.UPDATE_SEX);
            }
            if (userPo.getEmail() != null && !userPo.getEmail().equals(initialUser.getEmail())) {
                updatedTypes.add(UserDaoJdbcImpl.UPDATE_EMAIL);
            }
            if (userPo.getPassword() != null) {
                String hashPwd = Pbkdf2Util.createHash(userPo.getPassword());
                userPo.setPassword(hashPwd);
                updatedTypes.add(UserDaoJdbcImpl.UPDATE_PASSWORD);
            }
            userDao.update(userPo, updatedTypes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
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

    /*
    public List<UserPo> fuzzySearchUsers(String search) {
        return userDao.selectPossibleUsersByUsername(search.trim());
    }
    */
}

