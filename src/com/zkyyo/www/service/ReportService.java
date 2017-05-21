package com.zkyyo.www.service;

import com.zkyyo.www.bean.PageBean;
import com.zkyyo.www.bean.po.ReportPo;
import com.zkyyo.www.dao.ReportDao;
import com.zkyyo.www.dao.impl.ReportDaoJdbcImpl;
import com.zkyyo.www.util.CheckUtil;

import java.util.List;

/**
 * 提供与举报信息相关, 用于逻辑处理的代码
 * 该类包含增删查方法和校验方法
 */
public class ReportService {
    /**
     * 排序标识符, 排序依据为举报类型
     */
    public static final int ORDER_BY_CONTENT_TYPE = 0;
    /**
     * 排序标识符, 排序依据为举报时间
     */
    public static final int ORDER_BY_CREATED = 1;

    /**
     * 分页系统, 每一页显示的行数
     */
    private static final int ROWS_ONE_PAGE = 15;

    /**
     * 举报唯一标识ID的最大长度
     */
    private static final int MAX_ID_LENGTH = 10;
    /**
     * 举报原因的最大长度
     */
    private static final int MAX_REASON_LENGTH = 100;
    /**
     * 举报原因的最小长度
     */
    private static final int MIN_REASON_LENGTH = 0;

    /**
     * 数据库操作相关接口
     */
    private ReportDao reportDao;

    /**
     * 构建对象
     *
     * @param reportDao 传入的数据库操作接口
     */
    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    /**
     * 校验举报ID是否合法
     *
     * @param reportId 待校验的举报ID
     * @return true合法, false不合法
     */
    public boolean isValidId(String reportId) {
        return CheckUtil.isValidInteger(CheckUtil.NUMBER_POSITIVE, reportId, MAX_ID_LENGTH);
    }

    /**
     * 校验举报原因是否合法
     *
     * @param reason 待校验的举报原因
     * @return true合法, false非法
     */
    public boolean isValidReason(String reason) {
        return CheckUtil.isValidString(reason, MIN_REASON_LENGTH, MAX_REASON_LENGTH);
    }

    /**
     * 校验举报是否存在
     *
     * @param reportId 待校验的举报ID
     * @return true存在, false不存在
     */
    public boolean isExisted(int reportId) {
        return reportDao.selectReport(reportId) != null;
    }

    /**
     * 添加举报信息
     *
     * @param reportPo 待添加的举报对象
     */
    public void addReport(ReportPo reportPo) {
        reportDao.addReport(reportPo);
    }

    /**
     * 删除举报信息
     *
     * @param reportId 待删除的举报ID
     */
    public void deleteReport(int reportId) {
        reportDao.deleteReport(reportId);
    }

    /**
     * 查询所有举报信息, 同时进行分页和排序
     *
     * @param currentPage 当前页数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 封装的分页对象, 信息输入不合法时返回null
     */
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

        List<ReportPo> reports = reportDao.selectReportsByOrder(startIndex, ROWS_ONE_PAGE, orderType, isReverse);
        pageBean.setList(reports);
        return pageBean;
    }
}
