package com.zkyyo.www.web.filter;

import com.zkyyo.www.web.Access;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        filterName = "RootAccessFilter",
        urlPatterns = {
                "/admin_find.jsp"
        },
        servletNames = {
                "AdminAddServlet",
                "AdminDeleteServlet",
                "AdminFindServlet"
        }
)
public class RootAccessFilter extends GeneralAccessFilter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        super.doFilter(req, resp, chain);
    }

    protected boolean checkAccess(Access access) {
        //判断账号状态
        if (access.isNormal()) {
            //判断用户角色
            if (access.isUserInRole("root")) {
                return true;
            }
        }
        return false;
    }

}
