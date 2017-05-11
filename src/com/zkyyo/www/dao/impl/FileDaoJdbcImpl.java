package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.FileDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.FilePo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDaoJdbcImpl implements FileDao {
    public static final int APPLY_IMAGE = 0;
    public static final int APPLY_FILE = 1;

    public static final int ORDER_BY_CREATED = 0;

    private DataSource dataSource;

    public FileDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addFile(FilePo filePo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO upload_file (apply, user_id, topic_id, relative_path) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, filePo.getApply());
            pstmt.setInt(2, filePo.getUserId());
            pstmt.setInt(3, filePo.getTopicId());
            pstmt.setString(4, filePo.getPath());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    @Override
    public List<FilePo> selectFilesByTopicId(int topicId, int apply) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FilePo> files = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM upload_file WHERE topic_id=? AND apply=? ORDER BY created";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            pstmt.setInt(2, apply);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FilePo file = new FilePo();
                file.setFileId(rs.getInt("upload_file_id"));
                file.setApply(apply);
                file.setUserId(rs.getInt("user_id"));
                file.setPath(rs.getString("relative_path"));
                file.setCreated(rs.getTimestamp("created"));
                files.add(file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return files;
    }

    @Override
    public List<FilePo> selectFilesByTopicId(int currentPage, int rowsOnePage, int order, boolean isReverse, int topicId, int apply) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<FilePo> files = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = makeQuerySql(currentPage, rowsOnePage, order, isReverse, topicId, apply);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                files.add(getFile(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return files;
    }

    @Override
    public FilePo selectFileByFileId(int fileId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM upload_file WHERE upload_file_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fileId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return getFile(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public void deleteFile(int fileId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM upload_file WHERE upload_file_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fileId);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    @Override
    public int getTotalRow(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM upload_file WHERE topic_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
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

    private FilePo getFile(ResultSet rs) throws SQLException {
        FilePo file = new FilePo();
        file.setFileId(rs.getInt(("upload_file_id")));
        file.setUserId(rs.getInt("user_id"));
        file.setApply(rs.getInt("apply"));
        file.setTopicId(rs.getInt("topic_id"));
        file.setPath(rs.getString("relative_path"));
        file.setCreated(rs.getTimestamp("created"));
        return file;
    }

    private String makeQuerySql(int currentPage, int rowsOnePage, int order, boolean isReverse, int topicId, int apply) {
        String sql = "SELECT * FROM upload_file WHERE topic_id = " + topicId;
        if (APPLY_IMAGE == apply) {
            sql += " AND apply = 1";
        } else if (APPLY_FILE == apply) {
            sql += " AND apply = 2";
        }
        if (ORDER_BY_CREATED == order) {
            sql += " ORDER BY created";
        } else {
            sql += " ORDER BY created";
        }
        if (isReverse) {
            sql += " DESC";
        }
        return sql + " LIMIT " + currentPage + "," + rowsOnePage;
    }
}
