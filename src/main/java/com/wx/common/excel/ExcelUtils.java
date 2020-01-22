// ======================================
// Project Name:xqhb
// Package Name:com.wx.no_five_row_six.xqhb.common.excel
// File Name:ExcelUtils.java
// Create Date:2018年04月09日  17:19
// ======================================
package com.wx.common.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author fyq
 * @version 2018年04月09日  17:19
 */
public class ExcelUtils {

    /**
     * 根据模板excel导出到浏览器
     *
     * @param request
     * @param response
     * @param fileName
     * @param data
     * @param file
     * @throws Exception
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse response, String fileName,
                                   ExcelData data, File file) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        String agent = request.getHeader("USER-AGENT");
        String codedFileName = getCodedFileName(agent, fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName);
        exportExcel(file, data, null, response.getOutputStream());
    }

    public static void exportNoDataExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        exportExcel(request, response, "无数据.xlsx", new ExcelData());
    }

    /**
     * 导出到浏览器 修复firefox乱码
     *
     * @param request
     * @param response
     * @param fileName
     * @param data
     * @throws Exception
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse response, String fileName,
                                   ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        String agent = request.getHeader("USER-AGENT");
        String codedFileName = getCodedFileName(agent, fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName);
        exportExcel(data, response.getOutputStream());
    }

    /**
     * @param data
     * @param out
     * @throws Exception
     */
    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
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
     * @param data
     * @param out
     * @throws Exception
     */
    public static void exportExcel(File file, ExcelData data, Integer rowIndex, OutputStream out) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.getSheet(sheetName);
            if (null == sheet) {
                sheet = wb.getSheetAt(0);
            }
            if (rowIndex == null) {
                rowIndex = sheet.getLastRowNum() + 1;
            }
            if (data != null) {
                if (data.getRows() != null && data.getRows().size() > 0) {
                    writeRowsToExcel(wb, sheet, data.getRows(), rowIndex, data.getNums());
                }
            }
            autoSizeColumns(sheet, data.getTitles() != null ? data.getTitles().size() + 1 : 0);

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
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            for (ExcelData data : list) {
                String sheetName = data.getName();
                if (null == sheetName) {
                    sheetName = "Sheet1";
                }
                XSSFSheet sheet = wb.createSheet(sheetName);
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
    public static void exportFileExcel(String basePath, String fileName, ExcelData data) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        FileOutputStream out = null;
        try {
            File file = new File(basePath);
            if (!file.exists())
                file.mkdirs();
            out = new FileOutputStream(basePath + File.separator + fileName);
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, XSSFSheet sheet, ExcelData data) {
        int rowIndex = 0;
        if (data != null) {
            if (data.getTitles() != null && data.getTitles().size() > 0) {
                rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
            }
            if (data.getRows() != null && data.getRows().size() > 0) {
                writeRowsToExcel(wb, sheet, data.getRows(), rowIndex, data.getNums());
            }
        }
        autoSizeColumns(sheet, data.getTitles() != null ? data.getTitles().size() + 1 : 0);
    }

    public static void writeExcel(XSSFWorkbook wb, XSSFSheet sheet, ExcelData data, Integer rowIndex) {
        if (rowIndex == null) {
            rowIndex = sheet.getLastRowNum() + 1;
        }
        if (data != null) {
            if (data.getTitles() != null && data.getTitles().size() > 0) {
                rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
            }
            if (data.getRows() != null && data.getRows().size() > 0) {
                writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
            }
        }
        autoSizeColumns(sheet, data.getTitles() != null ? data.getTitles().size() + 1 : 0);
    }

    private static int writeTitlesToExcel(Workbook wb, XSSFSheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;
        //冻结首行
        sheet.createFreezePane(0, 1);
        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN);

        Row titleRow = sheet.createRow(rowIndex);
        titleRow.setHeightInPoints(22);
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

    public static int writeRowsToExcel(XSSFWorkbook wb, XSSFSheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN);

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
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

    public static int writeRowsToExcel(XSSFWorkbook wb, XSSFSheet sheet, List<List<Object>> rows, int rowIndex, List<Integer> num) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN);

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    if (num != null && num.contains(colIndex)) {
                        if (StringUtils.isNotEmpty(cellData.toString())) {
                            cell.setCellValue(Double.valueOf(cellData.toString()));
                        } else {
                            cell.setCellValue("");
                        }
                    } else {
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

    /**
     * 特殊
     *
     * @param wb
     * @param sheet
     * @param rows
     * @param rowIndex
     * @param colIndex
     * @return
     */
    public static int writeRowsToExcel(XSSFWorkbook wb, XSSFSheet sheet, List<List<Object>> rows, int rowIndex, int colIndex) {
        Font dataFont = wb.createFont();
        //dataFont.setFontName("simsun");
        dataFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        //setBorder(dataStyle, BorderStyle.THIN);

        int col;
        for (List<Object> rowData : rows) {
            Row dataRow = sheet.getRow(rowIndex);
            if (dataRow == null) {
                dataRow = sheet.createRow(rowIndex);
            }
            col = colIndex;
            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(col);
                if (cellData != null) {
                    if (col == 11 || col == 12) {
                        cell.setCellValue(Integer.valueOf(cellData.toString()));
                    } else {
                        cell.setCellValue(cellData.toString());
                    }
                } else {
                    cell.setCellValue("");
                }

                cell.setCellStyle(dataStyle);
                col++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    /**
     * 赋值
     *
     * @param row
     * @param index
     * @param style
     * @param value
     */
    public static void setValue(Row row, int index, CellStyle style, String value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    /**
     * 赋值
     *
     * @param row
     * @param index
     * @param style
     * @param value
     */
    public static void setValue(Row row, int index, CellStyle style, Object value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellValue(Double.valueOf(value.toString()));
    }

    public static void autoSizeColumns(XSSFSheet sheet, int columnNumber) {
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

    public static void setBorder(CellStyle style, BorderStyle border) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
    }

    public static String getCodedFileName(String agent, String fileName) throws Exception {
        if (fileName == null) {
            fileName = "导出文件";
        }
        String codedFileName = null;
        if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
            codedFileName = "=?UTF-8?B?" + (Base64Utils.encodeToString(fileName.getBytes("UTF-8"))) + "?=";
        } else {
            codedFileName = URLEncoder.encode(fileName, "UTF-8");
        }
        return codedFileName;
    }
}