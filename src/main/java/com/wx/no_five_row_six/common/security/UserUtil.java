package com.wx.no_five_row_six.common.security;

//import com.wx.no_five_row_six.entity.GioUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class UserUtil {

//    public static final String USER_LOGIN_SESSION = "USER_LOGIN_SESSION";
//
//    /**
//     * 取得session
//     */
//    private static HttpSession getSession() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//                .currentRequestAttributes()).getRequest();
//        return request.getSession(true);
//    }
//
//    /**
//     * 判断用户是否登录
//     */
//    public static boolean hasLogin() {
//        return getSession().getAttribute(USER_LOGIN_SESSION) != null;
//    }
//
//    /**
//     * 登录
//     */
////    public static void login(GioUser user) {
////        HttpSession session = getSession();
////        Object o = session.getAttribute(USER_LOGIN_SESSION);
////        if (o != null) {
////            session.removeAttribute(USER_LOGIN_SESSION);
////        }
////        session.setAttribute(USER_LOGIN_SESSION, user);
////    }
//
//    public static void logout() {
//        getSession().removeAttribute(USER_LOGIN_SESSION);
//    }
////
////
////    /**
////     * 取得登录用户信息
////     */
////    public static GioUser getLoginUser() {
////        if (hasLogin()) {
////            return (GioUser) getSession().getAttribute(USER_LOGIN_SESSION);
////        } else {
////            return null;
////        }
////    }
//////
//    /**
//     * 取得登录用户的主键
//     */
//    public static Long getLoginUserId() {
//        return hasLogin() ? Objects.requireNonNull(getLoginUser()).getGuId() : null;
//    }
//
//    /**
//     * 取得登录用户的姓名
//     */
//    public static String getLoginUserName() {
//        return hasLogin() ? Objects.requireNonNull(getLoginUser()).getGuName() : "";
//    }

}