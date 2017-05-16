package com.zkyyo.www.web.filter;

import com.zkyyo.www.web.Access;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = {"/admin.jsp"}
)
public class AdminFilter extends AbstractAccessFilter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        super.doFilter(req, resp, chain);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    protected boolean checkAccess(Access access) {
        System.out.println("(AdminFilter) checkAccess() invoked");
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
