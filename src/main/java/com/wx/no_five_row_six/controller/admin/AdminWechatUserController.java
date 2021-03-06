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
import com.wx.no_five_row_six.entity.WechatUser;
import com.wx.no_five_row_six.service.IWechatUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/wechatUser")
public class AdminWechatUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminWechatUserController.class);
    @Autowired
    private IWechatUserService wechatUserService;

    @RequestMapping("list")
    public String list() {
        return "admin/user/wechatUser/list";
    }

    /**
     * 列表
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("listAjax")
    @ResponseBody
    public JsonNode listAjax(String keyword, Integer current, Integer size) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        QueryWrapper<WechatUser> queryWrapper = new QueryWrapper<WechatUser>();
        IPage<WechatUser> page = new Page<>(current, size);
        try {
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), WechatUser::getWuNickname, keyword);
            page = wechatUserService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-微信列表异常", e);
            return JacksonMapper.newErrorInstance("微信用户列表异常");
        }
    }

    /**
     * weChat user list export
     *
     * @param response
     * @param keyword
     */
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response, String keyword) {
        try {
            QueryWrapper<WechatUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), WechatUser::getWuNickname, keyword);
            List<WechatUser> list = wechatUserService.list(queryWrapper);
            ExcelData excelData = wechatUserService.exportWechatUserExcel(list);
            String fileName = "五排六号微信用户导出" + TimeUtil.longToString(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx";
            ExcelUtils.exportExcel(response, fileName, excelData);
        } catch (Exception e) {
            LOGGER.error("微信用户导出时" + e.getMessage(), e);
            try {
                e.printStackTrace(response.getWriter());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
