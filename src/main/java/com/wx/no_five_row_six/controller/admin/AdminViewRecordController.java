package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.excel.ExcelData;
import com.wx.common.excel.ExcelUtils;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsViewRecord;
import com.wx.no_five_row_six.service.impl.FrsViewRecordServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("admin/visitLog")
public class AdminViewRecordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminViewRecordController.class);
    @Autowired
    private FrsViewRecordServiceImpl viewRecordService;

    /**
     * list
     *
     * @param limit
     * @param keyword
     * @return
     */
    @RequestMapping("/list")
    public String list(ModelMap mm, Integer current, Integer limit, String keyword, String startDate, String endDate) {
        if (current == null) {
            current = 1;
        }
        if (limit == null) {
            limit = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        IPage<FrsViewRecord> page = new Page<>(current, limit);
        QueryWrapper<FrsViewRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate), FrsViewRecord::getFvrTime, TimeUtil.stringToLong(startDate), TimeUtil.stringToLong(endDate))
                .like(StringUtils.isNotEmpty(keyword), FrsViewRecord::getFvrTypeName, keyword);
        page = viewRecordService.page(page, queryWrapper);
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        return "admin/other/listView";
    }

    /**
     * visit log  user list export
     *
     * @param response
     */
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response) {
        try {
            List<FrsViewRecord> list = viewRecordService.list();
            ExcelData excelData = viewRecordService.exportLogExcel(list);
            String fileName = "五排六号浏览记录导出" + TimeUtil.longToString(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx";
            ExcelUtils.exportExcel(response, fileName, excelData);
        } catch (Exception e) {
            LOGGER.error("导出时" + e.getMessage(), e);
            try {
                e.printStackTrace(response.getWriter());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
