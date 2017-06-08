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
    /**
     * 用户状态标识, 表示所有状态
     */
    public static final int STATUS_ALL = 2;
    /**
     * 用户状态标识, 表示正常
     */
    public static final int STATUS_NORMAL = 1;
    /**
     * 用户状态标识, 表示审核中
     */
    public static final int STATUS_AUDIT = 0;
    /**
     * 用户状态标识, 表示审核不通过
     */
    public static final int STATUS_NOT_APPROVED = -1;
    /**
     * 用户状态标识, 表示被封印
     */
    public static final int STATUS_FORBIDDEN = -2;

    /**
     * 排序依据标识, 排序依据为性别
     */
    public static final int ORDER_BY_SEX = 0;
    /**
     * 排序依据标识, 排序依据为注册时间
     */
    public static final int ORDER_BY_CREATED = 1;
    /**
     * 排序依据标识, 排序依据为用户状态
     */
    public static final int ORDER_BY_STATUS = 2;

    /**
     * 分页系统, 每一页的行数
     */
    private static final int ROWS_ONE_PAGE = 15;

    /**
     * 用户唯一标识ID的最大长度
     */
    private static final int MAX_ID_LENGTH = 10;
    /**
     * 用户名的最大长度
     */
    private static final int MAX_USERNAME_LENGTH = 16;
    /**
     * 用户名的最小长度
     */
    private static final int MIN_USERNAME_LENGTH = 3;
    /**
     * 密码的最大长度
     */
    private static final int MAX_PASSWORD_LENGTH = 16;
    /**
     * 密码的最小长度
     */
    private static final int MIN_PASSWORD_LENGTH = 3;

    /**
     * 数据库操作相关接口
     */
    private UserDao userDao;

    /**
     * 构建对象
     *
     * @param userDao 传入的数据库操作接口
     */
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 检查登录
     *
     * @param checkUser 待检查的用户对象
     * @return true检查通过, false检查不通过
     */
    public boolean checkLogin(UserPo checkUser) {
        //检查用户是否有进行输入
        if (checkUser.getUsername() != null && checkUser.getPassword() != null) {
            UserPo user = findUser(checkUser.getUsername());
            //检查用户是否存在
            if (user != null) {
                String correctPwd = user.getPassword();
                try {
                    //验证密码
                    return Pbkdf2Util.validatePassword(checkUser.getPassword(), correctPwd);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 校验用户ID是否合法
     *
     * @param userId 待校验的用户ID
     * @return true合法, false不合法
     */
    public boolean isValidUserId(String userId) {
        return CheckUtil.isValidInteger(CheckUtil.NUMBER_POSITIVE, userId, MAX_ID_LENGTH);
    }

    /**
     * 校验用户名是否合法
     *
     * @param username 待校验的用户名
     * @return true合法, false不合法
     */
    public boolean isValidUsername(String username) {
        return CheckUtil.isValidString(username, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
    }

    /**
     * 校验密码是否合法
     *
     * @param pwd 待校验的密码
     * @return true合法, false不合法
     */
    public boolean isValidPassword(String pwd) {
        return CheckUtil.isValidString(pwd, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
    }

    /**
     * 校验两次输入的密码是否相同
     *
     * @param pwd  第一次输入的密码
     * @param cpwd 第二次输入的密码
     * @return true相同, false不相同
     */
    public boolean isSamePassword(String pwd, String cpwd) {
        return isValidPassword(pwd) && pwd.equals(cpwd);
    }

    /**
     * 校验邮箱是否合法
     *
     * @param email 待校验的邮箱
     * @return true合法, false不合法
     */
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[\\w.+-]+@[\\w.+-]+\\.[\\w.+-]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 校验性别是否合法
     *
     * @param sex 待校验的性别
     * @return true合法, false不合法
     */
    public boolean isValidSex(String sex) {
        return "male".equals(sex)
                || "female".equals(sex)
                || "secret".equals(sex);
    }

    /**
     * 校验状态是否合法
     *
     * @param status 待校验的状态
     * @return true合法, false不合法
     */
    public boolean isValidStatus(String status) {
        return Integer.toString(STATUS_NORMAL).equals(status)
                || Integer.toString(STATUS_AUDIT).equals(status)
                || Integer.toString(STATUS_NOT_APPROVED).equals(status)
                || Integer.toString(STATUS_FORBIDDEN).equals(status);
    }

    /**
     * 通过用户ID, 校验用户是否存在
     *
     * @param userId 待校验的用户ID
     * @return true存在, false不存在
     */
    public boolean isUserExisted(int userId) {
        return findUser(userId) != null;
    }

    /**
     * 通过用户名, 校验用户是否存在
     *
     * @param username 待校验的用户名
     * @return true存在, false不存在
     */
    public boolean isUserExisted(String username) {
        return findUser(username) != null;
    }

    /**
     * 校验用户是否拥有指定角色
     *
     * @param userId 用户ID
     * @param role   指定角色
     * @return true拥有, false不拥有
     */
    public boolean isUserInRole(int userId, String role) {
        Set<String> roles = getRoles(userId);
        for (String r : roles) {
            if (role.equals(r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验用户是否位于指定小组中
     *
     * @param userId  用户ID
     * @param groupId 指定小组ID
     * @return true位于, false不位于
     */
    public boolean isUserInGroup(int userId, int groupId) {
        Set<Integer> groups = getGroups(userId);
        for (int group : groups) {
            if (groupId == group) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加用户信息
     *
     * @param userPo 待添加的用户对象
     */
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

    /**
     * 通过用户ID, 精确查询用户信息
     *
     * @param userId 待查询的用户ID
     * @return 存在返回用户对象, 不存在返回null
     */
    public UserPo findUser(int userId) {
        return userDao.selectUserByUserId(userId);
    }

    /**
     * 通过用户名, 精确查询用户信息
     *
     * @param username 待查询的用户名
     * @return 存在返回用户对象, 不存在返回null
     */
    public UserPo findUser(String username) {
        return userDao.selectUserByUsername(username);
    }

    /**
     * 通过用户名, 模糊查询指定状态的用户, 同时进行分页
     *
     * @param status      用户状态
     * @param currentPage 当前页数
     * @param username    可能的用户名
     * @return 封装的分页对象, 信息输入不合法时返回null
     */
    public PageBean<UserPo> queryUsers(int status, int currentPage, String username) {
        int statusType;
        //判断查询状态
        if (STATUS_ALL == status) {
            statusType = UserDaoJdbcImpl.STATUS_ALL;
        } else if (STATUS_NORMAL == status) {
            statusType = UserDaoJdbcImpl.STATUS_NORMAL;
        } else if (STATUS_AUDIT == status) {
            statusType = UserDaoJdbcImpl.STATUS_AUDIT;
        } else if (STATUS_NOT_APPROVED == status) {
            statusType = UserDaoJdbcImpl.STATUS_NOT_APPROVED;
        } else if (STATUS_FORBIDDEN == status) {
            statusType = UserDaoJdbcImpl.STATUS_FORBIDDEN;
        } else {
            return null;
        }

        //设置分页对象
        PageBean<UserPo> pageBean = new PageBean<>(currentPage, userDao.getTotalRow(statusType, username), ROWS_ONE_PAGE);
        //计算起始下标
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        List<UserPo> users = userDao.selectUsersByUsername(statusType, startIndex, ROWS_ONE_PAGE, username);
        pageBean.setList(users);
        return pageBean;
    }

    /**
     * 查询指定状态下的所有用户, 同时进行分页和排序
     *
     * @param status      用户状态
     * @param currentPage 当前页数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 封装的分页对象, 信息输入不合法时返回null
     */
    public PageBean<UserPo> queryUsers(int status, int currentPage, int order, boolean isReverse) {
        int statusType;
        //判断查询状态
        if (STATUS_ALL == status) {
            statusType = UserDaoJdbcImpl.STATUS_ALL;
        } else if (STATUS_NORMAL == status) {
            statusType = UserDaoJdbcImpl.STATUS_NORMAL;
        } else if (STATUS_AUDIT == status) {
            statusType = UserDaoJdbcImpl.STATUS_AUDIT;
        } else if (STATUS_NOT_APPROVED == status) {
            statusType = UserDaoJdbcImpl.STATUS_NOT_APPROVED;
        } else if (STATUS_FORBIDDEN == status) {
            statusType = UserDaoJdbcImpl.STATUS_FORBIDDEN;
        } else {
            return null;
        }
        int orderType;
        //判断排序依据
        if (ORDER_BY_SEX == order) {
            orderType = UserDaoJdbcImpl.ORDER_BY_SEX;
        } else if (ORDER_BY_CREATED == order) {
            orderType = UserDaoJdbcImpl.ORDER_BY_CREATED;
        } else if (ORDER_BY_STATUS == order) {
            orderType = UserDaoJdbcImpl.ORDER_BY_STATUS;
        } else {
            return null;
        }

        //设置分页对象
        PageBean<UserPo> pageBean = new PageBean<>(currentPage, userDao.getTotalRow(statusType), ROWS_ONE_PAGE);
        //计算起始下标
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;
        List<UserPo> users = userDao.selectUsersByOrder(statusType, startIndex, ROWS_ONE_PAGE, orderType, isReverse);
        pageBean.setList(users);
        return pageBean;
    }

    /**
     * 查询拥有指定角色的所有用户
     *
     * @param role 指定的角色
     * @return 返回一个size可为0的用户列表
     */
    public List<UserPo> queryUsersByRole(String role) {
        return userDao.selectUsersByRole(role);
    }

    /**
     * 查询指定小组下的所有用户
     *
     * @param groupId 指定的小组ID
     * @return 返回一个size可为0的用户列表
     */
    public List<UserPo> queryUsersByGroup(int groupId) {
        return userDao.selectUsersByGroup(groupId);
    }

    /**
     * 更新用户信息
     *
     * @param userPo 包含最新信息的用户对象
     */
    public void update(UserPo userPo) {
        try {
            UserPo initialUser = userDao.selectUserByUserId(userPo.getUserId());
            List<Integer> updatedTypes = new ArrayList<>();

            //判断需要修改的类型
            if (userPo.getSex() != null && !userPo.getSex().equals(initialUser.getSex())) {
                //修改性别
                updatedTypes.add(UserDaoJdbcImpl.UPDATE_SEX);
            }
            if (userPo.getEmail() != null && !userPo.getEmail().equals(initialUser.getEmail())) {
                //修改邮箱
                updatedTypes.add(UserDaoJdbcImpl.UPDATE_EMAIL);
            }
            if (userPo.getPassword() != null) {
                //修改密码
                //加密
                String hashPwd = Pbkdf2Util.createHash(userPo.getPassword());
                userPo.setPassword(hashPwd);
                updatedTypes.add(UserDaoJdbcImpl.UPDATE_PASSWORD);
            }
            userDao.update(userPo, updatedTypes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 最新状态标识符
     */
    public void updateStatus(int userId, int status) {
        UserPo user = new UserPo();
        user.setUserId(userId);

        //判断修改状态
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

    /**
     * 校验root密码后可提升用户为管理员
     *
     * @param rootId  root用户ID
     * @param rootPwd root密码
     * @param userId  待提升的用户ID
     * @return true提升成功, false密码错误
     */
    public boolean addAdmin(int rootId, String rootPwd, int userId) {
        UserPo root = findUser(rootId);
        try {
            //验证root密码
            boolean isRoot = Pbkdf2Util.validatePassword(rootPwd, root.getPassword());
            if (isRoot) {
                userDao.addRoleInUser(userId, UserDaoJdbcImpl.ROLE_ADMIN);
                return true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移除用户指定角色
     *
     * @param userId 用户ID
     * @param role   待移除的角色
     */
    public void removeRoleInUser(int userId, String role) {
        userDao.deleteRoleInUser(userId, role);
    }

    /**
     * 通过用户ID, 获取指定用户的所有角色
     *
     * @param userId 指定用户ID
     * @return 一个size可为0的角色集合
     */
    public Set<String> getRoles(int userId) {
        return userDao.selectRolesByUserId(userId);
    }

    /**
     * 通过用户名, 获取指定用户的所有角色
     *
     * @param username 指定用户名
     * @return 一个size可为0的角色集合
     */
    public Set<String> getRoles(String username) {
        return userDao.selectRolesByUsername(username);
    }

    /**
     * 通过用户ID, 获取指定用户所在的所有小组
     *
     * @param userId 指定用户ID
     * @return 一个size可为0的小组ID集合
     */
    public Set<Integer> getGroups(int userId) {
        return userDao.selectGroupsByUserId(userId);
    }

    /**
     * 通过用户名, 获取指定用户所在的所有小组
     *
     * @param username 指定用户名
     * @return 一个size可为0的小组ID集合
     */
    public Set<Integer> getGroups(String username) {
        return userDao.selectGroupsByUsername(username);
    }

    /**
     * 通过用户ID, 获取指定用户的所有权限, 包括角色和小组
     *
     * @param userId 指定用户ID
     * @return 封装了用户权限的对象
     */
    public Access getAccess(int userId) {
        UserPo user = userDao.selectUserByUserId(userId);
        Set<String> roles = getRoles(userId);
        Set<Integer> groups = getGroups(userId);
        return new Access(userId, user.getUsername(), user.getStatus(), roles, groups);
    }

    /**
     * 通过用户名, 获取指定用户的所有权限, 包括角色和小组
     *
     * @param username 指定用户名
     * @return 封装了用户权限的对象
     */
    public Access getAccess(String username) {
        UserPo user = userDao.selectUserByUsername(username);
        Set<String> roles = getRoles(username);
        Set<Integer> groups = getGroups(username);
        return new Access(user.getUserId(), username, user.getStatus(), roles, groups);
    }
}

