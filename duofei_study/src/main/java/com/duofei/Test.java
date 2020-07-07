package com.duofei;

import java.util.Scanner;

/**
 * @author duofei
 * @date 2020/6/5
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String next = scanner.next();
            System.out.println(":" + next);
            System.out.print("1:" + next);
        }
        scanner.close();
    }
}
