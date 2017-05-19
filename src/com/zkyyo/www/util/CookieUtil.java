package com.zkyyo.www.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该类封装了Cookie的操作方法
 */
public class CookieUtil {
    /**
     * 获取客户端请求中指定的Cookie Value
     * @param request 客户端请求
     * @param name 指定的Cookie Name
     * @return 存在返回Cookie Name, 否则返回null
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 在响应中添加定制的Cookie
     * @param response 服务器响应
     * @param name Cookie Name
     * @param value Cookie Value
     * @param maxAge Cookie在客户端的最大存活时间
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 移除客户端中指定的Cookie
     * @param response 服务器响应
     * @param name 待移除的Cookie Name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        addCookie(response, name, null, 0);
    }
}
