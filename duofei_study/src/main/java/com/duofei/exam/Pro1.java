package com.duofei.exam;

import java.util.Scanner;
import java.util.function.BiFunction;

/**
 * 小明最近在做病毒自动检测，他发现，在某些library 的代码段的二进制表示中，如果包含子串并且恰好有k个1，就有可能有潜在的病毒。library的二进制表示可能很大，并且子串可能很多，人工分析不可能，于是他想写个程序来先算算到底有多少个子串满足条件。如果子串内容相同，但是开始或者结束位置不一样，则被认为是不同的子串。
 * 注：子串一定是连续的。例如"010"有6个子串，分别是 "0, "1", "0", "01", "10", "010"
 *
 * @author duofei
 * @date 2020/3/27
 */
public class Pro1 {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(new Integer(9).equals(9));
    }
}
