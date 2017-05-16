package com.zkyyo.www.web.filter;

import com.zkyyo.www.service.RememberService;
import com.zkyyo.www.service.UserService;
import com.zkyyo.www.util.CookieUtil;
import com.zkyyo.www.web.Access;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "LoginFilter",
        urlPatterns = {
                "/index.jsp"
        }
)
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Access access = (Access) request.getSession().getAttribute("access");
        System.out.println("LoginFilter: " + access);

        if (access != null && access.isNormal()) {
            if (access.isUserInRole("user")) {
                chain.doFilter(req, resp);
            } else if (access.isUserInRole("admin")) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
        */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        Access access = (Access) request.getSession().getAttribute("access");
        System.out.println("(LoginFilter) access: " + access);
        boolean isApproved = false;
        //保证Session中存在包含用户权限信息的access
        //检查当前浏览器会话中是否存在Session
        if (access == null) {
            String uuid = CookieUtil.getCookieValue(request, "user");
            System.out.println("(LoginFilter) uuid: " + uuid);
            //检查浏览器中是否存在cookie
            if (uuid != null) {
                //若存在, 尝试通过Cookie的uuid获取用户名, 从而获取用户权限信息
                UserService userService = (UserService) request.getServletContext().getAttribute("userService");
                RememberService rememberService = (RememberService) request.getServletContext().getAttribute("rememberService");
                String username = rememberService.find(uuid);
                access = userService.getAccess(username);
                isApproved = checkAccess(access);
                if (isApproved) {
                    request.getSession().setAttribute("access", access);
                    CookieUtil.addCookie(response, "user", uuid, 10 * 60); //更新Cookie最大时间
                }
            } else { //Cookie不存在
                CookieUtil.removeCookie(response, "user");
            }
        } else {
            isApproved = true;
        }

        if (isApproved) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private boolean checkAccess(Access access) {
        //判断账号状态
        if (access.isNormal()) {
            //判断用户角色
            if (access.isUserInRole("user")) {
                return true;
            } else if (access.isUserInRole("admin")) {
                return true;
            }
        }
        return false;
    }
}
