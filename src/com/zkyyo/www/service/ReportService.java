package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.ReportPo;
import com.zkyyo.www.dao.ReportDao;
import com.zkyyo.www.dao.impl.ReportDaoJdbcImpl;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

public class ReportService {
    public static final int CONTENT_TYPE_REPLY = 0;
    public static final int CONTENT_TYPE_SHARE_IMAGE = 1;
    public static final int CONTENT_TYPE_SHARE_FILE = 2;

    public static final int ORDER_BY_CONTENT_TYPE = 0;
    public static final int ORDER_BY_CREATED = 1;

    private static final int ROWS_ONE_PAGE = 15;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_REASON_LENGTH = 100;
    private static final int MIN_REASON_LENGTH = 0;

    private ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public boolean isValidId(String reportId) {
        return CheckUtil.isValidId(reportId, MAX_ID_LENGTH);
    }

    /*
    public boolean isValidContentType(String contentType) {
        return Integer.toString(CONTENT_TYPE_REPLY).equals(contentType)
                || Integer.toString(CONTENT_TYPE_SHARE_IMAGE).equals(contentType)
                || Integer.toString(CONTENT_TYPE_SHARE_FILE).equals(contentType);
    }
    */

    public boolean isValidReason(String reason) {
        return CheckUtil.isValidString(reason, MIN_REASON_LENGTH, MAX_REASON_LENGTH);
    }

    public boolean isExisted(int reportId) {
        return reportDao.selectReport(reportId) != null;
    }

    public void addReport(ReportPo reportPo) {
        reportDao.addReport(reportPo);
    }

    public void deleteReport(int reportId) {
        reportDao.deleteReport(reportId);
    }

    public PageBean<ReportPo> queryReports(int currentPage, int order, boolean isReverse) {
        PageBean<ReportPo> pageBean = new PageBean<>(currentPage, reportDao.getTotalRow(), ROWS_ONE_PAGE);
        int startIndex = (pageBean.getCurrentPage() - 1) * ROWS_ONE_PAGE;

        int orderType;
        if (ORDER_BY_CONTENT_TYPE == order) {
            orderType = ReportDaoJdbcImpl.ORDER_BY_CONTENT_TYPE;
        } else if (ORDER_BY_CREATED == order) {
            orderType = ReportDaoJdbcImpl.ORDER_BY_CREATED;
        } else {
            return null;
        }

        List<ReportPo> reports = reportDao.selectReports(startIndex, ROWS_ONE_PAGE, orderType, isReverse);
        pageBean.setList(reports);
        return pageBean;
    }
}
