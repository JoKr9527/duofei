package com.duofei.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流式的源码研究
 * @author duofei
 * @date 2019/9/3
 */
public class StreamCodeStu {

    public static void main(String[] args) {
        List<Integer> testData = Stream.of(1, 2, 3).collect(Collectors.toList());
        testData.stream().forEach(i->{
            System.out.println(i);
        });
    }
}
