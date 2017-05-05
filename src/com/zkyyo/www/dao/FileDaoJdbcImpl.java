package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.po.FilePo;

import javax.sql.DataSource;
import java.sql.*;
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
    public List<FilePo> selectFiles(int topicId, int apply) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FilePo> files = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM upload_file WHERE topic_id=? AND apply=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            pstmt.setInt(2, apply);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int fileId = rs.getInt("upload_file_id");
                int userId = rs.getInt("user_id");
                String path = rs.getString("relative_path");
                Timestamp created = rs.getTimestamp("created");
                files.add(new FilePo(fileId, apply, userId, topicId, path, created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return files;
    }
}
