package com.zkyyo.www.web.filter;

import com.zkyyo.www.web.Access;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        filterName = "AdminAccessFilter",
        urlPatterns = {
                //管理员主页
                "/admin_index.jsp",
                //小组操作
                "/group_add.jsp",
                "/group_detail.jsp",
                "/group_find_user.jsp",
                "/group_manage.jsp",
                //举报操作
                "/report_manage.jsp",
                //讨论区操作
                "/topic_add.jsp",
                "/topic_detail.jsp",
                "/topic_find_group.jsp",
                "/topic_manage.jsp",
                "/topic_update.jsp",
                //用户操作
                "/user_detail_admin.jsp",
                "/user_manage.jsp",
                //管理员操作
                "/admin_manage.jsp"
        },
        servletNames = {
                //文件操作
                "FileDeleteServlet",
                //小组操作
                "GroupAddServlet",
                "GroupAddUserServlet",
                "GroupDeleteServlet",
                "GroupDetailServlet",
                "GroupFindUserServlet",
                "GroupManageInfoServlet",
                "GroupRemoveTopicServlet",
                "GroupRemoveUserServlet",
                //回复操作
                "ReplyDeleteServlet",
                //举报操作
                "ReportContentDeleteServlet",
                "ReportDeleteServlet",
                "ReportDetailServlet",
                "ReportManageInfoServlet",
                //讨论区操作
                "TopicAddGroupServlet",
                "TopicAddServlet",
                "TopicDeleteServlet",
                "TopicDetailServlet",
                "TopicFindGroupServlet",
                "TopicManageInfoServlet",
                "TopicUpdateInfoServlet",
                "TopicUpdateServlet",
                //用户操作
                "UserAuditServlet",
                "UserManageInfoServlet"
        }
)
public class AdminAccessFilter extends GeneralAccessFilter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        super.doFilter(req, resp, chain);
    }

    protected boolean checkAccess(Access access) {
        //判断账号状态
        if (access.isNormal()) {
            //判断用户角色
            if (access.isUserInRole("admin")) {
                return true;
            }
        }
        return false;
    }
}
