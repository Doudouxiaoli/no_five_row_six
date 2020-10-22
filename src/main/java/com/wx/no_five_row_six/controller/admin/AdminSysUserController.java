package com.wx.no_five_row_six.controller.admin;

import com.wx.no_five_row_six.entity.SysUser;
import com.wx.no_five_row_six.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sysUser")
public class AdminSysUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSysUserController.class);
    @Autowired
    private SysUserServiceImpl userService;

    /**
     * 用户编辑
     *
     * @param mm
     * @param userId
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long userId) {
        SysUser user = userService.getById(userId);
        if (user == null) {
            mm.addAttribute("noPassword", true);
            return "redirect:admin/login";
        }
        mm.addAttribute("noPassword", false);
        mm.addAttribute("user", user);
        return "admin/user/adminUser/edit";
    }

    /**
     * 用户保存
     *
     * @param user
     * @param mm
     * @return
     */
    @RequestMapping("save")
    public String save(SysUser user, ModelMap mm) {
        try {
            if (user.getSuId() == null) {
                user.setSuCreateTime(System.currentTimeMillis());
                userService.save(user);
            } else {
                user.setSuUpdateTime(System.currentTimeMillis());
                userService.updateById(user);
            }
            return "redirect:/admin/index";
        } catch (Exception e) {
            e.printStackTrace();
            mm.addAttribute("errMsg", "用户保存异常");
            LOGGER.error("后台用户保存出现异常");
            return "error/error";
        }


    }

}
