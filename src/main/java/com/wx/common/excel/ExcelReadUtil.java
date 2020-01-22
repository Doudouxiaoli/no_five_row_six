package com.wx.common.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 读取Excel2007,大量数据
 * Created by zhl on 2018/11/13.
 */
public class ExcelReadUtil {
    private LinkedHashMap<String, String> rowContents = new LinkedHashMap<String, String>();
    private SheetHandler sheetHandler;

    public LinkedHashMap<String, String> getRowContents() {
        return rowContents;
    }

    public void setRowContents(LinkedHashMap<String, String> rowContents) {
        this.rowContents = rowContents;
    }

    public SheetHandler getSheetHandler() {
        return sheetHandler;
    }

    public void setSheetHandler(SheetHandler sheetHandler) {
        this.sheetHandler = sheetHandler;
    }

    /**
     * 处理一个sheet
     */
    public void processOneSheet(String filename) throws Exception {
        InputStream sheet2 = null;
        OPCPackage pkg = null;
        try {
            pkg = OPCPackage.open(filename);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst);
            sheet2 = r.getSheet("rId1");
            InputSource sheetSource = new InputSource(sheet2);
            parser.parse(sheetSource);
            setRowContents(sheetHandler.getRowContents());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (pkg != null) {
                pkg.close();
            }
            if (sheet2 != null) {
                sheet2.close();
            }
        }
    }

    /**
     * 处理多个sheet
     *
     * @param filename
     * @throws Exception
     */
    public void processAllSheets(String filename) throws Exception {
        OPCPackage pkg = null;
        InputStream sheet = null;
        try {
            pkg = OPCPackage.open(filename);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst);
            Iterator<InputStream> sheets = r.getSheetsData();
            while (sheets.hasNext()) {
                System.out.println("Processing new sheet:\n");
                sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (pkg != null) {
                pkg.close();
            }
            if (sheet != null) {
                sheet.close();
            }
        }
    }

    /**
     * @param sst
     * @return
     * @throws SAXException
     */
    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        setSheetHandler(new SheetHandler(sst));
        ContentHandler handler = (ContentHandler) sheetHandler;
        parser.setContentHandler(handler);
        return parser;
    }

    public static void main(String[] args) {
        try {
            Long time = System.currentTimeMillis();
            ExcelReadUtil excelReadUtil = new ExcelReadUtil();
            excelReadUtil.processOneSheet("C:\\Users\\zhanghongliang\\Desktop\\重庆儿童\\AD复诊数据.xlsx");
            LinkedHashMap<String, String> map = excelReadUtil.getRowContents();
            Long endtime = System.currentTimeMillis();
            int count = 0;
            String prePos = "";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key + ";" + value);
            }
            System.out.println("解析数据" + count + "条;耗时" + (endtime - time) / 1000 + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}