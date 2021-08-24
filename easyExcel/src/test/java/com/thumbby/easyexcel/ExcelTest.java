package com.thumbby.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.sun.scenario.effect.ImageData;
import com.thumbby.easyexcel.dto.EasyExcelUtils;
import com.thumbby.easyexcel.entity.FillData;
import com.thumbby.easyexcel.entity.Student;
import com.thumbby.easyexcel.listener.StudentListener;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @author: Thumbby
 * @description: 测试用表1_学生_测试
 * @date: 2021-08-20 11:01
 **/
public class ExcelTest {

    /**
     * 导出图片(使用实体对象)
     */
    @Test
    public void imageWrite() throws Exception{
        String fileName = "测试用表2_学生.xlsx";
        InputStream inputStream = null;
        try {
            List<Student> list = new ArrayList<Student>();
            Student student = new Student();
            list.add(student);
            student.setId("114514");
            student.setGender("男");
            student.setBirth(new Date());
            student.setName("田所浩二");
            //使用url插入图片,图片来自网络(确信
            student.setProfile(new URL("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F68db50227a2044998526d6ab55f93e9f60dae645.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632376625&t=c6f93dabcf01320ed71482679be2278d"));
            EasyExcel.write(fileName, Student.class).sheet().doWrite(list);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 导出图片(直接写)
     */
    @Test
    public void exportImage() throws Exception{
        String fileName = "测试用表3_学生.xlsx";
        InputStream inputStream = null;
        try {
            //规定表头和对应字段名
            String[] titleNames = {"学号", "学生姓名", "学生性别", "学生出生日期", "学生照片"};
            String[] listKeys = {"id", "name", "gender", "birth", "profile"};
            List<Map<String, Object>> students = new ArrayList<Map<String, Object>>();
            HashMap<String, Object> student = new HashMap<String, Object>();
            student.put("id", "114514");
            student.put("name", "田所浩二");
            student.put("gender", "男");
            student.put("birth", new Date());
            student.put("profile", new URL("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F68db50227a2044998526d6ab55f93e9f60dae645.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632376625&t=c6f93dabcf01320ed71482679be2278d"));
            students.add(student);
            EasyExcel.write(fileName).head(EasyExcelUtils.head(titleNames)).sheet().doWrite(EasyExcelUtils.dataList(students, listKeys));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 基础读Excel
     */
    @Test
    public void test01(){

        //获得一个工作簿
        ExcelReaderBuilder workbook = EasyExcel.read("测试用表1_学生.xlsx", Student.class, new StudentListener());

        //获得一个工作表
        ExcelReaderSheetBuilder sheet = workbook.sheet();

        //获取工作表内容
        sheet.doRead();
    }

    /**
     * 导出单实体
     */
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
            data.setName("田所浩二");
            data.setGender("男");
            data.setId("114514");
            data.setBirth(new Date());
            students.add(data);
        }
        return students;
    }

    /**
     * 模板填充测试(单组数据)
     */
    @Test
    public void test03(){

        //准备数据模板
        String template = "fill_data_template1.xlsx";

        //创建工作簿对象
        ExcelWriterBuilder writerBook = EasyExcel.write("单组数据填充.xlsx", FillData.class).withTemplate(template);

        //创建工作表对象
        ExcelWriterSheetBuilder sheet = writerBook.sheet();

        //用实体类准备数据
        /*FillData fillData = new FillData();
        fillData.setId("114514");
        fillData.setName("田所浩二");
        fillData.setAge(24);*/

        //准备map类型数据
        HashMap<String, String> fillData =new HashMap<String, String>();
        fillData.put("id","114514");
        fillData.put("name","田所浩二");
        fillData.put("age","24");

        //填充数据
        sheet.doFill(fillData);
    }

    /**
     * 生成多组数据用于填充
     */
    private static List<FillData> initFillData(){
        ArrayList<FillData> fillData = new ArrayList<FillData>();
        FillData data = new FillData();
        for(int i=0; i<5;i++){
            data.setName("田所浩二");
            data.setId("114514");
            data.setAge(24);
            fillData.add(data);
        }
        return fillData;
    }

    /**
     * 模板填充测试(多组数据)
     */
    @Test
    public void test04(){

        //准备模板
        String template = "fill_data_template2.xlsx";

        //创建工作簿对象
        ExcelWriterBuilder writerBook = EasyExcel.write("多组数据填充.xlsx", FillData.class).withTemplate(template);

        //创建工作表
        ExcelWriterSheetBuilder sheet = writerBook.sheet();

        List<FillData> fillData = initFillData();

        //这种do方法会在读写之后自动关闭流
        sheet.doFill(fillData);
    }

    /**
     * 模板填充测试(组合填充)
     */
    @Test
    public void test05() {

        //准备模板
        String template = "fill_data_template3.xlsx";

        //创建工作簿对象
        ExcelWriter writerBook = EasyExcel.write("组合数据填充.xlsx", FillData.class).withTemplate(template).build();

        //创建工作表
        WriteSheet sheet =EasyExcel.writerSheet().build();

        //换行,因为多组填充数据量不确定
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();

        //准备数据
        List<FillData> fillData = initFillData();

        HashMap<String, String> dateAndTotal = new HashMap<String, String>();
        dateAndTotal.put("date", "2114-5-14");
        dateAndTotal.put("total", "114514");

        //多组填充
        writerBook.fill(fillData, fillConfig, sheet);

        //单组填充
        writerBook.fill(dateAndTotal, sheet);

        //手动关闭流(因为没有使用doFill)
        writerBook.finish();

    }

    /**
     * 数据水平填充
     */
    @Test
    public void test06() {

        //准备模板
        String template = "fill_data_template4.xlsx";

        //创建工作簿对象
        ExcelWriter writerBook = EasyExcel.write("水平数据填充.xlsx", FillData.class).withTemplate(template).build();


        //创建工作表
        WriteSheet sheet =EasyExcel.writerSheet().build();

        //换行,因为多组填充数据量不确定
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();

        //准备数据
        List<FillData> fillData = initFillData();

        //多组填充
        writerBook.fill(fillData, fillConfig, sheet);

        //手动关闭流(因为没有使用doFill)
        writerBook.finish();

    }
}
