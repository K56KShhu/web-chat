package com.zkyyo.www.web;

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
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Access access = (Access) request.getSession().getAttribute("access");
        System.out.println("LoginFilter: " + access);

        if (access != null) {
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
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
