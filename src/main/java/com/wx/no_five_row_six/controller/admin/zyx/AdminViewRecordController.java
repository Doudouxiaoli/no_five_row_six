package com.wx.no_five_row_six.controller.admin.zyx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.excel.ExcelData;
import com.wx.common.excel.ExcelUtils;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsZyxVisitLog;
import com.wx.no_five_row_six.service.IFrsZyxVisitLogService;
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
import java.util.Optional;


@Controller
@RequestMapping("admin/visitLog")
public class AdminViewRecordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminViewRecordController.class);
    @Autowired
    private IFrsZyxVisitLogService visitLogService;
    @RequestMapping("list")
    public String list() { return "admin/other/listView"; }
    /**
     * list
     *
     * @param limit
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(ModelMap mm, Integer current, Integer limit, String keyword, String startDate, String endDate) {
        current = Optional.ofNullable(current).orElse(1);
        limit = Optional.ofNullable(limit).orElse(Const.ADMIN_ROWSPERPAGE_MORE);
        IPage<FrsZyxVisitLog> page = new Page<>(current, limit);
        try {
            QueryWrapper<FrsZyxVisitLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().between(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate), FrsZyxVisitLog::getZvlStartTime, TimeUtil.stringToLong(startDate), TimeUtil.stringToLong(endDate))
                    .like(StringUtils.isNotEmpty(keyword), FrsZyxVisitLog::getZvlNewsModule, keyword);
            page = visitLogService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("查询访问记录日志出错");
        }
    }

    /**
     * visit log  user list export
     *
     * @param response
     */
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response) {
        try {
            List<FrsZyxVisitLog> list = visitLogService.list();
            ExcelData excelData = visitLogService.exportLogExcel(list);
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
