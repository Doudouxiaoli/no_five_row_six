// ======================================
// Project Name:xqhb
// Package Name:com.wx.xqhb.common.excel
// File Name:ExcelUtils.java
// Create Date:2018年04月09日  17:19
// ======================================
package com.wx.common.excel;

import com.wx.common.spring.mvc.WebUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author fyq
 * @version 2018年04月09日  17:19
 */
public class BigExcelUtils {
    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {

        SXSSFWorkbook wb = new SXSSFWorkbook(200);
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            SXSSFSheet sheet = (SXSSFSheet)wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);

            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //此处需要关闭 wb 变量
            out.close();
        }
    }

    /**
     * excel含有多个标签页
     */
    public static void exportSheetExcel(HttpServletResponse response, String fileName, List<ExcelData> list) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        exportSheetExcel(list, response.getOutputStream());
    }

    /**
     * excel含有多个标签页
     */
    public static void exportSheetExcel(List<ExcelData> list, OutputStream out) throws Exception {
        SXSSFWorkbook wb = new SXSSFWorkbook(200);
        try {
            for (ExcelData data : list) {
                String sheetName = data.getName();
                if (null == sheetName) {
                    sheetName = "Sheet1";
                }
                SXSSFSheet sheet = (SXSSFSheet)wb.createSheet(sheetName);
                writeExcel(wb, sheet, data);
            }
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //此处需要关闭 wb 变量
            out.close();
        }
    }

    /**
     * 将excel导出到某目录文件
     *
     * @param fileName
     * @param data
     * @throws Exception
     */
    public static void exportFileExcel(String fileName, ExcelData data) throws Exception {
        SXSSFWorkbook wb = new SXSSFWorkbook(200);
        FileOutputStream out = null;
        try {
            String basePath = WebUtil.getRealPath("/") + "upload/email";
            File file = new File(basePath);
            if (!file.exists()){
                file.mkdirs();
            }
            out = new FileOutputStream(basePath + File.separator + fileName);
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            SXSSFSheet sheet = (SXSSFSheet)wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    private static void writeExcel(SXSSFWorkbook wb, Sheet sheet, ExcelData data) {

        int rowIndex = 0;

        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        autoSizeColumns(sheet, data.getTitles().size() + 1);
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);

    }

    private static int writeTitlesToExcel(SXSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;

        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        //titleFont.setBoldweight(Short.MAX_VALUE);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = (XSSFCellStyle)wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static int writeRowsToExcel(SXSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = (XSSFCellStyle)wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
				if (cellData != null) {
					if(cellData instanceof Double){
						cell.setCellValue((Double) cellData);
					}else if(cellData instanceof Integer){
						cell.setCellValue((Integer) cellData);
					}else if(cellData instanceof Long){
						cell.setCellValue((Long) cellData);
					}else{
						cell.setCellValue(cellData.toString());
					}
				} else {
					cell.setCellValue("");
				}

                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        ((SXSSFSheet)sheet).trackAllColumnsForAutoSizing();
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > 255 * 255) {
                newWidth = 6000;
            }
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
        style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
    }
}