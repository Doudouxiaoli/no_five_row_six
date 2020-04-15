package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.EncryptUtil;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.service.impl.FrsUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pcUser")
public class AdminPcUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPcUserController.class);
    @Autowired
    private FrsUserServiceImpl userService;

    /**
     * 列表
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, String keyword, Integer current, Integer size, String startDate, String endDate) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<FrsUser>();
        IPage<FrsUser> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsUser::getFuName, keyword);
            page = userService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-用户列表异常", e);
            mm.addAttribute("errMsg", "用户列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("recordSize", page.getRecords().size());
        return "admin/user/pcUser/list";
    }


}
