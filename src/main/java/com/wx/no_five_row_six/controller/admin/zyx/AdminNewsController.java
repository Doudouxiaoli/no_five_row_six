package com.wx.no_five_row_six.controller.admin.zyx;

import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dxl
 * @version 2020/10/16 17:57
 * @desc 后台管理-公共方法提取
 */
@Controller
@RequestMapping("/admin/zyx/news")
public class AdminNewsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminNewsController.class);
    @Autowired
    private IFrsZyxNewsService newsService;

    /**
     * 删除/恢复
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("changeValid")
    public JsonNode changeValid(Long id) {
        try {
            newsService.changeValid(id);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-状态修改异常。", e);
            return JacksonMapper.newErrorInstance("状态修改异常");
        }
    }
}
