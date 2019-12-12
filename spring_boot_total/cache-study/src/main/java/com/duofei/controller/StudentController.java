package com.duofei.controller;

import com.duofei.entity.Student;
import com.duofei.entity.Teacher;
import com.duofei.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author duofei
 * @date 2019/12/12
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentById/{id}")
    public Student getStudentById(@PathVariable String id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/getStudentByTeacherName/{name}")
    public List<Student> getStudentByTeacherName(@PathVariable String name){
        Teacher teacher = new Teacher();
        teacher.setName(name);
        return studentService.getStudentByTeacher(teacher);
    }
}
