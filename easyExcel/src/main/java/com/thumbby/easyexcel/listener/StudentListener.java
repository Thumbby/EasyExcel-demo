package com.thumbby.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.thumbby.easyexcel.entity.Student;

/**
 * @author: Thumbby
 * @description: 学生表监听器
 * @date: 2021-08-20 11:06
 **/
public class StudentListener extends AnalysisEventListener<Student> {

    public void invoke(Student student, AnalysisContext context){
        System.out.println("student="+student);
    }

    public void doAfterAllAnalysed(AnalysisContext context){

    }
}
