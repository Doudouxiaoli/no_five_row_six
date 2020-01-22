package com.wx.common.excel;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedHashMap;

/**
 * Created by zhl on 2018/11/13.
 */
public class SheetHandler extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;
    private String cellPosition;
    private LinkedHashMap<String, String> rowContents = new LinkedHashMap<String, String>();

    public LinkedHashMap<String, String> getRowContents() {
        return rowContents;
    }

    public void setRowContents(LinkedHashMap<String, String> rowContents) {
        this.rowContents = rowContents;
    }

    public SheetHandler(SharedStringsTable sst) {
        this.sst = sst;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if (name.equals("c")) {
            // Print the cell reference
            cellPosition = attributes.getValue("r");
            //System.out.print(attributes.getValue("r") + " - ");
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // Clear contents cache
        lastContents = "";
    }

    /**
     * 第二个执行
     * 得到单元格对应的索引值或是内容值
     * 如果单元格类型是字符串、INLINESTR、数字、日期，lastIndex则是索引值
     * 如果单元格类型是布尔值、错误、公式，lastIndex则是内容值
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
        if (nextIsString) {
            int idx = Integer.parseInt(lastContents);
            lastContents = sst.getItemAt(idx).getString();
            nextIsString = false;
        }
        if (name.equals("v")) {
            //System.out.println("lastContents:" + cellPosition + ";" + lastContents);
            //数据读取结束后，将单元格坐标,内容存入map中
            rowContents.put(cellPosition, lastContents);
        }
    }


}
