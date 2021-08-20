package com.thumbby.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.thumbby.easyexcel.entity.Student;
import com.thumbby.easyexcel.listener.StudentListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Thumbby
 * @description: 测试用表1_学生_测试
 * @date: 2021-08-20 11:01
 **/
public class ExcelTest {

    @Test
    public void test01(){

        //获得一个工作簿
        ExcelReaderBuilder workbook = EasyExcel.read("测试用表1_学生.xlsx", Student.class, new StudentListener());

        //获得一个工作表
        ExcelReaderSheetBuilder sheet = workbook.sheet();

        //获取工作表内容
        sheet.doRead();
    }

    //导出单实体
    @Test
    public void test02(){

        //获得一个工作簿
        ExcelWriterBuilder workbook = EasyExcel.write("测试用表1_学生.xlsx", Student.class);

        //工作表
        ExcelWriterSheetBuilder sheet = workbook.sheet();

        List<Student>students = initData();
        //执行写操作
        sheet.doWrite(students);

    }

    private static List<Student> initData(){
        ArrayList<Student> students = new ArrayList<Student>();
        Student data = new Student();
        for(int i=0; i<5;i++){
            data.setName("空手道部员");
            data.setGender("男");
            data.setId("114514");
            data.setBirth(new Date());
            students.add(data);
        }
        return students;
    }
}
