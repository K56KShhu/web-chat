package com.zkyyo.www.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类封装一些通用验证方法
 */
public class CheckUtil {
    /**
     * 正则表达式验证ID的合法性
     * @param id 待验证的ID
     * @param maxLength ID最大长度
     * @return true合法, false不合法
     */
    public static boolean isValidId(String id, int maxLength) {
        if (id == null) {
            return false;
        }
        String regex = String.format("^\\d{1,%d}$", maxLength);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }

    /**
     * 验证字符串长度是否合法
     * @param s 待验证的字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return true合法, false不合法
     */
    public static boolean isValidString(String s, int minLength, int maxLength) {
        return s != null && s.length() >= minLength && s.length() <= maxLength;
    }
}
