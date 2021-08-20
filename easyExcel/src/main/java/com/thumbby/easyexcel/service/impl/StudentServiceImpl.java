package com.thumbby.easyexcel.service.impl;

import com.thumbby.easyexcel.entity.Student;
import com.thumbby.easyexcel.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Thumbby
 * @description: 学生Service实现
 * @date: 2021-08-20 14:47
 **/
@Service
public class StudentServiceImpl implements StudentService {
    public void readExcel(List<Student> students) {
        for (Student student : students) {
            System.out.println("student=" + student);
        }
    }
}
