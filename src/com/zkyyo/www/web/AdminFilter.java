package com.zkyyo.www.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = {"/admin.jsp"}
)
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Access access = (Access) request.getSession().getAttribute("access");
        System.out.println(access);
        if (access != null && access.isUserInRole("admin")) {
            chain.doFilter(req, resp);
        } else {
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendRedirect("index.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
