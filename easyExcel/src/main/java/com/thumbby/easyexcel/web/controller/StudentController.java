package com.thumbby.easyexcel.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.thumbby.easyexcel.dto.EasyExcelUtils;
import com.thumbby.easyexcel.entity.Student;
import com.thumbby.easyexcel.web.listener.WebStudentListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

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
            ExcelReaderBuilder readerWorkBook = EasyExcel.read(uploadExcel.getInputStream(), Student.class, webStudentListener);

            //获取工作表
            readerWorkBook.sheet().doRead();

            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    private static List<Student> initData(){
        ArrayList<Student> students = new ArrayList<Student>();
        Student data = new Student();
        for(int i=0; i<5;i++){
            data.setName("田所浩二");
            data.setGender("男");
            data.setId("114514");
            data.setBirth(new Date());
            students.add(data);
        }
        return students;
    }

    @RequestMapping("write")
    @ResponseBody
    public void writeExcel(HttpServletResponse response) throws  IOException{

        //设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        //防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();

        //获取工作簿
        ExcelWriterBuilder writerWorkBook = EasyExcel.write(outputStream, Student.class);

        //获取工作表
        ExcelWriterSheetBuilder writerSheet = writerWorkBook.sheet();

        List<Student> students = initData();

        writerSheet.doWrite(students);

    }

    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {

        String fileName = "测试用表3_学生.xlsx";

        //规定表头和对应字段名
        String[] titleNames = {"学号", "学生姓名", "学生性别", "学生出生日期", "学生照片"};
        String[] listKeys = {"id", "name", "gender", "birth", "profile"};

        //获取查询出的数据
        List<Map<String, Object>> students = new ArrayList<Map<String, Object>>();
        for(int i=0;i<5;i++){
            HashMap<String, Object> student = new HashMap<String, Object>();
            student.put("id", "114514");
            student.put("name", "田所浩二");
            student.put("gender", "男");
            student.put("birth", new Date());
            student.put("profile", new URL("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F68db50227a2044998526d6ab55f93e9f60dae645.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632376625&t=c6f93dabcf01320ed71482679be2278d"));
            students.add(student);
        }
        EasyExcelUtils.download(response, "学生信息", titleNames, listKeys, students);

    }
}
