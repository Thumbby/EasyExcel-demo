package com.thumbby.easyexcel.dto;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Thumbby
 * @description: EasyExcel导出工具类
 * @date: 2021-08-24 15:36
 **/
public class EasyExcelUtils {
    /**
     * web浏览器写 自动列宽
     * 导出图片只支持单张，需要导出就在listkey的图片字段拼接showImg
     * @param response
     * @param fileName  文件名称
     * @param headArray 文件头
     * @param listKey   list key
     * @param dataList  数据list
     */
    public static void download(HttpServletResponse response, String fileName, String[] headArray, String[] listKey, List<Map<String, Object>> dataList) {
        try {
            EasyExcel.write(EasyExcelUtils.outputStream(response, fileName)).head(EasyExcelUtils.head(headArray))
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet()
                    .doWrite(EasyExcelUtils.dataList(dataList, listKey));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<List<String>> head(String[] array) {
        List<List<String>> list = new ArrayList<List<String>>();
        for (String s : array) {
            List<String> head = new ArrayList<String>();
            head.add(s);
            list.add(head);
        }
        return list;
    }

    public static List<List<Object>> dataList(List<Map<String, Object>> list, String[] listKey) throws MalformedURLException {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (Map<String, Object> map : list) {
            List<Object> data = new ArrayList<Object>();
            for (String s : listKey) {
                if (map.get(s) == null) {
                    data.add("");
                } else {
                    //数据格式处理,可以根据自己的需求进行格式化操作都放在这里
                    Object obj = map.get(s);
                    if(s.contains("profile")  && (obj.toString().contains("http") || obj.toString().contains("https"))){
                        data.add(new URL(obj.toString()));
                    }else {
                        data.add(obj.toString());
                    }
                }
            }
            dataList.add(data);
        }
        return dataList;
    }

    public static OutputStream outputStream(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        //防止中文乱码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }

}
