package com.zkyyo.www.test;

public class ParamterTest {
    public static void main(String[] args) {
        foo("123", "ac", "23");
    }

    private static void foo(String abc, String...roles) {
        for (String role : roles) {
            System.out.println(role);
        }
    }

}
