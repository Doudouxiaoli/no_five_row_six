package com.wx.no_five_row_six.service.impl;

import com.wx.no_five_row_six.entity.AdminUser;
import com.wx.no_five_row_six.mapper.AdminUserMapper;
import com.wx.no_five_row_six.service.IAdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

}
