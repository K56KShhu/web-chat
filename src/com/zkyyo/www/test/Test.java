package com.zkyyo.www.test;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int num = Math.random() > 0.5 ? 1 : 0;
            System.out.println(num);
        }
    }
}
