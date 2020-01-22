package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wx.common.util.EncryptUtil;
import com.wx.no_five_row_six.common.security.AdminUserModel;
import com.wx.no_five_row_six.common.security.AdminUserUtil;
import com.wx.no_five_row_six.entity.AdminUser;
import com.wx.no_five_row_six.service.impl.AdminUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/admin")
public class AdminLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginController.class);

    @Autowired
    private AdminUserServiceImpl adminUserService;

    @RequestMapping(value = {"", "/index"})
    public String index(ModelMap mm) {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String LoginInit() {
        return "/admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ModelMap mm, String name, String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            mm.addAttribute("errMsg", "用户名和密码不能为空");
        }
        try {
            QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<AdminUser>()
                    .eq("login_name", name)
                    .eq("password", EncryptUtil.getSHA256Value(password));
            AdminUser user = adminUserService.getOne(queryWrapper, false);
            if (user == null) {
                mm.addAttribute("errMsg", "用户名或密码错误");
            }
            AdminUserModel userModel = new AdminUserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getLoginName());
            userModel.setShowName(user.getLoginName());
            HttpSession session = request.getSession(true);
            if (session.getAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION) != null) {
                session.removeAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION);
            }
            session.setAttribute(AdminUserUtil.ADMIN_USER_LOGIN_SESSION, userModel);
            return "/admin/index";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            mm.addAttribute("errMsg", "出错了");
            return "/admin/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        AdminUserUtil.logout();
        return "/admin/login";
    }

}
