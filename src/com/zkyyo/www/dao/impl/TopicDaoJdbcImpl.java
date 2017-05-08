package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.po.TopicPo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoJdbcImpl implements TopicDao {
    private DataSource dataSource;

    public TopicDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<TopicPo> selectTopics() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<TopicPo> topics = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM topic";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                TopicPo topic = new TopicPo();
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTitle(rs.getString("title"));
                topic.setDescription(rs.getString("description"));
                topic.setCreatorId(rs.getInt("creator_id"));
                topic.setLastModifyId(rs.getInt("last_modify_id"));
                topic.setIsPrivate(rs.getInt("is_private"));
                topic.setReplyAccount(rs.getInt("reply_account"));
                topic.setLastTime(rs.getTimestamp("last_time"));
                topic.setCreated(rs.getTimestamp("created"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return topics;
    }

    @Override
    public TopicPo selectTopicByTopicId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM topic WHERE topic_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                TopicPo topic = new TopicPo();
                topic.setTopicId(id);
                topic.setTitle(rs.getString("title"));
                topic.setDescription(rs.getString("description"));
                topic.setCreatorId(rs.getInt("creator_id"));
                topic.setLastModifyId(rs.getInt("last_modify_id"));
                topic.setIsPrivate(rs.getInt("is_private"));
                topic.setReplyAccount(rs.getInt("reply_account"));
                topic.setLastTime(rs.getTimestamp("last_time"));
                topic.setCreated(rs.getTimestamp("created"));
                return topic;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public void addTopic(TopicPo topicPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO topic (title, description, is_private, creator_id, last_modify_id) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topicPo.getTitle());
            pstmt.setString(2, topicPo.getDescription());
            pstmt.setInt(3, topicPo.getIsPrivate());
            pstmt.setInt(4, topicPo.getCreatorId());
            pstmt.setInt(5, topicPo.getLastModifyId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    @Override
    public void deleteTopicByTopicId(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //删除讨论区信息
            String topicSql = "DELETE FROM topic WHERE topic_id=?";
            pstmt = conn.prepareStatement(topicSql);
            pstmt.setInt(1, topicId);
            pstmt.execute();
            //删除讨论区聊天信息
            String replySql = "DELETE FROM reply WHERE topic_id=?";
            pstmt = conn.prepareStatement(replySql);
            pstmt.setInt(1, topicId);
            pstmt.execute();
            //删除讨论区上传文件信息
            String fileSql = "DELETE FROM upload_file WHERE topic_id=?";
            pstmt = conn.prepareStatement(fileSql);
            pstmt.setInt(1, topicId);
            pstmt.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    @Override
    public void updateTopic(TopicPo topicPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "UPDATE topic SET title=?, description=?, last_modify_id=? WHERE topic_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topicPo.getTitle());
            pstmt.setString(2, topicPo.getDescription());
            pstmt.setInt(3, topicPo.getLastModifyId());
            pstmt.setInt(4, topicPo.getTopicId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }
}
