package com.wx.no_five_row_six.common.security;

import com.wx.common.spring.mvc.WebUtil;
import com.wx.no_five_row_six.entity.SysUser;

import javax.servlet.http.HttpSession;

/**
 * 用户工具类
 * 
 * 
 * @author 李旭光
 * @version 2013-7-13下午1:11:59
 */
public class AdminUserUtil {
    public static final String ADMIN_USER_LOGIN_SESSION = "SEC_ADMIN_USER_LOGIN_SESSION";


    /**
     * 取得session
     */
    private static HttpSession getSession() {
        return WebUtil.getOrCreateSession();
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public static boolean hasLogin() {
        return getSession().getAttribute(ADMIN_USER_LOGIN_SESSION) != null;
    }


    /**
     * 取得登录用户信息
     *
     * @return
     */
    public static SysUser getLoginUser() {
        if (hasLogin()) {
            return (SysUser) getSession().getAttribute(ADMIN_USER_LOGIN_SESSION);
        } else {
            return null;
        }
    }

    public static String getShowName() {
        if (!hasLogin()) {
            return "";
        }
        return getLoginUser().getSuName();
    }
    public static String getUserHead() {
        if (!hasLogin()) {
            return "";
        }
        return getLoginUser().getSuHeadImg();
    }
    public static Long getUserId() {
        return getLoginUser().getSuId();
    }

	/**
	 * 做登录时，需要的一些操作
	 */
	public static void login(SysUser user){
		HttpSession session = getSession();
		Object o = session.getAttribute(ADMIN_USER_LOGIN_SESSION);
		if (o != null) {
			session.removeAttribute(ADMIN_USER_LOGIN_SESSION);
		}
		session.setAttribute(ADMIN_USER_LOGIN_SESSION, user);
	}

	/**
	 * 做logout时，需要的一些操作
	 */
	public static void logout() {
		getSession().removeAttribute(ADMIN_USER_LOGIN_SESSION);
	}


}
