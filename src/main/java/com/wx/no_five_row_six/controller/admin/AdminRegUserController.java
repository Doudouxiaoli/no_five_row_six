package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.EncryptUtil;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.service.IFrsUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * @author dxl
 * @version 2020年10月22日  13:57
 * @desc 后台管理-注册用户
 */
@Controller
@RequestMapping("/admin/regUser")
public class AdminRegUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRegUserController.class);
    @Autowired
    private IFrsUserService userService;

    @RequestMapping("list")
    public String list() {
        return "admin/user/regUser/list";
    }

    /**
     * 列表
     *
     * @param keyword
     * @param page
     * @param limit
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(Integer page, Integer limit, String keyword, String startDate, String endDate) {
        page = Optional.ofNullable(page).orElse(1);
        limit = Optional.ofNullable(limit).orElse(Const.ADMIN_ROWSPERPAGE_MORE);
        IPage<FrsUser> pageInfo = new Page<>(page, limit);
        QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<FrsUser>();
        try {
            // 查询条件
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsUser::getFuName, keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), FrsUser::getFuPhone, keyword)
                    .ge(StringUtils.isNotEmpty(startDate), FrsUser::getFuCreateTime, TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
                    .le(StringUtils.isNotEmpty(endDate), FrsUser::getFuCreateTime, TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
            pageInfo = userService.page(pageInfo, queryWrapper);
            return JacksonMapper.newCountInstance(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-注册用户列表异常", e);
            return JacksonMapper.newErrorInstance("注册用户列表异常");
        }
    }

    /**
     * 用户编辑
     *
     * @param mm
     * @param userId
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long userId) {
        try {
            FrsUser user = userService.getById(userId);
            mm.addAttribute("user", user);
            return "admin/user/regUser/edit";
        } catch (Exception e) {
            e.printStackTrace();
            mm.addAttribute("errMsg", "注册用户编辑出错");
            return "error/error";
        }
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsUser user) {
        try {
            if (StringUtils.isNotEmpty(user.getFuPassword())) {
                user.setFuPassword(EncryptUtil.getSHA256Value(user.getFuPassword()));
            }
            user.setFuUpdateTime(TimeUtil.dateTolong());
            userService.updateById(user);
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("修改注册用户异常。", e);
            mm.addAttribute("errMsg", "修改注册用户异常");
            return "error/error";
        }
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping("del")
    public String del(ModelMap mm, Long userId) {
        try {
            userService.removeById(userId);
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除注册用户异常。", e);
            mm.addAttribute("errMsg", "删除注册用户异常");
            return "error/error";
        }
    }
}
