// ======================================
// Project Name:gipap
// Package Name:com.wx.common.excel
// File Name:ExcelMethodParameter.java
// Create Date:2019年03月21日  21:20
// ======================================
package com.wx.common.excel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出时共用的处理参数方法
 *
 * @author fyq
 * @version 2019年03月21日  21:20
 */
public class ExcelMethodUtil {

    public static List<Long> getListPks(String parp) {
        List<Long> pks = new ArrayList<Long>();
        if (!parp.isEmpty()) {
            JsonElement element = new JsonParser().parse(parp);
            JsonObject parpObj = element.getAsJsonObject();
            JsonArray array = parpObj.get("pks").getAsJsonArray();
            // 前台传过来的id
            for (int i = 0; i < array.size(); i++) {
                pks.add(array.get(i).getAsLong());
            }
        }
        return pks;
    }

}