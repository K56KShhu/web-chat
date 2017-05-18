package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReportPo;

import java.util.List;

public interface ReportDao {
    void addReport(ReportPo reportPo);

    void deleteReport(int reportId);

    ReportPo selectReport(int reportId);

    List<ReportPo> selectReportsByOrder(int startIndex, int rowsOnePage, int order, boolean isReverse);

    int getTotalRow();
}
