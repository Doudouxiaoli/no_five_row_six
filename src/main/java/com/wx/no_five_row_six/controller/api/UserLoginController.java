package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.EncryptUtil;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.security.UserModel;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.entity.FrsWechatUser;
import com.wx.no_five_row_six.entity.SmsCode;
import com.wx.no_five_row_six.service.impl.FrsUserServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsWechatUserServiceImpl;
import com.wx.no_five_row_six.service.impl.SmsCodeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/api/user")
public class UserLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private FrsUserServiceImpl userService;
    @Autowired
    private FrsWechatUserServiceImpl wechatUserService;
    @Autowired
    private SmsCodeServiceImpl smsCodeService;

    /**
     * 用户名密码登录
     *
     * @return
     */
    @RequestMapping(value = {"", "LoginInit"})
    public String LoginInit() {
        return "pc/include/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ModelMap mm, String name, String password, HttpServletRequest request) {
        try {
            QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<FrsUser>();
            queryWrapper.lambda()
                    .eq(FrsUser::getFuName, name)
                    .eq(FrsUser::getFuPassword, EncryptUtil.getSHA256Value(password));
            FrsUser user = userService.getOne(queryWrapper, false);
            if (user == null) {
                mm.addAttribute("errMsg", "用户名或密码错误");
            }
            UserModel userModel = new UserModel();
            userModel.setUser(user);
            HttpSession session = request.getSession(true);
            if (session.getAttribute(UserUtil.USER_SESSION_NAME) != null) {
                session.removeAttribute(UserUtil.USER_SESSION_NAME);
            }
            session.setAttribute(UserUtil.USER_SESSION_NAME, userModel);
            return "pc/include/index";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            mm.addAttribute("errMsg", "登录出错了");
            return "pc/include/login";
        }
    }

    /**
     * 注册
     *
     * @return
     */
    @RequestMapping("registerInit")
    public String registerInit() {
        return "pc/include/register";
    }

    @RequestMapping("register")
    public String register(FrsUser user, String scCode, ModelMap mm) {
        try {
            QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FrsUser::getFuPhone, user.getFuPhone());
            FrsUser userDb = userService.getOne(queryWrapper, false);
            if (userDb != null) {
                mm.addAttribute("errMsg", "此手机号已经注册，请直接登录");
                return "pc/include/login";
            }
            QueryWrapper<SmsCode> smsWrapper = new QueryWrapper<>();
            smsWrapper.lambda().eq(SmsCode::getScPhone, user.getFuPhone())
                    .eq(SmsCode::getScCode, scCode)
                    .eq(SmsCode::getScType, smsCodeService.MOBILE_REGISTER)
                    .eq(SmsCode::getScIsUsed, 0)
                    .le(SmsCode::getScCreateDate, TimeUtil.dateTolong())
                    .ge(SmsCode::getScInvalidDate, TimeUtil.dateTolong());
            SmsCode dbCode = smsCodeService.getOne(smsWrapper, false);
            user.setFuCreateTime(TimeUtil.dateToLong());
            user.setFuUpdateTime(TimeUtil.dateTolong());
            userService.save(user);
            //用户和微信号绑定
//            QueryWrapper<FrsWechatUser> wechatWrapper = new QueryWrapper<>();
//            wechatWrapper.lambda().eq(FrsWechatUser::getFwuOpenid, UserUtil.getOpenid());
//            FrsWechatUser FrsWechatUser = wechatUserService.getOne(wechatWrapper, false);
//            FrsWechatUser.setFwuUserId(user.getFuId());
//            FrsWechatUser.setFwuUpdateTime(TimeUtil.dateTolong());
//            wechatUserService.updateById(FrsWechatUser);
//            dbCode.setScIsUsed(1);
//            smsCodeService.updateById(dbCode);
            UserModel userModel = new UserModel();
//            userModel.setWechatUser(FrsWechatUser);
            userModel.setUser(user);
            UserUtil.login(userModel);
            return "pc/include/index";
        } catch (Exception e) {
            String errMsg = "注册异常，请重试";
            LOGGER.error(e.getMessage(), e);
            mm.addAttribute("errMsg", errMsg);
            return "error/error";
        }
    }

    /**
     * 忘记密码时跳转至手机号登录
     *
     * @return
     */
    @RequestMapping("forgetPasswordInit")
    public String forgetPasswordInit() {
        return "pc/include/forgetPassword";
    }

    @RequestMapping("forgetPassword")
    @ResponseBody
    public String forgetPassword(String scCode, String fuPhone, ModelMap mm, HttpServletRequest request) {
        try {
            QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(SmsCode::getScCode, scCode)
                    .eq(SmsCode::getScPhone, fuPhone);
            SmsCode smsCode = smsCodeService.getOne(queryWrapper, false);
            UserModel userModel = new UserModel();
            FrsUser user = userService.getById(smsCode.getScUserId());
            userModel.setUser(user);
            HttpSession session = request.getSession(true);
            if (session.getAttribute(UserUtil.USER_SESSION_NAME) != null) {
                session.removeAttribute(UserUtil.USER_SESSION_NAME);
            }
            session.setAttribute(UserUtil.USER_SESSION_NAME, userModel);
            return "pc/include/index";
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "登录出错了";
            LOGGER.error(errMsg, e);
            mm.addAttribute("errMsg", errMsg);
            return "pc/include/forgetPassword";
        }
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        try {
            UserUtil.logout();
            return "pc/include/login";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("前台用户退出失败，openid=" + UserUtil.getOpenid(), e);
            return "11";
        }
    }


}
