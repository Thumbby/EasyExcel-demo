package com.thumbby.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * @author: Thumbby
 * @description: 学生实体类
 * @date: 2021-08-20 10:58
 **/
@Data
//@ContentRowHeight() 内容行高
public class Student {

    //学生id
    @ExcelProperty(value = {"学生信息表", "学生id"}, index = 0)
    private String id;

    //学生姓名
    @ExcelProperty(value = {"学生信息表", "学生姓名"})
    private String name;

    //学生性别
    @ExcelProperty(value = {"学生信息表", "学生性别"})
    private String gender;

    //学生出生日期
    @ExcelProperty(value = {"学生信息表", "出生日期"})
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    private Date birth;

}
