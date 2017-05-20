package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.TopicDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.TopicPo;
import com.zkyyo.www.service.RememberService;

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

/**
 * 通过JDBC实现TopicDao接口
 */
public class TopicDaoJdbcImpl implements TopicDao {
    /**
     * 作为排序的标识符, 排序依据为回复数量
     */
    public static final int ORDER_BY_REPLY_ACCOUNT = 0;
    /**
     * 作为排序的标识符, 排序依据为最后回复时间
     */
    public static final int ORDER_BY_LAST_TIME = 1;
    /**
     * 作为排序的标识符, 排序依据为创建时间
     */
    public static final int ORDER_BY_CREATED = 2;
    /**
     * 作为排序的标识符, 排序依据为讨论区类型
     */
    public static final int ORDER_BY_ACCESS = 3;

    /**
     * 讨论区类型, 表示公开讨论区
     */
    public static final int ACCESS_PUBLIC = 0;
    /**
     * 讨论区类型, 表示授权讨论区
     */
    public static final int ACCESS_PRIVATE = 1;
    /**
     * 讨论区类型, 表示所有讨论区
     */
    public static final int ACCESS_ALL = 2;

    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    /**
     * 构建对象
     *
     * @param dataSource 传入的数据库连接池
     */
    public TopicDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库中指定类型的多个讨论区信息, 同时进行分页和排序
     *
     * @param type          讨论区类型
     * @param startIndex    起始下标
     * @param ROWS_ONE_PAGE 获取总数
     * @param order         排序依据
     * @param isReverse     是否降序
     * @return 包含多个讨论区信息的列表, 不包含任何讨论区则返回size为0的列表
     */
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

    /**
     * 获取数据库中与指定小组关联的所有讨论区信息
     *
     * @param groupId 指定小组的ID
     * @return 包含多个讨论区信息的列表, 不包含任何讨论区则返回size为0的列表
     */
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

    /**
     * 获取数据库中与多个小组关联的所有讨论区信息
     *
     * @param groupIds 多个小组的ID集合
     * @return 包含多个讨论区信息的集合, 不包含任何讨论区则返回size为0的集合
     */
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
        return topics;
    }

    @Override
    public List<Integer> selectTopicsBeforeDaysAboutLastReply(int accessType, int days) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> topics = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT topic_id FROM topic WHERE TO_DAYS(NOW()) - TO_DAYS(last_time) >= ?";
            if (ACCESS_PUBLIC == accessType) {
                sql += " AND is_private = 0";
            } else if (ACCESS_PRIVATE == accessType) {
                sql += " AND is_private = 1";
            } else if (ACCESS_ALL == accessType) {
                sql += "";
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, days);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                topics.add(rs.getInt("topic_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return topics;
    }

    /**
     * 获取数据库中指定类型, 且标题符合关键词的多个讨论区信息, 同时进行分页
     *
     * @param type        讨论区类型
     * @param keys        关于标题的关键词集合
     * @param startIndex  起始下标
     * @param rowsOnePage 获取总数
     * @return 包含多个讨论区信息的列表, 不包含任何讨论区则返回size为0的列表
     */
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

    /**
     * 获取数据库中与指定讨论区有关的所有小组ID
     *
     * @param topicId 指定讨论区的ID
     * @return 包含所有小组ID的集合, 不包含任何小组ID则返回size为0的集合
     */
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

    /**
     * 删除数据库中指定讨论区ID的讨论区信息
     *
     * @param topicId 待删除的讨论区ID
     */
    @Override
    public TopicPo selectTopicByTopicId(int topicId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM topic WHERE topic_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, topicId);
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

    /**
     * 向数据库中插入讨论区信息
     *
     * @param topicPo 待插入的讨论区对象
     */
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

    /**
     * 删除数据库中指定讨论区ID的讨论区信息
     *
     * @param topicId 待删除的讨论区ID
     */
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

    /**
     * 更新数据库中讨论区信息
     *
     * @param topicPo 包含最新信息的讨论区对象
     */
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

    /**
     * 获取数据库中指定讨论区类型的所有讨论区数量
     *
     * @param accessType 讨论区类型
     * @return 总数量
     */
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

    /**
     * 获取数据库中指定讨论区类型, 且标题符合关键词的所有讨论区数量
     *
     * @param accessType 讨论区类型
     * @param keys       关于标题的关键词集合
     * @return 总数量
     */
    @Override
    public int getTotalRowByTitle(int accessType, Set<String> keys) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<Integer> topics = new HashSet<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT topic_id FROM topic WHERE title LIKE ?";
            if (ACCESS_PUBLIC == accessType) {
                sql += " AND is_private = 0";
            } else if (ACCESS_PRIVATE == accessType) {
                sql += " AND is_private = 1";
            } else if (ACCESS_ALL == accessType) {
                sql += "";
            }
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

    /**
     * 封装通过ResultSet构建讨论区对象的方法
     *
     * @param rs 当前位置的数据光标
     * @return 文件对象
     * @throws SQLException 数据库发生错误则抛出异常
     */
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

    /**
     * 构建数据库检索语句
     *
     * @param isSearch  是否进行关于标题的关键词搜索
     * @param type      讨论区类型
     * @param order     排序依据
     * @param isReverse 是否降序
     * @return 构建完成的检索语句
     */
    private String makeQuerySql(boolean isSearch, int type, int order, boolean isReverse) {
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
}
