package com.wx.no_five_row_six.service;

import com.wx.common.excel.ExcelData;
import com.wx.no_five_row_six.entity.FrsWechatUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
public interface IFrsWechatUserService extends IService<FrsWechatUser> {
    ExcelData exportWechatUserExcel(List<FrsWechatUser> userList);

}
