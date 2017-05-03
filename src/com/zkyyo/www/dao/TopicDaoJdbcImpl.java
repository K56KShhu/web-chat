package com.zkyyo.www.dao;

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
                int topicId = rs.getInt("topic_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int creatorId = rs.getInt("creator_id");
                int lastModifyId = rs.getInt("last_modify_id");
                int isPrivate = rs.getInt("is_private");
                int replyAccount = rs.getInt("reply_account");
                Timestamp lastTime = rs.getTimestamp("last_time");
                Timestamp created = rs.getTimestamp("created");
                topics.add(new TopicPo(topicId, title, description, creatorId, lastModifyId, isPrivate, replyAccount, lastTime, created));
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
                int topicId = rs.getInt("topic_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int creatorId = rs.getInt("creator_id");
                int lastModifyId = rs.getInt("last_modify_id");
                int isPrivate = rs.getInt("is_private");
                int replyAccount = rs.getInt("reply_account");
                Timestamp lastTime = rs.getTimestamp("last_time");
                Timestamp created = rs.getTimestamp("created");
                return (new TopicPo(topicId, title, description, creatorId, lastModifyId, isPrivate, replyAccount, lastTime, created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }
}
