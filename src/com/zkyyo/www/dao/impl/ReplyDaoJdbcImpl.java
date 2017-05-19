package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.ReplyDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.ReplyPo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过JDBC实现ReplyDao接口
 */
public class ReplyDaoJdbcImpl implements ReplyDao {
    /**
     * 回复类型, 表示回复文本
     */
    public static final int CONTENT_TYPE_TEXT = 0;
    /**
     * 回复类型, 表示回复图片
     */
    public static final int CONTENT_TYPE_IMAGE = 1;

    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    /**
     * 构建对象
     *
     * @param dataSource 传入的数据库连接池
     */
    public ReplyDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 向数据库中插入回复信息
     *
     * @param replyPo 待插入的回复对象
     */
    @Override
    public void addReply(ReplyPo replyPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //添加回复
            String replySql = "INSERT INTO reply (topic_id, user_id, content, content_type) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(replySql);
            pstmt.setInt(1, replyPo.getTopicId());
            pstmt.setInt(2, replyPo.getUserId());
            pstmt.setString(3, replyPo.getContent());
            pstmt.setInt(4, replyPo.getContentType());
            pstmt.execute();
            //讨论区回复数量+1, 同时修改最新回复时间, 可能出现不同步问题
            String topicSql = "UPDATE topic SET reply_account = reply_account + 1, last_time=? WHERE topic_id=?";
            pstmt = conn.prepareStatement(topicSql);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(1, now);
            pstmt.setInt(2, replyPo.getTopicId());
            pstmt.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 获取数据库中指定讨论区ID的所有回复信息, 同时进行分页操作
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的文件总数
     * @param topicId     指定讨论区的ID
     * @return 包含回复信息的列表, 不包含任何回复信息则返回一个size为0的列表
     */
    @Override
    public List<ReplyPo> selectReplysByTopicId(int startIndex, int rowsOnePage, int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReplyPo> replys = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM reply WHERE topic_id=? ORDER BY created DESC LIMIT ?, ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            pstmt.setInt(2, startIndex);
            pstmt.setInt(3, rowsOnePage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                replys.add(getReply(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return replys;
    }

    /**
     * 获取数据库中指定回复ID的回复信息
     *
     * @param replyId 待获取的回复ID
     * @return 存在返回回复对象, 否则返回null
     */
    @Override
    public ReplyPo selectReplyByReplyId(int replyId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM reply WHERE reply_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, replyId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return getReply(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 删除数据库中指定回复ID的回复信息
     *
     * @param replyId 待删除的回复ID
     */
    @Override
    public void deleteReply(int replyId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //讨论区回复数量-1
            String topicSql = "UPDATE topic SET reply_account = reply_account - 1 WHERE topic_id = (SELECT topic_id FROM reply WHERE reply_id=?)";
            pstmt = conn.prepareStatement(topicSql);
            pstmt.setInt(1, replyId);
            pstmt.execute();
            //删除回复
            String replySql = "DELETE FROM reply WHERE reply_id=?";
            pstmt = conn.prepareStatement(replySql);
            pstmt.setInt(1, replyId);
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 获取数据库中指定讨论区ID的所有回复信息的行数
     *
     * @param topicId 指定讨论区的ID
     * @return 回复数量
     */
    @Override
    public int getTotalRow(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM reply WHERE topic_id=?";
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

    /**
     * 封装通过ResultSet构建回复对象的方法
     *
     * @param rs 当前位置的数据光标
     * @return 回复对象
     * @throws SQLException 数据库发生异常时抛出异常
     */
    private ReplyPo getReply(ResultSet rs) throws SQLException {
        ReplyPo reply = new ReplyPo();
        reply.setReplyId(rs.getInt("reply_id"));
        reply.setTopicId(rs.getInt("topic_id"));
        reply.setUserId(rs.getInt("user_id"));
        reply.setContent(rs.getString("content"));
        reply.setContentType(rs.getInt("content_type"));
        reply.setCreated(rs.getTimestamp("created"));
        return reply;
    }
}
