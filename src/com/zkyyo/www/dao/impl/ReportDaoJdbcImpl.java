package com.zkyyo.www.dao.impl;

import com.zkyyo.www.dao.ReportDao;
import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.bean.po.ReportPo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoJdbcImpl implements ReportDao {
    public static final int CONTENT_TYPE_REPLY = 0;
    public static final int CONTENT_TYPE_SHARE_IMAGE = 1;
    public static final int CONTENT_TYPE_SHARE_FILE = 2;

    public static final int ORDER_BY_CONTENT_TYPE = 0;
    public static final int ORDER_BY_CREATED = 1;

    private DataSource dataSource;

    public ReportDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
