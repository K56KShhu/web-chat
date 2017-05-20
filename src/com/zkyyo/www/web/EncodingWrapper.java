package com.zkyyo.www.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

/**
 * 字符编码包装类, 用于重写请求方法
 */
public class EncodingWrapper extends HttpServletRequestWrapper{
    /**
     * 编码格式
     */
    private String encoding;

    /**
     * 构建对象
     * @param request 请求方法
     * @param encoding 编码格式
     */
    public EncodingWrapper(HttpServletRequest request, String encoding) {
        super(request);
        this.encoding = encoding;
    }

    /**
     * 重写请求方法, 将ISO-8859-1编码设置的请求参数转换为指定编码
     * @param name 请求参数
     * @return 转换为指定编码后的字符串
     */
    @Override
    public String getParameter(String name) {
        String value = getRequest().getParameter(name);
        if (value != null) {
            try {
                byte[] bytes = value.getBytes("ISO-8859-1");
                value = new String(bytes, encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
