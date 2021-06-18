package com.duofei;

import java.util.function.Function;

public class Student {

    private String name;

    public Student(String name){
        this.name = name;
        Function<String, String> function = nm -> nm;
    }

    public static Student newInstance(String name){
        return new Student(name);
    }

    public boolean legalName(String name){
        return name != null;
    }

    public String getName() {
        return name;
    }

    public Student getStudent(Student student){
        return this;
    }
}
