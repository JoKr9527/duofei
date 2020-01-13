package com.duofei.beans;

/**
 * @author duofei
 * @date 2020/1/6
 */
public class TestB {

    private TestC testC;

    public TestB(){}

    public TestB(TestC testC){
        this.testC = testC;
    }

    public TestC getTestC() {
        return testC;
    }

    public void setTestC(TestC testC) {
        this.testC = testC;
    }
}
