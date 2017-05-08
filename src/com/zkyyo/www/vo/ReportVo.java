package com.zkyyo.www.vo;

public class ReportVo {
    private int reportId;
    private int userId;
    private String username; //vo
    private int contentId;
    private int contentType; // 0举报发言, 1举报分享图片, 3举报分享文件
    private String contentTypeStr; //vo
    private String reason;

    public ReportVo() {

    }

    public ReportVo(int reportId, int userId, String username, int contentId, int contentType, String contentTypeStr, String reason) {
        this.reportId = reportId;
        this.userId = userId;
        this.username = username;
        this.contentId = contentId;
        this.contentType = contentType;
        this.contentTypeStr = contentTypeStr;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getContentTypeStr() {
        return contentTypeStr;
    }

    public void setContentTypeStr(String contentTypeStr) {
        this.contentTypeStr = contentTypeStr;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ReportVo{" +
                "reportId=" + reportId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", contentId=" + contentId +
                ", contentType=" + contentType +
                ", contentTypeStr='" + contentTypeStr + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
