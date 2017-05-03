package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.po.UserPo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDaoJdbcImpl implements UserDao {
    public static final int UPDATE_SEX = 1;
    public static final int UPDATE_EMAIL = 2;
    public static final int UPDATE_PASSWORD = 3;
    private DataSource dataSource;

    public UserDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
    public String getPassword(UserPo user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT password FROM user WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }
    */

    @Override
    public UserPo selectUser(UserPo userPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserPo user = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userPo.getUsername());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserPo(rs.getString("username"), rs.getString("password"), rs.getString("sex"),
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
    public Set<String> selectRoles(UserPo userPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<String> roles = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT role FROM user_role WHERE user_id = (SELECT user_id FROM user WHERE username = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userPo.getUsername());
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
    public Set<String> selectGroups(UserPo userPo) {
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
                        sql = "UPDATE user SET sex=? WHERE username=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, userPo.getSex());
                        pstmt.setString(2, userPo.getUsername());
                        pstmt.execute();
                        break;
                    case UPDATE_EMAIL:
                        sql = "UPDATE user SET email=? WHERE username=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, userPo.getEmail());
                        pstmt.setString(2, userPo.getUsername());
                        pstmt.execute();
                        break;
                    case UPDATE_PASSWORD:
                        sql = "UPDATE user SET password=? WHERE username=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, userPo.getPassword());
                        pstmt.setString(2, userPo.getUsername());
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
