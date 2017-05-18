package com.zkyyo.www.web.filter;

import com.zkyyo.www.service.RememberService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.CookieUtil;
import com.zkyyo.www.web.Access;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "GeneralAccessFilter")
public class GeneralAccessFilter implements Filter {
    private String LOGIN_COOKIE_NAME;
    private int STAY_LOGGED_TIME;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        Access access = (Access) request.getSession().getAttribute("access");
        System.out.println("(GeneralAccessFilter) access: " + access);
        boolean isApproved = false;
        //保证Session中存在包含用户权限信息的access
        //检查当前浏览器会话中是否存在Session
        if (access == null) {
            String uuid = CookieUtil.getCookieValue(request, LOGIN_COOKIE_NAME);
            System.out.println("(GeneralAccessFilter) uuid: " + uuid);
            //检查浏览器中是否存在cookie
            if (uuid != null) {
                //若存在,101 尝试通过Cookie的uuid获取用户名, 从而获取用户权限信息
                UserService userService = (UserService) request.getServletContext().getAttribute("userService");
                RememberService rememberService = (RememberService) request.getServletContext().getAttribute("rememberService");
                String username = rememberService.find(uuid);
                if (username != null) {
                    access = userService.getAccess(username);
                    //校验角色权限
                    isApproved = this.checkAccess(access);
                    if (isApproved) {
                        request.getSession().setAttribute("access", access);
                        CookieUtil.addCookie(response, LOGIN_COOKIE_NAME, uuid, STAY_LOGGED_TIME); //更新Cookie最大时间
                    }
                }
            } else { //Cookie不存在
                CookieUtil.removeCookie(response, LOGIN_COOKIE_NAME);
            }
        } else {
            //校验角色权限
            isApproved = this.checkAccess(access);
        }

        if (isApproved) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {
        LOGIN_COOKIE_NAME = (String) config.getServletContext().getAttribute("loginCookieName");
        STAY_LOGGED_TIME = (int) config.getServletContext().getAttribute("stayLoggedTime");
    }

    protected boolean checkAccess(Access access) {
        System.out.println("(GeneralAccessFilter) checkAccess() invoked");
        return false;
    }
}
