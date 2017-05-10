package com.zkyyo.www.service;

import com.zkyyo.www.dao.ReportDao;
import com.zkyyo.www.bean.po.ReportPo;

import java.util.List;

public class ReportService {
    public static final int CONTENT_TYPE_CHAT = 0;
    public static final int CONTENT_TYPE_SHARE_IMAGE = 1;
    public static final int CONTENT_TYPE_SHARE_FILE = 2;

    private ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public boolean isValidContentType(String contentType) {
        return true;
    }

    public boolean isValidReason(String reason) {
        return true;
    }

    public void addReport(ReportPo reportPo) {
        reportDao.addReport(reportPo);
    }

    public List<ReportPo> findReports() {
        return reportDao.selectReports();
    }

    public boolean isValidId(String reportId) {
        return true;
    }

    public boolean isExisted(int reportId) {
        return true;
    }

    public void deleteReport(int reportId) {
        reportDao.deleteReport(reportId);
    }
}
