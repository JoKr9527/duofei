package com.duofei;

import java.util.Scanner;

/**
 * @author duofei
 * @date 2020/6/5
 */
public class Test {
    public static void main(String[] args) {
        String a = "1";
        String b = null;
        if(a1(a) || b1(b) && b2(b)){
            System.out.println("end");
        }
    }

    public static boolean a1(String a1){
        System.out.println("a1");
        return a1.equals("");
    }

    public static boolean b1(String b1){
        System.out.println("b1");
        return b1 != null;
    }

    public static boolean b2(String b2){
        System.out.println("b2");
        return b2.equals("");
    }
}
