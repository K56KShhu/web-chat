package com.zkyyo.www.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类封装一些通用验证方法
 */
public class CheckUtil {
    /**
     * 正数标识符
     */
    public static final int NUMBER_POSITIVE = 1;
    /**
     * 有理数标识符, 包括0
     */
    public static final int NUMBER_ALL = 0;
    /**
     * 负数标识符
     */
    public static final int NUMBER_NEGATIVE = -1;

    /**
     * 正则表达式验证输入的字符串是否为整数, 且满足特定符号和长度
     * @param sign 符号, 参考该类的NUMBER_POSITIVE, NUMBER_ALL, NUMBER_NEGATIVE
     * @param s 待验证的字符串
     * @param maxLength 最大长度
     * @return true满足, false不满足
     */
    public static boolean isValidInteger(int sign, String s, int maxLength) {
        if (s == null) {
            return false;
        }
        String regex;
        if (NUMBER_ALL == sign) {
            regex = String.format("^-?\\d{1,%d}$", maxLength);
        } else if (NUMBER_POSITIVE == sign) {
            regex = String.format("^\\d{1,%d}$", maxLength);
        } else if (NUMBER_NEGATIVE == sign) {
            regex = String.format("^-\\d{1,%d}$", maxLength);
        } else {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 验证字符串长度是否合法
     *
     * @param s         待验证的字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return true合法, false不合法
     */
    public static boolean isValidString(String s, int minLength, int maxLength) {
        return s != null && s.length() >= minLength && s.length() <= maxLength;
    }

    public static void main(String[] args) {
        System.out.println(isValidInteger(CheckUtil.NUMBER_ALL, "0", 4));
    }
}
