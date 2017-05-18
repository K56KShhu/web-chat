package com.zkyyo.www.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "TestFilter",
//        urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "PATH_IGNORED", value = "/temp/login.jsp")
        }
)
public class TestFilter implements Filter {
    private String pathIgnored;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI();
        if (path.startsWith(pathIgnored)) {
            System.out.println("[TestFilter] ignored!");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        pathIgnored = config.getInitParameter("PATH_IGNORED");
    }

}
