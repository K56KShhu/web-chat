package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.RememberDao;
import com.zkyyo.www.db.DbClose;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 通过JDBC实现RememberDao接口
 */
public class RememberDaoJdbcImpl implements RememberDao {
    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    /**
     * 构建对象
     *
     * @param dataSource 待传入的数据库连接池
     */
    public RememberDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 向数据库中插入Cookie信息
     *
     * @param uuid     待插入的Cookie Value
     * @param username 关联此Cookie的用户名
     */
    @Override
    public void addRemember(String uuid, String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO remember (remember_id, username) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, username);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 删除数据库中指定用户名的Cookie信息
     *
     * @param username 待删除的用户名
     */
    @Override
    public void delete(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM remember WHERE username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 获取数据库中指定Cookie Value的用户名
     *
     * @param uuid 指定的Cookie Value
     * @return 存在则返回用户名, 否则返回null;
     */
    @Override
    public String selectUsername(String uuid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT username FROM remember WHERE remember_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }
}
