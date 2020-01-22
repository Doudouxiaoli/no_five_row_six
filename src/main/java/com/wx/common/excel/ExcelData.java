// ======================================
// Project Name:xqhb
// Package Name:com.wx.no_five_row_six.xqhb.common.excel
// File Name:ExcelData.java
// Create Date:2018年04月09日  17:18
// ======================================
package com.wx.common.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fyq
 * @version 2018年04月09日  17:18
 */
public class ExcelData implements Serializable {
    private static final long serialVersionUID = 4444017239100620999L;

    // 表头
    private List<String> titles;

    // 数据
    private List<List<Object>> rows;

    // 页签名称
    private String name;

    //格式为数字的列
    private List<Integer> nums;

    public ExcelData() {

    }

    public ExcelData(String name) {
        this.name = name;
    }

    public ExcelData(String name, String... titles) {
        this.name = name;
        this.titles = Arrays.asList(titles);
    }

    public void addRow(List<Object> row) {
        if (this.rows == null) {
            this.rows = new ArrayList<>();
        }
        this.rows.add(row);
    }

    public void addRow(Object... data) {
        addRow(Arrays.asList(data));
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }

    /**
     * 第几列设置为数字,从0开始
     *
     * @param data
     */
    public void addNums(Integer... data) {
        setNums(Arrays.asList(data));
    }
}