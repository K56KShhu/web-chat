package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.ReplyDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.ReplyPo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReplyDaoJdbcImpl implements ReplyDao {
    private DataSource dataSource;

    public ReplyDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    @Override
    public List<ReplyPo> selectReplysByTopicId(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReplyPo> replys = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM reply WHERE topic_id=? ORDER BY created";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReplyPo reply = new ReplyPo();
                reply.setReplyId(rs.getInt("reply_id"));
                reply.setTopicId(rs.getInt("topic_id"));
                reply.setUserId(rs.getInt("user_id"));
                reply.setContent(rs.getString("content"));
                reply.setContentType(rs.getInt("content_type"));
                reply.setCreated(rs.getTimestamp("created"));
                replys.add(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return replys;
    }

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
            DbClose.close(conn ,pstmt, rs);
        }
        return rows;
    }

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
