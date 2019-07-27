package com.duofei.database;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duofei
 * @date 2019/6/29
 */
public class Test {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }

}
