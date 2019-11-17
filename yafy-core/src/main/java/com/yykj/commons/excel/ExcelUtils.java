package com.yykj.commons.excel;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yykj.system.commons.CalendarUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *  @author: 王晓
 *  @Date: 2019/7/27 11:08
 *  @Description: excel操作的封装工具类类
 */
public class ExcelUtils {
    /**
     * @Description  excel导出通用关闭流
     * @Author 王晓
     * @Date 2019/7/27 11:13
     * @Param response
     * @Param rows
     * @Return void
     * @Exception
     */
    public static void ExcelPortClose(HttpServletResponse response,ExcelWriter writer, List<?> rows,String fileName) throws IOException {
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        //out为OutputStream，需要写出到的目标流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String headStr = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename="+headStr);
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     * 读取xlsx信息
     * @author chenbiao
     * @date 2018年8月27日 下午2:52:16
     * 参数说明
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static List<List<String>> readExcelByXlsx(InputStream input, int ingoreRows, int columnsLength)
            throws Exception {

        try {
            List<List<String>> columnNameList = new ArrayList<List<String>>();
            @SuppressWarnings("resource")
            XSSFWorkbook wb = new XSSFWorkbook(input);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            List<String> rowValues;
            XSSFCell cell;
            for (int j = (ingoreRows), m = sheet.getPhysicalNumberOfRows(); j < m; j++) { //遍历行
                row = sheet.getRow(j);
                if(row ==null){
                    return columnNameList;
                }
                rowValues = new LinkedList<String>();
                for(int i = 0 ; i < columnsLength ; i++) { //遍历每个单元格数据存放到内存中
                    cell = row.getCell(i);
                    if(null != cell) {
                        CellType cellType = cell.getCellTypeEnum();
                        if(CellType.STRING == cellType) {
                            rowValues.add(row.getCell(i).getStringCellValue());
                        }else if(CellType.NUMERIC == cellType) {
                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                                rowValues.add(CalendarUtils.dateToString(date, CalendarUtils.yyyy_MM_dd__HH_mm_ss));
                            }else {
                                cell.setCellType(CellType.STRING);
                                //DecimalFormat df = new DecimalFormat("0");
                                rowValues.add(row.getCell(i).getStringCellValue());
                            }
                        }else if(CellType.BOOLEAN == cellType) {
                            rowValues.add(String.valueOf(row.getCell(i).getBooleanCellValue()));
                        }else if(CellType.BLANK == cellType) {
                            rowValues.add("");
                        }else {
                            rowValues.add("");
                        }
                    }else {
                        rowValues.add("");
                    }

                }
                columnNameList.add(rowValues);
            }
            return columnNameList;
        } finally {
            input.close();
        }
    }
    /**
     * 读取xls数据
     * @author chenbiao
     * @date 2018年8月27日 下午6:01:14
     * 参数说明
     *
     * @param input
     * @param ingoreRows
     * @param columnsLength
     * @return
     * @throws Exception
     */
    public static List<List<String>> readExcelByXls(InputStream input,int ingoreRows,int columnsLength)
            throws Exception {

        try {
            List<List<String>> columnList = new ArrayList<List<String>>();
            @SuppressWarnings("resource")
            HSSFWorkbook wb = new HSSFWorkbook(input);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            List<String> rowValues;
            HSSFCell cell;
            for (int j = ingoreRows, m = sheet.getPhysicalNumberOfRows(); j < m; j++) { //遍历行
                row = sheet.getRow(j);
                rowValues = new LinkedList<String>();
                for(int i = 0 ; i < columnsLength ; i++) { //遍历每个单元格数据存放到内存中
                    cell = row.getCell(i);
                    if(null != cell) {
                        CellType cellType = cell.getCellTypeEnum();
                        if(CellType.STRING == cellType) {
                            rowValues.add(row.getCell(i).getStringCellValue());
                        }else if(CellType.NUMERIC == cellType) {
                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                                rowValues.add(CalendarUtils.dateToString(date, CalendarUtils.yyyy_MM_dd__HH_mm_ss));
                            }else {
                                DecimalFormat df = new DecimalFormat("0");
                                rowValues.add(df.format(row.getCell(i).getNumericCellValue()));
                            }
                        }else if(CellType.BOOLEAN == cellType) {
                            rowValues.add(String.valueOf(row.getCell(i).getBooleanCellValue()));
                        }else if(CellType.BLANK == cellType) {
                            rowValues.add("");
                        }else {
                            rowValues.add("");
                        }
                    }else {
                        rowValues.add("");
                    }
                }
                columnList.add(rowValues);
            }
            return columnList;
        } finally {
            input.close();
        }
    }

    /**
     * 读取Excel
     * @param fileName
     * @param input
     * @param ingoreRows
     * @param columnsLength
     * @return
     * @throws Exception
     */
    public static List<List<String>> readFile(String fileName,InputStream input,int ingoreRows,int columnsLength) throws Exception {

        String buffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        List<List<String>> datas = null;
        if (buffix.equalsIgnoreCase("xls")) {
            datas = ExcelUtils.readExcelByXls(input, ingoreRows, columnsLength);
        } else if (buffix.equalsIgnoreCase("xlsx")) {
            datas = ExcelUtils.readExcelByXlsx(input, ingoreRows, columnsLength);
        } else {
            return null;
        }
        return datas;
    }
}