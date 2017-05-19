package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.ReportDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.ReportPo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过JDBC实现ReportDao接口
 */
public class ReportDaoJdbcImpl implements ReportDao {
    /**
     * 举报类型, 表示举报回复信息
     */
    public static final int CONTENT_TYPE_REPLY = 0;
    /**
     * 举报类型, 表示举报分享图片
     */
    public static final int CONTENT_TYPE_SHARE_IMAGE = 1;
    /**
     * 举报蕾西, 表示举报分享文件
     */
    public static final int CONTENT_TYPE_SHARE_FILE = 2;

    /**
     * 作为排序的标识符, 排序依据为举报类型
     */
    public static final int ORDER_BY_CONTENT_TYPE = 0;
    /**
     * 作为排序的标识符, 排序依据为举报时间
     */
    public static final int ORDER_BY_CREATED = 1;

    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    /**
     * 构建对象
     *
     * @param dataSource 传入的数据库连接池
     */
    public ReportDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库中指定举报ID的举报信息
     *
     * @param reportId 待获取的举报ID
     * @return 存在返回举报对象, 否则返回null
     */
    @Override
    public ReportPo selectReport(int reportId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM report WHERE report_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reportId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return getReport(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 向数据库中插入举报信息
     *
     * @param reportPo 待插入的举报对象
     */
    @Override
    public void addReport(ReportPo reportPo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO report (user_id, content_id, content_type, reason) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reportPo.getUserId());
            pstmt.setInt(2, reportPo.getContentId());
            pstmt.setInt(3, reportPo.getContentType());
            pstmt.setString(4, reportPo.getReason());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 获取数据库中的多个举报信息, 同时进行分页和排序
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的举报总数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 包含举报信息的列表, 不包含任何举报信息则返回一个size为0的列表
     */
    @Override
    public List<ReportPo> selectReportsByOrder(int startIndex, int rowsOnePage, int order, boolean isReverse) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ReportPo> reports = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = makeQuerySql(startIndex, rowsOnePage, order, isReverse);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                reports.add(getReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return reports;
    }

    /**
     * 删除数据库中指定举报ID的举报信息
     *
     * @param reportId 待删除的举报ID
     */
    @Override
    public void deleteReport(int reportId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM report WHERE report_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reportId);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
    }

    /**
     * 获取数据库中的所有举报信息的总行数
     *
     * @return 所有举报信息的总行数
     */
    @Override
    public int getTotalRow() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM report";
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
     * 封装通过ResultSet构建举报对象的方法
     *
     * @param rs 当前位置的数据光标
     * @return 文件对象
     * @throws SQLException 数据库发生错误则抛出异常
     */
    private ReportPo getReport(ResultSet rs) throws SQLException {
        ReportPo report = new ReportPo();
        report.setReportId(rs.getInt("report_id"));
        report.setUserId(rs.getInt("user_id"));
        report.setContentId(rs.getInt("content_id"));
        report.setContentType(rs.getInt("content_type"));
        report.setReason(rs.getString("reason"));
        report.setCreated(rs.getTimestamp("created"));
        return report;
    }

    /**
     * 构建数据库检索语句
     * @param startIndex 起始下标
     * @param rowsOnePage 获取的举报总数
     * @param order 排序依据
     * @param isReverse 是否倒序
     * @return 构建完成的检索语句
     */
    private String makeQuerySql(int startIndex, int rowsOnePage, int order, boolean isReverse) {
        String sql = "SELECT * FROM report ORDER BY";
        if (ORDER_BY_CONTENT_TYPE == order) {
            sql += " content_type";
        } else if (ORDER_BY_CREATED == order) {
            sql += " created";
        } else {
            sql += " created";
        }
        if (isReverse) {
            sql += " DESC";
        }
        return sql + " LIMIT " + startIndex + "," + rowsOnePage;
    }
}
