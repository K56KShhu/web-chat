package com.zkyyo.www.dao;

import com.zkyyo.www.bean.po.ReportPo;

import java.util.List;

/**
 * 定义与举报相关, 涉及数据库操作的接口
 */
public interface ReportDao {
    /**
     * 向数据库中插入举报信息
     *
     * @param reportPo 待插入的举报对象
     */
    void addReport(ReportPo reportPo);

    /**
     * 删除数据库中指定举报ID的举报信息
     *
     * @param reportId 待删除的举报ID
     */
    void deleteReport(int reportId);

    /**
     * 获取数据库中指定举报ID的举报信息
     *
     * @param reportId 待获取的举报ID
     * @return 存在返回举报对象, 否则返回null
     */
    ReportPo selectReport(int reportId);

    /**
     * 获取数据库中的多个举报信息, 同时进行分页和排序
     *
     * @param startIndex  起始下标
     * @param rowsOnePage 获取的举报总数
     * @param order       排序依据
     * @param isReverse   是否降序
     * @return 包含举报信息的列表, 不包含任何举报信息则返回一个size为0的列表
     */
    List<ReportPo> selectReportsByOrder(int startIndex, int rowsOnePage, int order, boolean isReverse);

    /**
     * 获取数据库中的所有举报信息的总行数
     *
     * @return 所有举报信息的总行数
     */
    int getTotalRow();
}
