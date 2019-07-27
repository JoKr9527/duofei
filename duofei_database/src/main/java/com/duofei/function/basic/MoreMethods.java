package com.duofei.function.basic;

import java.util.List;

/**
 * 多个方法的接口
 * @author duofei
 * @date 2019/7/8
 */
@FunctionalInterface
public interface MoreMethods{

    // a
    // void getOne();
    List getOne();

    /** b
     * 来自Object 的public方法
     * @author hxf
     * @date 2019/7/8 19:49
     * @param 
     * @return 
     * @throws 
     */
    @Override
    boolean equals(Object obj);

    /**
     * c : 测试时，无法将该类标记为 FunctionalInterface
     * 来自Object 的protected方法
     * @author hxf
     * @date 2019/7/8 19:51
     * @param
     * @return
     * @throws
     */
     // Object clone() throws CloneNotSupportedException;

    /**
     * d : 来自Object 的public方法
     * @author hxf
     * @date 2019/7/8 19:53
     * @param
     * @return
     * @throws
     */
    @Override
    String toString();

    /**
     * e: 无法将该接口标记为FunctionalInterface
     * @author hxf
     * @date 2019/7/8 19:54
     * @param
     * @return
     * @throws
     */
    //void finalize() throws Throwable;
}
