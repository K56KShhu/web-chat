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

public class GroupDaoJdbcImpl implements GroupDao {
    private DataSource dataSource;

    public GroupDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
