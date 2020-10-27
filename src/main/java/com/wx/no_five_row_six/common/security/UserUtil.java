// ======================================
// Project Name:ninlaro
// Package Name:com.wx.isa.common.security
// File Name:UserUtil.java
// Create Date:2018年03月05日  10:43
// ======================================
package com.wx.no_five_row_six.common.security;

import com.wx.common.spring.mvc.WebUtil;
import com.wx.common.util.CookieUtil;
import com.wx.common.util.RC4Util;
import com.wx.no_five_row_six.entity.WechatUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * 微信用户工具类
 *
 * @author 李旭光
 * @version 2018年03月05日  10:43
 */
public class UserUtil {
    public static final String USER_LOGIN_SESSION = "USER_LOGIN_SESSION";
    /**
     * 取得session
     */
    private static HttpSession getSession() {
        return WebUtil.getOrCreateSession();
    }
    /**
     * 判断用户是否登录
     */
    public static boolean hasLogin() {
        return getSession().getAttribute(USER_LOGIN_SESSION) != null;
    }
    /**
     * 取得用户model
     */
    public static UserModel getUserModel() {
        if (hasLogin()) {
            return (UserModel) getSession().getAttribute(USER_LOGIN_SESSION);
        }
        return null;
    }

    /**
     * 取得user id
     */
    public static Long getUserId() {
        if (hasLogin()) {
            UserModel userModel = (UserModel) getSession().getAttribute(USER_LOGIN_SESSION);
            return userModel.getWechatUser().getWuId();
        }
        return null;
    }

    /**
     * 取得openid
     */
    public static String getOpenid() {
        if (hasLogin()) {
            UserModel userModel = (UserModel) getSession().getAttribute(USER_LOGIN_SESSION);
            return userModel.getWechatUser().getWuOpenid();
        }
        return "游客";
    }

    /**
     * 取得用户姓名
     */
    public static String getUserName() {
        if (hasLogin()) {
            UserModel userModel = (UserModel) getSession().getAttribute(USER_LOGIN_SESSION);
            return userModel.getWechatUser().getWuNickname();
        }
        return "游客";
    }

    /**
     * 取得用户微信昵称
     */
    public static String getWechatUserName() {
        if (hasLogin()) {
            UserModel userModel = (UserModel) getSession().getAttribute(USER_LOGIN_SESSION);
            return userModel.getWechatUser().getWuNickname();
        }
        return "游客";
    }

    /**
     * 取得用户头像
     *
     * @return
     */
    public static String getHeadImg() {
        if (hasLogin()) {
            UserModel userModel = (UserModel) getSession().getAttribute(USER_LOGIN_SESSION);
            return userModel.getWechatUser().getWuHeadimg();
        }
        return "游客";
    }


    /**
     * 做登录时，需要的一些操作
     */
    public static void login(UserModel userModel) {
        HttpSession session = getSession();
        Object o = session.getAttribute(USER_LOGIN_SESSION);
        if (o != null) {
            session.removeAttribute(USER_LOGIN_SESSION);
        }
        session.setAttribute(USER_LOGIN_SESSION, userModel);
    }

    /**
     * 做logout时，需要的一些操作
     */
    public static void logout() throws UnsupportedEncodingException {
        getSession().removeAttribute(USER_LOGIN_SESSION);
        setCookie(WebUtil.getRequest(), WebUtil.getResponse(), USER_LOGIN_SESSION, "", -1);
    }

    /**
     * 创造cookie.
     *
     * @throws UnsupportedEncodingException
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response,
                                 String cookieName, String userName, int maxAge) throws UnsupportedEncodingException {
        /* 自动登录 cookie设定 */
        String autoLogin = RC4Util.encode(userName);
        CookieUtil.setCookie(request, response, cookieName, autoLogin, maxAge);
    }

    /**
     * 获得cookie对应的内容
     *
     * @param request
     * @param key
     * @return Cookie
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie cookie = CookieUtil.getCookie(request, key);
        if (cookie != null) {
            String autoLogin = cookie.getValue();
            autoLogin = RC4Util.decode(autoLogin);
            return autoLogin;
        } else {
            return null;
        }
    }

    /**
     * 更新用户后更新session
     *
     * @param user
     */
    public static void updateSession(WechatUser user) {
        if (hasLogin()) {
            getUserModel().setWechatUser(user);
        }
    }
}
