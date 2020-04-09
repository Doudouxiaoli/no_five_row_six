package com.wx.no_five_row_six.service.impl;

import com.wx.no_five_row_six.entity.SmsCode;
import com.wx.no_five_row_six.mapper.SmsCodeMapper;
import com.wx.no_five_row_six.service.ISmsCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信验证码 服务实现类
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
@Service
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeMapper, SmsCode> implements ISmsCodeService {

}
