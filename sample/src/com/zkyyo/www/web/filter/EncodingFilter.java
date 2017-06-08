package com.zkyyo.www.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 该过滤器用于过滤请求的编码设置
 */
@WebFilter(
        filterName = "EncodingFilter",
        urlPatterns = {"/*"}
)
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        //判断请求方法
        if ("GET".equals(request.getMethod())) {
//            chrome浏览器下默认按UTF-8编码
//            request = new EncodingWrapper(request, "UTF-8");
        } else {
            request.setCharacterEncoding("UTF-8");
        }
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
