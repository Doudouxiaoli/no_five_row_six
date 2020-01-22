package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.EncryptUtil;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.AdminUser;
import com.wx.no_five_row_six.service.impl.AdminUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/adminUser")
public class AdminUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);
    @Autowired
    private AdminUserServiceImpl adminUserService;

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
        QueryWrapper<AdminUser> queryWrapper = null;
        IPage<AdminUser> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper = new QueryWrapper<AdminUser>().like(StringUtils.isNotEmpty(keyword), "login_name", keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), "id", keyword)
                    .ge(StringUtils.isNotEmpty(startDate), "create_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
                    .le(StringUtils.isNotEmpty(endDate), "create_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE));
            page = adminUserService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-管理员列表异常", e);
            mm.addAttribute("errMsg", "管理员列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("recordSize", page.getRecords().size());
        return "admin/adminUser/list";
    }

    /**
     * 添加用户界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String newsAddUI() {
        return "admin/adminUser/add";
    }

    /**
     * 编辑用户界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            AdminUser user = adminUserService.getById(id);
            mm.addAttribute("user", user);
            return "admin/adminUser/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取管理员异常。", e);
            mm.addAttribute("errMsg", "获取管理员异常");
            return "error/error";
        }
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @RequestMapping("userUpdate")
    public String userUpdate(ModelMap mm, AdminUser user) {
        try {
            if (user.getId() == null) {
                if (StringUtils.isNotEmpty(user.getPassword())) {
                    user.setPassword(EncryptUtil.getSHA256Value(user.getPassword()));
                }
                user.setCreateTime(TimeUtil.dateToLong());
                adminUserService.save(user);
            } else {
                if (StringUtils.isNotEmpty(user.getPassword())) {
                    user.setPassword(EncryptUtil.getSHA256Value(user.getPassword()));
                }
                user.setUpdateTime(TimeUtil.dateTolong());
                adminUserService.updateById(user);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改管理员异常。", e);
            mm.addAttribute("errMsg", "保存或修改管理员异常");
            return "error/error";
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    public String userDel(ModelMap mm, Long id) {
        try {
            adminUserService.removeById(id);
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除管理员异常。", e);
            mm.addAttribute("errMsg", "删除管理员异常");
            return "error/error";
        }
    }
}
