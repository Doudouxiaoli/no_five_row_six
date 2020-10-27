package com.wx.no_five_row_six.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.no_five_row_six.entity.FrsSmsCode;

/**
 * <p>
 * 短信验证码 服务类
 * </p>
 *
 * @author dxl
 * @since 2020-10-22
 */
public interface IFrsSmsCodeService extends IService<FrsSmsCode> {

    /***************type 类型  start*******************************/
    //手机号登录用验证码
    String MOBILE_LOGIN = "mobile_login";
    //手机号注册用验证码
    String MOBILE_REGISTER = "mobile_register";
    //修改个人信息用验证码
    String MOBILE_MODIFY = "mobile_modify";
    //直播预约接收验证码
    String MOBILE_FORWARD = "mobile_forward";
    /***************type 类型  end*******************************/
    JsonNode saveCode(String ip, String phone, String type, Long userId) throws Exception;
}
