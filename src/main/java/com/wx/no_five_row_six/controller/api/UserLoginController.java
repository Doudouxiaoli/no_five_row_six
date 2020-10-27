package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.EncryptUtil;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.security.UserModel;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsSmsCode;
import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.service.IFrsSmsCodeService;
import com.wx.no_five_row_six.service.IFrsUserService;
import com.wx.no_five_row_six.service.IWechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/api/user")
public class UserLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private IFrsUserService userService;
    @Autowired
    private IWechatUserService wechatUserService;
    @Autowired
    private IFrsSmsCodeService smsCodeService;

    /**
     * 用户名密码登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonNode login(String name, String password, HttpServletRequest request) {
        try {
            QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<FrsUser>();
            queryWrapper.lambda()
                    .eq(FrsUser::getFuName, name)
                    .eq(FrsUser::getFuPassword, EncryptUtil.getSHA256Value(password));
            FrsUser user = userService.getOne(queryWrapper, false);
            if (user == null) {
                return JacksonMapper.newErrorInstance("用户名或密码错误");
            }
            UserModel model = new UserModel();
            model.setFrsUser(user);
            UserUtil.login(model);
            return JacksonMapper.newSuccessInstance();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return JacksonMapper.newErrorInstance("登录出错了");
        }
    }

    @ResponseBody
    @RequestMapping("register")
    public JsonNode register(String fuName, String fuPassword, String fuProvince, String fuCity, String fuRegion, String fuPhone, String scCode) {
        try {
            QueryWrapper<FrsUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FrsUser::getFuPhone, fuPhone);
            FrsUser userDb = userService.getOne(queryWrapper, false);
            if (userDb != null) {
                return JacksonMapper.newErrorInstance("此手机号已经注册，请直接登录");
            }
            FrsUser user = new FrsUser();
            user.setFuName(fuName);
            user.setFuCity(fuCity);
            user.setFuProvince(fuProvince);
            user.setFuRegion(fuRegion);
            user.setFuPassword(EncryptUtil.getSHA256Value(fuPassword));
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
            //            FrsSmsCodeService.updateById(dbCode);
            UserModel userModel = new UserModel();
            //            userModel.setWechatUser(FrsWechatUser);
            userModel.setFrsUser(user);
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
            QueryWrapper<FrsSmsCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(FrsSmsCode::getScCode, scCode)
                    .eq(FrsSmsCode::getScPhone, fuPhone);
            FrsSmsCode FrsSmsCode = smsCodeService.getOne(queryWrapper, false);
            UserModel userModel = new UserModel();
            FrsUser user = userService.getById(FrsSmsCode.getScUserId());
            userModel.setFrsUser(user);
            UserUtil.login(userModel);
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
