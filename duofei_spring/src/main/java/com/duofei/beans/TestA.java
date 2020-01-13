package com.duofei.beans;

/**
 * @author duofei
 * @date 2020/1/6
 */
public class TestA {

    private TestB testB;

    public TestA() {
    }

    public TestA(TestB testB){
        this.testB = testB;
    }

    public TestB getTestB() {
        return testB;
    }

    public void setTestB(TestB testB) {
        this.testB = testB;
    }
}
