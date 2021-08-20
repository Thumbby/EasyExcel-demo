package com.thumbby.easyexcel.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.thumbby.easyexcel.entity.Student;
import com.thumbby.easyexcel.web.listener.WebStudentListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: Thumbby
 * @description:学生Controller
 * @date: 2021-08-20 14:40
 **/
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    WebStudentListener webStudentListener;

    @RequestMapping("read")
    @ResponseBody
    public String readExcel(MultipartFile uploadExcel){

        try{

            //获取工作簿
            ExcelReaderBuilder workBook = EasyExcel.read(uploadExcel.getInputStream(), Student.class, webStudentListener);

            //获取工作表
            workBook.sheet().doRead();

            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
