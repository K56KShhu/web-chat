package com.zkyyo.www.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(
        filterName = "EncodingFilter",
        urlPatterns = {"/*"}
)
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if ("GET".equals(request.getMethod())) {
            request = new EncodingWrapper(request, "UTF-8");
        } else {
            request.setCharacterEncoding("UTF-8");
        }
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
