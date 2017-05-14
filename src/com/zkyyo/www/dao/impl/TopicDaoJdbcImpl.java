package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.TopicPo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopicDaoJdbcImpl implements TopicDao {
    public static final int ORDER_BY_REPLY_ACCOUNT = 0;
    public static final int ORDER_BY_LAST_TIME = 1;
    public static final int ORDER_BY_CREATED = 2;
    public static final int ORDER_BY_ACCESS = 3;

    public static final int ACCESS_PUBLIC = 0;
    public static final int ACCESS_PRIVATE = 1;
    public static final int ACCESS_ALL = 2;

    private DataSource dataSource;

    public TopicDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<TopicPo> selectTopicsByOrder() {
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
                topics.add(getTopic(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return topics;
    }

    @Override
    public List<TopicPo> selectTopicsByOrder(int type, int startIndex, int ROWS_ONE_PAGE, int order, boolean isReverse) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TopicPo> topics = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = makeQuerySql(false, type, order, isReverse);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startIndex);
            pstmt.setInt(2, ROWS_ONE_PAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                topics.add(getTopic(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return topics;
    }

    @Override
    public List<TopicPo> selectPossibleTopicsByTitle(Set<String> keys) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TopicPo> topics = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            for (String key : keys) {
                String sql = "SELECT * FROM topic WHERE title LIKE ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + key + "%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    topics.add(getTopic(rs));
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return topics;
    }

    @Override
    public List<TopicPo> selectTopicsByGroup(int groupId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TopicPo> topics = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM topic WHERE topic_id IN (SELECT topic_id FROM topic_usergroup WHERE usergroup_id=?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                topics.add(getTopic(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return topics;
    }

    @Override
    public Set<TopicPo> selectTopicsByGroups(Set<Integer> groupIds) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<TopicPo> topics = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM topic WHERE is_private = 1 AND topic_id IN (SELECT topic_id FROM topic_usergroup WHERE usergroup_id=?)";
            pstmt = conn.prepareStatement(sql);
            for (int groupId : groupIds) {
                pstmt.setInt(1, groupId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    topics.add(getTopic(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return  topics;
    }

    @Override
    public Set<TopicPo> selectTopicsByTitle(int type, Set<String> keys, int startIndex, int rowsOnePage) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<TopicPo> topics = new HashSet<>();

        try {
            conn = dataSource.getConnection();
//            String sql = "SELECT * FROM topic WHERE title LIKE ? AND is_private=? ORDER BY reply_account DESC LIMIT ?, ?";
            String sql = makeQuerySql(true, type, ORDER_BY_REPLY_ACCOUNT, true);
            pstmt = conn.prepareStatement(sql);
            for (String key : keys) {
                pstmt.setString(1, "%" + key + "%");
                pstmt.setInt(2, startIndex);
                pstmt.setInt(3, rowsOnePage);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    topics.add(getTopic(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return topics;
    }

    @Override
    public Set<Integer> selectGroupsByTopicId(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<Integer> groups = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT usergroup_id FROM topic_usergroup WHERE topic_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                groups.add(rs.getInt("usergroup_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return groups;
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
                return getTopic(rs);
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

    @Override
    public int getTotalRow(int accessType) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM topic";
            if (ACCESS_PUBLIC == accessType) {
                sql += " WHERE is_private = 0";
            } else if (ACCESS_PRIVATE == accessType) {
                sql += " WHERE is_private = 1";
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rows = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return rows;
    }

    @Override
    public int getTotalRowByTitle(Set<String> keys) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<Integer> topics = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT topic_id FROM topic WHERE title LIKE ?";
            pstmt = conn.prepareStatement(sql);
            for (String key : keys) {
                pstmt.setString(1, "%" + key + "%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    topics.add(rs.getInt("topic_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return topics.size();
    }

    private TopicPo getTopic(ResultSet rs) throws SQLException {
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
        return topic;
    }

    private String makeQuerySql(boolean isSearch, int type, int order, boolean isReverse) {
        /*
        String sql;
        if (isSearch) {
            sql = "SELECR * FROM topic WHERE title LIKE ? AND";
        } else {
            sql = "SELECT * FROM topic";
        }
        if (ACCESS_PUBLIC == type) {
            sql += " WHERE is_private = 0";
        } else if (ACCESS_PRIVATE == type) {
            sql += " WHERE is_private = 1";
        } else if (ACCESS_ALL == type) {
            sql += "";
        } else {
            sql += "";
        }
        */
        String sql;
        if (isSearch) {
            sql = "SELECT * FROM topic WHERE title LIKE ?";
            if (ACCESS_PUBLIC == type) {
                sql += " AND is_private = 0";
            } else if (ACCESS_PRIVATE == type) {
                sql += " AND is_private = 1";
            } else if (ACCESS_ALL == type) {
                sql += "";
            } else {
                sql += "";
            }
        } else {
            sql = "SELECT * FROM topic";
            if (ACCESS_PUBLIC == type) {
                sql += " WHERE is_private = 0";
            } else if (ACCESS_PRIVATE == type) {
                sql += " WHERE is_private = 1";
            } else if (ACCESS_ALL == type) {
                sql += "";
            } else {
                sql += "";
            }
        }
        if (ORDER_BY_REPLY_ACCOUNT == order) {
            sql += " ORDER BY reply_account";
        } else if (ORDER_BY_LAST_TIME == order) {
            sql += " ORDER BY last_time";
        } else if (ORDER_BY_CREATED == order) {
            sql += " ORDER BY created";
        } else if (ORDER_BY_ACCESS == order) {
            sql += " ORDER BY is_private";
        } else {
            sql += " ORDER BY created";
        }
        if (isReverse) {
            sql += " DESC";
        }
        return sql + " LIMIT ?, ?";
    }

    /*
    public TopicDaoJdbcImpl() {

    }

    public static void main(String[] args) {
        TopicDaoJdbcImpl topicDaoJdbc = new TopicDaoJdbcImpl();
        System.out.println(topicDaoJdbc.makeQuerySql(ACCESS_PRIVATE, ORDER_BY_CREATED, true, true));
    }
    */
}
