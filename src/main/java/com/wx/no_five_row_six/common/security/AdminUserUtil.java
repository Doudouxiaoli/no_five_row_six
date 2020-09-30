package com.wx.no_five_row_six.common.security;

import com.wx.common.spring.mvc.WebUtil;

import javax.servlet.http.HttpSession;

/**
 * 用户工具类
 * 
 * 
 * @author 李旭光
 * @version 2013-7-13下午1:11:59
 */
public class AdminUserUtil {



	public static final String ADMIN_USER_LOGIN_SESSION = "ADMIN_USER_LOGIN_SESSION";

	
	/** 取得session */
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
	public static AdminUserModel getLoginUser() {
		if (hasLogin()) {
			return (AdminUserModel) getSession().getAttribute(ADMIN_USER_LOGIN_SESSION);
		} else {
			return null;
		}
	}

	public static String getShowName(){
		return getLoginUser().getShowName();
	}
	
	public static Long getUserId(){
		return getLoginUser().getId();
	}
	
	public static void logout() {
		getSession().removeAttribute(ADMIN_USER_LOGIN_SESSION);
	}

}
