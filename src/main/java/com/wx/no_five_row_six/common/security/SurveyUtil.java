package com.wx.no_five_row_six.common.security;

import com.wx.common.util.CookieUtil;
import com.wx.common.util.RC4Util;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


/**
 * 文章用户工具类
 *
 */
@Component
public class SurveyUtil {
    //用户cookie
    public static String LOGIN_COOKIE = "LOGIN_COOKIE";
    //10年有效
    public static int COOKIE_TIME = 10 * 12 * 30 * 24 * 60 * 60;

    /**
     * 创造cookie.
     *
     * @throws UnsupportedEncodingException
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response,
                                 String cookieName, String cookName, int maxAge) throws UnsupportedEncodingException {
        /* 自动登录 cookie设定 */
        String cookId = RC4Util.encode(cookName);
        CookieUtil.setCookie(request, response, cookieName, cookId, maxAge);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie cookie = CookieUtil.getCookie(request, key);
        if (cookie != null) {
            String cookId = cookie.getValue();
            cookId = RC4Util.decode(cookId);
            return cookId;
        } else {
            return null;
        }
    }

    /**
     * 获取一个http请求的ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
            if (ip.contains(",")) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
