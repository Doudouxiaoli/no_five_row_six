package com.wx.no_five_row_six.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.common.excel.ExcelData;
import com.wx.no_five_row_six.entity.WechatUser;

import java.util.List;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public interface IWechatUserService extends IService<WechatUser> {
    ExcelData exportWechatUserExcel(List<WechatUser> userList);
}
