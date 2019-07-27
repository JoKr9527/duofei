package com.duofei.function.test;

import com.duofei.function.basic.MoreMethods;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 函数式接口声明测试
 * @author duofei
 * @date 2019/7/8
 */
public class FunctionInterfaceTest {

    public static void main(String[] args) {

        // a,编译无法通过，在MoreMethods 找到多个非覆盖的抽象方法
        // MoreMethods m = ()->{};

        // 测试b，c，d, e 时，发现当重写Object的公共的非原生方法，该接口依然是函数式接口
        MoreMethods m = ArrayList::new;
        MoreMethods m2 = Arrays::asList;
        m.getOne();



    }
}
