package com.duofei.suger;

/**
 * @author duofei
 * @date 2020/7/13
 */
public class SugarTest {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Integer h = 127;
        Integer j = 127;
        Integer i = 128;
        Integer k = 128;
        Long g = 3L;
        System.out.println(c == d); //true
        System.out.println(h == j); //true 常量池的缘故（-128,127）
        System.out.println(i == k); //false
        System.out.println(e == f); // false
        System.out.println(c == (a + b)); //true 只有在遇到算术运算符才会自动拆箱
        System.out.println(c.equals(a + b)); //true
        System.out.println(g == (a + b)); //true
        System.out.println(g.equals(a + b)); //false
    }
}
