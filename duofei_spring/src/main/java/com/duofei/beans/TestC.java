package com.duofei.beans;

/**
 * @author duofei
 * @date 2020/1/6
 */
public class TestC {

    private TestA testA;

    public TestC() {
    }

    public TestC(TestA testA){
        this.testA = testA;
    }

    public TestA getTestA() {
        return testA;
    }

    public void setTestA(TestA testA) {
        this.testA = testA;
    }
}
