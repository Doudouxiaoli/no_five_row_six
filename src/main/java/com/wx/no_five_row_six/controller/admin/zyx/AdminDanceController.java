package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.ZyxNewsConst;
import com.wx.no_five_row_six.common.security.AdminUserUtil;
import com.wx.no_five_row_six.entity.FrsZyxNews;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dxl
 * @version 2020/10/10 13:27
 * @desc 后台管理-舞蹈
 */
@Controller
@RequestMapping("/admin/zyx/dance")
public class AdminDanceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDanceController.class);

    @Autowired
    private IFrsZyxNewsService danceService;


    @RequestMapping("list")
    public String list() {
        return "admin/zyx/dance/list";
    }

    /**
     * 舞蹈列表
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(ModelMap mm, String keyword, Integer current, Integer size) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword)
                    .or()
                    .like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnFrom, keyword)
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.DANCE)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = danceService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-舞蹈列表异常", e);
            mm.addAttribute("errMsg", "舞蹈列表异常");
            return JacksonMapper.newErrorInstance("后台管理-舞蹈列表异常");
        }

    }

    /**
     * 编辑舞蹈界面
     *
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            if (null == id) {
                mm.addAttribute("title", "舞蹈添加");
            } else {
                mm.addAttribute("title", "舞蹈编辑");
                FrsZyxNews dance = danceService.getById(id);
                mm.addAttribute("dance", dance);
            }
            return "admin/zyx/dance/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取舞蹈异常。", e);
            mm.addAttribute("errMsg", "获取舞蹈异常");
            return "error/error";
        }
    }

    /**
     * 保存舞蹈
     *
     * @param dance
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews dance) {
        try {
            if (dance.getZnId() == null) {
                dance.setZnNcId(ZyxNewsConst.DANCE);
                dance.setZnCreateUserId(AdminUserUtil.getUserId());
                dance.setZnCreateUserName(AdminUserUtil.getShowName());
                dance.setZnCreateTime(TimeUtil.dateToLong());
                dance.setZnIsValid(ZyxNewsConst.VALID);
                danceService.save(dance);
            } else {
                dance.setZnUpdateTime(TimeUtil.dateTolong());
                dance.setZnUpdateUserId(AdminUserUtil.getUserId());
                dance.setZnUpdateUserName(AdminUserUtil.getShowName());
                danceService.updateById(dance);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改舞蹈异常。", e);
            mm.addAttribute("errMsg", "保存或修改舞蹈异常");
            return "error/error";
        }
    }
}
