package com.thumbby.easyexcel.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.thumbby.easyexcel.entity.Student;
import com.thumbby.easyexcel.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author: Thumbby
 * @description:
 * @date: 2021-08-20 15:01
 **/
@Component
@Scope("prototype")
public class WebStudentListener extends AnalysisEventListener<Student> {

    @Autowired
    StudentService studentService;

    ArrayList<Student> students = new ArrayList<Student>();

    public void invoke(Student student, AnalysisContext context){
        students.add(student);
        if(students.size()%5 == 0){
            studentService.readExcel(students);
            students.clear();
        }
    }

    public void doAfterAllAnalysed(AnalysisContext context){

    }
}
