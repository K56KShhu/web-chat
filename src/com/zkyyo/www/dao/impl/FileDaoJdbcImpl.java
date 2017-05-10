package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.FileDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.FilePo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDaoJdbcImpl implements FileDao {
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
}
