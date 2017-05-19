package com.zkyyo.www.dao.impl;

import com.zkyyo.www.bean.po.GroupPo;
import com.zkyyo.www.dao.GroupDao;
import com.zkyyo.www.db.DbClose;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过JDBC实现GroupDao接口
 */
public class GroupDaoJdbcImpl implements GroupDao {
    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    /**
     * 构建对象
     *
     * @param dataSource 数据库连接池
     */
    public GroupDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库中的所有小组
     *
     * @return 包含所有小组的列表, 不包含任何小组则返回size为0的列表
     */
    @Override
    public List<GroupPo> selectGroups() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<GroupPo> groups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM usergroup";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                groups.add(getGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return groups;
    }

    /**
     * 获取数据库中指定小组ID的小组信息
     *
     * @param groupId 待获取的小组ID
     * @return 存在则返回小组对象, 否则返回null
     */
    @Override
    public GroupPo selectGroup(int groupId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM usergroup WHERE usergroup_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return getGroup(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 获取数据库中指定小组名的所有小组
     *
     * @param name 待获取的小组名
     * @return 包含指定小组的列表, 不包含任何小组则返回size为0的列表
     */
    @Override
    public List<GroupPo> selectGroupsByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<GroupPo> groups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM usergroup WHERE name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                groups.add(getGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return groups;
    }

    /**
     * 获取数据库中指定讨论区ID的所有小组
     *
     * @param topicId 指定的讨论区ID
     * @return 包含指定小组的列表, 不包含任何小组则返回size为0的列表
     */
    @Override
    public List<GroupPo> selectGroupsByTopic(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<GroupPo> groups = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM usergroup WHERE usergroup_id IN (SELECT usergroup_id FROM topic_usergroup WHERE topic_id =?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                groups.add(getGroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return groups;
    }

    /**
     * 向数据库中插入用户与小组的关联
     *
     * @param groupId 待关联的小组ID
     * @param userId  待关联的用户ID
     */
    @Override
    public void removeUserInGroup(int groupId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //移除用户与组别的对应关系
            String userSql = "DELETE FROM user_usergroup WHERE usergroup_id=? AND user_id=?";
            pstmt = conn.prepareStatement(userSql);
            pstmt.setInt(1, groupId);
            pstmt.setInt(2, userId);
            pstmt.execute();
            //组别人数减1
            String groupSql = "UPDATE usergroup SET population = population - 1 WHERE usergroup_id=?";
            pstmt = conn.prepareStatement(groupSql);
            pstmt.setInt(1, groupId);
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 向数据库中插入小组与讨论区的关联
     *
     * @param groupId 待关联的小组ID
     * @param topicId 待关联的讨论区ID
     */
    @Override
    public void removeGroupInTopic(int groupId, int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM topic_usergroup WHERE usergroup_id=? AND topic_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            pstmt.setInt(2, topicId);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 向数据库中插入用户与小组的关联
     *
     * @param groupId 待关联的小组ID
     * @param userId  待关联的用户ID
     */
    @Override
    public void addUserInGroup(int groupId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //添加用户与组别对应关系
            String userSql = "INSERT INTO user_usergroup (usergroup_id, user_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(userSql);
            pstmt.setInt(1, groupId);
            pstmt.setInt(2, userId);
            pstmt.execute();
            //组别人数加1
            String groupSql = "UPDATE usergroup SET population = population + 1 WHERE usergroup_id=?";
            pstmt = conn.prepareStatement(groupSql);
            pstmt.setInt(1, groupId);
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 删除数据库中的指定小组
     *
     * @param groupId 待删除的小组ID
     */
    @Override
    public void deleteGroup(int groupId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //删除组别
            String groupSql = "DELETE FROM usergroup WHERE usergroup_id=?";
            pstmt = conn.prepareStatement(groupSql);
            pstmt.setInt(1, groupId);
            pstmt.execute();

            //删除用户与组别的对应关系
            String userSql = "DELETE FROM user_usergroup WHERE usergroup_id=?";
            pstmt = conn.prepareStatement(userSql);
            pstmt.setInt(1, groupId);
            pstmt.execute();

            //删除讨论区与组别的对应关系
            String topicSql = "DELETE FROM topic_usergroup WHERE usergroup_id=?";
            pstmt = conn.prepareStatement(topicSql);
            pstmt.setInt(1, groupId);
            pstmt.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 向数据库中插入小组
     *
     * @param groupPo 待插入的小组
     */
    @Override
    public void addGroup(GroupPo groupPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO usergroup (name, description) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, groupPo.getName());
            pstmt.setString(2, groupPo.getDescription());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 向数据库中插入小组与讨论区的关联
     *
     * @param groupId 待关联的小组ID
     * @param topicId 待关联的讨论区ID
     */
    @Override
    public void addGroupInTopic(int groupId, int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO topic_usergroup (usergroup_id, topic_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            pstmt.setInt(2, topicId);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 封装通过ResultSet构建小组对象的方法
     *
     * @param rs 当前位置的数据光标
     * @return 小组对象
     * @throws SQLException 数据库发生错误时抛出异常
     */
    private GroupPo getGroup(ResultSet rs) throws SQLException {
        GroupPo group = new GroupPo();
        group.setGroupId(rs.getInt("usergroup_id"));
        group.setName(rs.getString("name"));
        group.setDescription(rs.getString("description"));
        group.setPopulation(rs.getInt("population"));
        group.setCreated(rs.getTimestamp("created"));
        return group;
    }
}
