package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.po.ReportPo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoJdbcImpl implements ReportDao {
    private DataSource dataSource;

    public ReportDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
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
    public List<ReportPo> selectReports() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ReportPo> reports = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM report";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ReportPo report = new ReportPo();
                report.setReportId(rs.getInt("report_id"));
                report.setUserId(rs.getInt("user_id"));
                report.setContentId(rs.getInt("content_id"));
                report.setContentType(rs.getInt("content_type"));
                report.setReason(rs.getString("reason"));
                reports.add(report);
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
}
