package com.zkyyo.www.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    public static boolean isValidId(String id, int maxLength) {
        if (id == null) {
            return false;
        }
        String regex = String.format("^\\d{1,%d}$", maxLength);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }

    public static boolean isValidString(String s, int minLength, int maxLength) {
        return s != null && s.length() >= minLength && s.length() <= maxLength;
    }

    public static void main(String[] args) {
        String s = "12345678901";
        System.out.println(isValidString(s, 0, 10));
    }
}
