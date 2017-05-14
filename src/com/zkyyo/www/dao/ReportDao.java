package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReportPo;

import java.util.List;

public interface ReportDao {
    ReportPo selectReport(int reportId);
    void addReport(ReportPo reportPo);
    List<ReportPo> selectReports();
    List<ReportPo> selectReports(int startIndex, int rowsOnePage, int order, boolean isReverse);
    void deleteReport(int reportId);
    int getTotalRow();
}
