package com.duofei.entity;

/**
 * 学生信息
 * @author duofei
 * @date 2019/12/12
 */
public class Student {

    private String id;

    private String name;

    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Student student;

        private Builder(){
            this.student = new Student();
        }

        public Builder id(String id){
            this.student.id = id;
            return this;
        }

        public Builder name(String name){
            this.student.name = name;
            return this;
        }

        public Builder age(Integer age){
            this.student.age = age;
            return this;
        }

        public Student build(){
            return this.student;
        }
    }
}
