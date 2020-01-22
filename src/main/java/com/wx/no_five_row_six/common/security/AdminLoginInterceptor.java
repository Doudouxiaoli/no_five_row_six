// ======================================
// Project Name:ssm
// Package Name:com.wx.no_five_row_six.common.security
// File Name:LoginInterceptor.java
// Create Date:2019年10月24日  11:13
// ======================================
package com.wx.no_five_row_six.common.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lym
 * @version 2019年11月8日  11:01
 */
@Component
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String path = request.getContextPath();
        requestUri = requestUri.replaceFirst(path, "");
        if (!AdminUserUtil.hasLogin()) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}