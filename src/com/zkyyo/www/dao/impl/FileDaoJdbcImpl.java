package com.zkyyo.www.dao.impl;

import com.zkyyo.www.bean.po.FilePo;
import com.zkyyo.www.dao.FileDao;
import com.zkyyo.www.db.DbClose;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过JDBC实现FileDao接口
 */
public class FileDaoJdbcImpl implements FileDao {
    /**
     * 文件应用类型, 表示分享图片
     */
    public static final int APPLY_IMAGE = 0;
    /**
     * 文件应用类型, 表示分享文件
     */
    public static final int APPLY_FILE = 1;
    /**
     * 作为排序的标识符, 排序依据为文件上传时间
     */
    public static final int ORDER_BY_CREATED = 0;
    /**
     * 数据池
     */
    private DataSource dataSource;

    /**
     * 构建对象, 同时传入数据池
     *
     * @param dataSource 待传入的连接池
     */
    public FileDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 向数据库中插入文件信息
     *
     * @param filePo 待插入的文件对象
     */
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

    /**
     * 删除数据库中的文件信息
     *
     * @param fileId 待删除的文件ID
     */
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

    /**
     * 获取数据库中的文件信息
     *
     * @param fileId 待获取的文件ID
     * @return 存在则返回该文件对象, 不存在则返回null
     */
    @Override
    public FilePo selectFile(int fileId) {
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

    /**
     * 获取数据库中一个讨论区中指定文件类型的多个文件信息, 同时进行分页和排序
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的文件数量
     * @param order       排序依据
     * @param isReverse   是否降序
     * @param topicId     讨论区ID
     * @param apply       文件类型
     * @return 返回一个关于文件的列表, 不包含任何认为则为size为0的空列表
     */
    @Override
    public List<FilePo> selectFilesByTopicId(int startIndex, int rowsOnePage, int order,
                                             boolean isReverse, int topicId, int apply) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FilePo> files = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = makeQuerySql(startIndex, rowsOnePage, order, isReverse);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            pstmt.setInt(2, apply);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                files.add(getFile(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return files;
    }

    /**
     * 获取数据库中指定讨论区ID和文件类型的所有文件数量
     *
     * @param topicId 讨论区ID
     * @param apply   文件类型
     * @return 文件数量
     */
    @Override
    public int getTotalRow(int topicId, int apply) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM upload_file WHERE topic_id=? AND apply=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            pstmt.setInt(2, apply);
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
     * 构建数据库检索语句
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的文件总数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 构建完成的检索语句
     */
    private String makeQuerySql(int startIndex, int rowsOnePage, int order, boolean isReverse) {
        String sql = "SELECT * FROM upload_file WHERE topic_id=? AND apply=?";
        //判断排序依据
        if (ORDER_BY_CREATED == order) {
            sql += " ORDER BY created";
        } else {
            sql += " ORDER BY created";
        }
        //判断升序降序
        if (isReverse) {
            sql += " DESC";
        }
        return sql + " LIMIT " + startIndex + "," + rowsOnePage;
    }

    /**
     * 封装通过ResultSet构建文件对象的方法
     *
     * @param rs 当前位置的数据光标
     * @return 文件对象
     * @throws SQLException 数据库发生错误则抛出异常
     */
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
