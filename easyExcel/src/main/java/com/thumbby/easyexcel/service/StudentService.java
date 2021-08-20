package com.thumbby.easyexcel.service;

import com.thumbby.easyexcel.entity.Student;

import java.util.List;

/**
 * @author: Thumbby
 * @description: 学生Service
 * @date: 2021-08-20 14:41
 **/
public interface StudentService {
    void readExcel(List<Student> students);
}
