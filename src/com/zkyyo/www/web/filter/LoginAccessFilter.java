package com.zkyyo.www.web.filter;

import com.zkyyo.www.web.Access;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 该过滤器用于权限验证, 只有权限为user, admin, root可以通过
 */
@WebFilter(
        filterName = "LoginAccessFilter",
        urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "PATH_LOGIN_FORM", value = "/temp/login.jsp"),
                @WebInitParam(name = "PATH_LOGIN_PROCESS", value = "/temp/login.do"),
                @WebInitParam(name = "PATH_REGISTER_FORM", value = "/temp/register.jsp"),
                @WebInitParam(name = "PATH_REGISTER_PROCESS", value = "/temp/register.do")
        }
)
public class LoginAccessFilter extends GeneralAccessFilter {
    private String pathLoginForm;
    private String pathLoginProcess;
    private String pathRegisterForm;
    private String pathRegisterProcess;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String path = ((HttpServletRequest) req).getRequestURI();
        if (path.startsWith(pathLoginForm)
                || path.startsWith(pathLoginProcess)
                || path.startsWith(pathRegisterForm)
                || path.startsWith(pathRegisterProcess)) {
            chain.doFilter(req, resp);
        } else {
            super.doFilter(req, resp, chain);
        }
    }

    protected boolean checkAccess(Access access) {
        //判断账号状态
        if (access.isNormal()) {
            //判断用户角色
            if (access.isUserInRole("user")) {
                return true;
            } else if (access.isUserInRole("admin")) {
                return true;
            } else if (access.isUserInRole("root")) {
                return true;
            }
        }
        return false;
    }

    public void init(FilterConfig config) throws ServletException {
        pathLoginForm = config.getInitParameter("PATH_LOGIN_FORM");
        pathLoginProcess = config.getInitParameter("PATH_LOGIN_PROCESS");
        pathRegisterForm = config.getInitParameter("PATH_REGISTER_FORM");
        pathRegisterProcess = config.getInitParameter("PATH_REGISTER_PROCESS");
        super.init(config);
    }
}
