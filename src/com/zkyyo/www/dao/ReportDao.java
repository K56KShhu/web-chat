package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReportPo;

import java.util.List;

public interface ReportDao {
    void addReport(ReportPo reportPo);
    List<ReportPo> selectReports();
    void deleteReport(int reportId);
}
