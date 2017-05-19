package com.zkyyo.www.dao.impl;

import com.zkyyo.www.bean.po.UserPo;
import com.zkyyo.www.dao.UserDao;
import com.zkyyo.www.db.DbClose;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通过JDBC实现UserDap接口
 */
public class UserDaoJdbcImpl implements UserDao {
    /**
     * 更新标识符, 表示更新性别
     */
    public static final int UPDATE_SEX = 1;
    /**
     * 更细标识符, 表示更新邮箱
     */
    public static final int UPDATE_EMAIL = 2;
    /**
     * 更细标识符, 表示更新密码
     */
    public static final int UPDATE_PASSWORD = 3;
    /**
     * 更细标识符, 表示更新状态
     */
    public static final int UPDATE_STATUS = 4;

    /**
     * 状态标识符, 表示所有状态
     */
    public static final int STATUS_ALL = 2;
    /**
     * 状态标识符, 表示正常状态
     */
    public static final int STATUS_NORMAL = 1;
    /**
     * 状态标识符, 表示审核中状态
     */
    public static final int STATUS_AUDIT = 0;
    /**
     * 状态标识符, 表示审核不通过状态
     */
    public static final int STATUS_NOT_APPROVED = -1;
    /**
     * 状态标识符, 表示被封印状态
     */
    public static final int STATUS_FORBIDDEN = -2;

    /**
     * 排序依据标识符, 表示通过性别排序
     */
    public static final int ORDER_BY_SEX = 0;
    /**
     * 排序依据标识符, 表示通过注册时间排序
     */
    public static final int ORDER_BY_CREATED = 1;
    /**
     * 排序依据标识符, 表示通过状态排序
     */
    public static final int ORDER_BY_STATUS = 2;

    /**
     * 角色权限, 表示"user"权限
     */
    public static final String ROLE_USER = "user";
    /**
     * 角色权限, 表示"admin"权限
     */
    public static final String ROLE_ADMIN = "admin";

    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    /**
     * 构建对象
     *
     * @param dataSource 传入的数据库连接池
     */
    public UserDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库中指定用户ID的用户信息
     *
     * @param id 待获取的用户ID
     * @return 存在返回用户对象, 否则返回null
     */
    @Override
    public UserPo selectUserByUserId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Integer.toString(id));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return getUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 获取数据库中指定用户名的用户信息
     *
     * @param username 待获取的用户名
     * @return 存在返回用户对象, 否则返回null
     */
    @Override
    public UserPo selectUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return getUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 获取数据库中指定用户名的用户信息
     *
     * @param username 待获取的用户名
     * @return 存在返回用户对象, 否则返回null
     */
    @Override
    public List<UserPo> selectUsersByUsername(int status, int startIndex, int rowsOnePage, String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserPo> users = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            //SELECT * FROM user WHERE username LIKE ? AND status = status ORDER BY created DESC LIMIT ?, ?
            String sql = makeQuerySql(true, status, ORDER_BY_CREATED, true);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + username + "%");
            pstmt.setInt(2, startIndex);
            pstmt.setInt(3, rowsOnePage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(getUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return users;
    }

    /**
     * 获取数据库中的多个用户信息, 同时进行分页和排序
     *
     * @param status      用户状态
     * @param startIndex  起始下标
     * @param rowsOnePage 获取总数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    @Override
    public List<UserPo> selectUsersByOrder(int status, int startIndex, int rowsOnePage, int order, boolean isReverse) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserPo> users = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            //SELECT * FROM user WHERE status = status ORDER BY order LIMIT ?, ?;
            String sql = makeQuerySql(false, status, order, isReverse);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startIndex);
            pstmt.setInt(2, rowsOnePage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(getUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return users;
    }

    /**
     * 获取数据库中与指定角色关联的多个用户信息
     *
     * @param role 指定角色
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    @Override
    public List<UserPo> selectUsersByRole(String role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserPo> users = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE user_id IN (SELECT user_id FROM user_role WHERE role=?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(getUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return users;
    }

    /**
     * 获取数据库中与指定小组关联的多个用户信息
     *
     * @param groupId 指定小组ID
     * @return 包含多个用户信息的列表, 不包含任何用户则返回size为0的列表
     */
    @Override
    public List<UserPo> selectUsersByGroup(int groupId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserPo> users = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE user_id IN (SELECT user_id FROM user_usergroup WHERE usergroup_id=?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(getUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return users;
    }

    /**
     * 获取数据库中指定用户ID的角色
     *
     * @param id 指定用户ID
     * @return 包含多个角色的集合, 不包含任何角色则返回size为0的集合
     */
    @Override
    public Set<String> selectRolesByUserId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<String> roles = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT role FROM user_role WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Integer.toString(id));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return roles;
    }

    /**
     * 获取数据库中指定用户名的角色
     *
     * @param username 指定用户名
     * @return 包含多个角色的集合, 不包含任何角色则返回size为0的集合
     */
    @Override
    public Set<String> selectRolesByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<String> roles = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT role FROM user_role WHERE user_id = (SELECT user_id FROM user WHERE username = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return roles;
    }

    /**
     * 获取数据库中与指定用户关联的多个小组信息
     *
     * @param id 指定用户ID
     * @return 包含多个小组ID的集合, 不包含任何小组则返回size为0的集合
     */
    @Override
    public Set<Integer> selectGroupsByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<Integer> groups = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT usergroup_id FROM user_usergroup WHERE user_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                groups.add(rs.getInt("usergroup_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return groups;
    }

    /**
     * 获取数据库中与指定用户关联的多个小组信息
     *
     * @param username 指定用户名
     * @return 包含多个小组ID的集合, 不包含任何小组则返回size为0的集合
     */
    @Override
    public Set<Integer> selectGroupsByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<Integer> groups = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT usergroup_id FROM user_usergroup WHERE user_id = (SELECT user_id FROM user WHERE username=?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                groups.add(rs.getInt("usergroup_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return groups;
    }

    /**
     * 获取数据库中与指定用户名相关的用户总数
     *
     * @param username 指定用户名
     * @return 相关的用户总数
     */
    @Override
    public int getTotalRow(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM user WHERE username LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + username + "%");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rows = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return rows;
    }

    /**
     * 获取数据库中指定状态的用户总数
     *
     * @param status 状态
     * @return 相关的用户总数
     */
    @Override
    public int getTotalRowByStatus(int status) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM user";
            if (STATUS_ALL == status) {
                sql += "";
            } else if (STATUS_NORMAL == status) {
                sql += " WHERE status = 1";
            } else if (STATUS_AUDIT == status) {
                sql += " WHERE status = 0";
            } else if (STATUS_NOT_APPROVED == status) {
                sql += " WHERE status = -1";
            } else if (STATUS_FORBIDDEN == status) {
                sql += " WHERE status = -2";
            } else {
                sql += "";
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rows = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return rows;
    }

    /**
     * 获取数据库中所有用户的总数
     *
     * @return 所有用户总数
     */
    @Override
    public int getTotalRow() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM user";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rows = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return rows;
    }

    /**
     * 获取数据库中指定用户状态, 且与指定用户名相关的用户总数
     *
     * @param status   状态
     * @param username 指定用户名
     * @return 相关的用户总数
     */
    @Override
    public int getTotalRow(int status, String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM user WHERE username LIKE ?";
            if (STATUS_ALL == status) {
                sql += "";
            } else if (STATUS_NORMAL == status) {
                sql += " AND status = 1";
            } else if (STATUS_AUDIT == status) {
                sql += " AND status = 0";
            } else if (STATUS_NOT_APPROVED == status) {
                sql += " AND status = -1";
            } else if (STATUS_FORBIDDEN == status) {
                sql += " AND status = -2";
            } else {
                sql += "";
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + username + "%");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rows = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return rows;
    }

    /**
     * 向数据库中插入用户与角色的关联
     *
     * @param userId 待关联的用户ID
     * @param role   待关联的角色
     */
    @Override
    public void addRole(int userId, String role) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO user_role (user_id, role) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, role);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 向数据库中插入用户信息
     *
     * @param userPo 待插入的用户对象
     */
    @Override
    public void addUser(UserPo userPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //添加用户信息
            String userSql = "INSERT INTO user (username, password, sex, email) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(userSql);
            pstmt.setString(1, userPo.getUsername());
            pstmt.setString(2, userPo.getPassword());
            pstmt.setString(3, userPo.getSex());
            pstmt.setString(4, userPo.getEmail());
            pstmt.execute();
            //获取user_id
            String idSql = "SELECT user_id FROM user WHERE username=?";
            pstmt = conn.prepareStatement(idSql);
            pstmt.setString(1, userPo.getUsername());
            rs = pstmt.executeQuery();
            int user_id;
            if (rs.next()) {
                user_id = rs.getInt("user_id");
                //设置角色
                String roleSql = "INSERT INTO user_role (user_id, role) VALUES (?, ?)";
                pstmt = conn.prepareStatement(roleSql);
                pstmt.setInt(1, user_id);
                pstmt.setString(2, ROLE_USER); //默认为user
                pstmt.execute();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 移除数据库中用户与角色的关联
     *
     * @param userId 待移除的用户ID
     * @param role   待移除的角色
     */
    @Override
    public void deleteRoleInUser(int userId, String role) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM user_role WHERE user_id=? AND role=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, role);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 更新数据库中的部分用户信息
     *
     * @param userPo       包含最新信息的用户对象
     * @param updatedTypes 待更新部分的标识符列表
     */
    @Override
    public void update(UserPo userPo, List<Integer> updatedTypes) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            String sql;
            for (int updatedType : updatedTypes) {
                switch (updatedType) {
                    case UPDATE_SEX:
                        sql = "UPDATE user SET sex=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, userPo.getSex());
                        pstmt.setInt(2, userPo.getUserId());
                        pstmt.execute();
                        break;
                    case UPDATE_EMAIL:
                        sql = "UPDATE user SET email=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, userPo.getEmail());
                        pstmt.setInt(2, userPo.getUserId());
                        pstmt.execute();
                        break;
                    case UPDATE_PASSWORD:
                        sql = "UPDATE user SET password=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, userPo.getPassword());
                        pstmt.setInt(2, userPo.getUserId());
                        pstmt.execute();
                        break;
                    case UPDATE_STATUS:
                        sql = "UPDATE user SET status=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, userPo.getStatus());
                        pstmt.setInt(2, userPo.getUserId());
                        pstmt.execute();
                        break;
                    default:
                        break;
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 构建数据库检索语句
     *
     * @param isSearch  是否通过用户名搜索
     * @param status    用户状态
     * @param order     排序依据
     * @param isReverse 是否降序
     * @return 构建完成的检索语句
     */
    private String makeQuerySql(boolean isSearch, int status, int order, boolean isReverse) {
        String sql;
        if (isSearch) {
            sql = "SELECT * FROM user WHERE username LIKE ?";
            if (STATUS_ALL == status) {
                sql += "";
            } else if (STATUS_NORMAL == status) {
                sql += " AND status = 1";
            } else if (STATUS_AUDIT == status) {
                sql += " AND status = 0";
            } else if (STATUS_NOT_APPROVED == status) {
                sql += " AND status = -1";
            } else if (STATUS_FORBIDDEN == status) {
                sql += " AND status = -2";
            } else {
                sql += "";
            }
        } else {
            sql = "SELECT * FROM user";
            if (STATUS_ALL == status) {
                sql += "";
            } else if (STATUS_NORMAL == status) {
                sql += " WHERE status = 1";
            } else if (STATUS_AUDIT == status) {
                sql += " WHERE status = 0";
            } else if (STATUS_NOT_APPROVED == status) {
                sql += " WHERE status = -1";
            } else if (STATUS_FORBIDDEN == status) {
                sql += " WHERE status = -2";
            } else {
                sql += "";
            }
        }
        if (ORDER_BY_SEX == order) {
            sql += " ORDER BY sex";
        } else if (ORDER_BY_CREATED == order) {
            sql += " ORDER BY created";
        } else if (ORDER_BY_STATUS == order) {
            sql += " ORDER BY status";
        } else {
            sql += " ORDER BY created";
        }
        if (isReverse) {
            sql += " DESC";
        }
        return sql + " LIMIT ?, ?";
    }

    /**
     * 封装通过ResultSet构建用户对象的方法
     *
     * @param rs 当前位置的数据光标
     * @return 文件对象
     * @throws SQLException 数据库发生错误则抛出异常
     */
    private UserPo getUser(ResultSet rs) throws SQLException {
        UserPo user = new UserPo();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setSex(rs.getString("sex"));
        user.setEmail(rs.getString("email"));
        user.setStatus(rs.getInt("status"));
        user.setCreated(rs.getTimestamp("created"));
        return user;
    }
}
