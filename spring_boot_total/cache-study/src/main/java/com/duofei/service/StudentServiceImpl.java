package com.duofei.service;

import com.duofei.Application;
import com.duofei.entity.Student;
import com.duofei.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duofei
 * @date 2019/12/12
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private ApplicationContext applicationContext;

    public static List<Student> container = new ArrayList<>();

    static {
        container.add(Student.builder().id("1").name("luobu").age(23).build());
        container.add(Student.builder().id("2").name("tudou").age(24).build());
        container.add(Student.builder().id("3").name("malingshu").age(25).build());
    }

    @Override
    @Cacheable("students")
    public Student getStudentById(String id){
        for (Student student : container) {
            if(student.getId().equals(id)){
                return student;
            }
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "studentsOfTeacher", keyGenerator = "keyGeneratorForTeacher")
    public List<Student> getStudentByTeacher(Teacher teacher){
        List<Student> findStudents = new ArrayList<>();
        for (Student student : container) {
            if(student.getName().equals(teacher.getName())){
                findStudents.add(student);
            }
        }
        return findStudents;
    }
}
