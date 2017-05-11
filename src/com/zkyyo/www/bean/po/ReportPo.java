package com.zkyyo.www.bean.po;

import java.io.Serializable;
import java.util.Objects;

public class ReportPo implements Serializable {
    private int reportId;
    private int userId;
    private int contentId;
    private int contentType; // 0举报发言, 1举报分享图片, 3举报分享文件
    private String reason;

    public ReportPo() {

    }

    public ReportPo(int reportId, int userId, int contentId, int contentType, String reason) {
        this.reportId = reportId;
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
        this.reason = reason;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!(obj instanceof ReportPo)) {
            return false;
        }
        ReportPo other = (ReportPo) obj;
        return reportId == other.getReportId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }

    @Override
    public String toString() {
        return "ReportPo{" +
                "reportId=" + reportId +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", contentType=" + contentType +
                ", reason='" + reason + '\'' +
                '}';
    }
}