package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.EncryptUtil;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.security.UserModel;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.entity.SmsCode;
import com.wx.no_five_row_six.service.impl.FrsUserServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsWechatUserServiceImpl;
import com.wx.no_five_row_six.service.impl.SmsCodeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonNode login(String name, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<FrsUser>();
            queryWrapper.lambda()
                    .eq(FrsUser::getFuName, name)
                    .eq(FrsUser::getFuPassword, EncryptUtil.getSHA256Value(password));
            FrsUser user = userService.getOne(queryWrapper, false);
            if (user == null) {
                return JacksonMapper.newErrorInstance("用户名或密码错误");
            }
            UserModel userModel = new UserModel();
            userModel.setUser(user);
            HttpSession session = request.getSession(true);
            if (session.getAttribute(UserUtil.USER_SESSION_NAME) != null) {
                session.removeAttribute(UserUtil.USER_SESSION_NAME);
            }

            session.setAttribute(UserUtil.USER_SESSION_NAME, userModel);
            //存储cookie
            //Cookie cookie = new Cookie("user",userModel.toString());
            response.setHeader("token",session.getId());
            return JacksonMapper.newSuccessInstance();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return JacksonMapper.newErrorInstance("登录出错了");
        }
    }

    /**
     * 用户注册
     * @param fuName
     * @param fuPassword
     * @param fuProvince
     * @param fuCity
     * @param fuRegion
     * @param fuPhone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonNode register(String fuName, String fuPassword, String fuProvince, String fuCity, String fuRegion, String fuPhone) {
        try {
            FrsUser user = new FrsUser();
            user.setFuName(fuName);
            user.setFuPassword(EncryptUtil.getSHA256Value(fuPassword));
            user.setFuPhone(fuPhone);
            user.setFuCity(fuCity);
            user.setFuProvince(fuProvince);
            user.setFuRegion(fuRegion);
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
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return JacksonMapper.newErrorInstance("登录异常");
        }
    }

    /**
     * 忘记密码时跳转至手机号登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("forgetPassword")
    public JsonNode forgetPassword(String scCode, String fuPhone, HttpServletRequest request) {
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
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "登录出错了";
            LOGGER.error(errMsg, e);
            return JacksonMapper.newErrorInstance(errMsg);
        }
    }

    /**
     * 退出
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonNode logout() {
        try {
            UserUtil.logout();
            return JacksonMapper.newSuccessInstance();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("前台用户退出失败，openid=" + UserUtil.getOpenid(), e);
            return JacksonMapper.newErrorInstance("前台用户退出失败，openid=" + UserUtil.getOpenid());
        }
    }


}
