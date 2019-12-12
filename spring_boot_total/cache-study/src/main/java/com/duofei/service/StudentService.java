package com.duofei.service;

import com.duofei.entity.Student;
import com.duofei.entity.Teacher;

import java.util.List;

/**
 * @author duofei
 * @date 2019/12/12
 */
public interface StudentService {

    Student getStudentById(String id);

    List<Student> getStudentByTeacher(Teacher teacher);
}
