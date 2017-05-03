package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.po.UserPo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDaoJdbcImpl implements UserDao {
    public static final int UPDATE_SEX = 1;
    public static final int UPDATE_EMAIL = 2;
    public static final int UPDATE_PASSWORD = 3;
    private DataSource dataSource;

    public UserDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserPo selectUserByUserId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserPo user = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Integer.toString(id));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserPo(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("sex"),
                        rs.getString("email"), rs.getInt("status"), rs.getTimestamp("created"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return user;
    }

    @Override
    public UserPo selectUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserPo user = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserPo(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("sex"),
                        rs.getString("email"), rs.getInt("status"), rs.getTimestamp("created"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return user;
    }

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

    @Override
    public Set<String> selectGroupsByUserId(int id) {
        return null;
    }

    @Override
    public Set<String> selectGroupsByUsername(String username) {
        return null;
    }

    @Override
    public void addUser(UserPo userPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO user (username, password, sex, email) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userPo.getUsername());
            pstmt.setString(2, userPo.getPassword());
            pstmt.setString(3, userPo.getSex());
            pstmt.setString(4, userPo.getEmail());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

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
}
