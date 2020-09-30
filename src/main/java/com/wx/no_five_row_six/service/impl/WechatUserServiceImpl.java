package com.wx.no_five_row_six.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.common.excel.ExcelData;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.entity.WechatUser;
import com.wx.no_five_row_six.mapper.WechatUserMapper;
import com.wx.no_five_row_six.service.IWechatUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 微信用户表 服务实现类
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements IWechatUserService {

    @Override
    public ExcelData exportWechatUserExcel(List<WechatUser> userList) {
        ExcelData data = new ExcelData();
        data.setName("微信用户信息");
        List<String> titles = Arrays.asList("微信用户表主键", "openid", "昵称", "性别", "城市", "国家", "省份", "语言", "头像", "unionid",
                "头像本地路径", "创建时间", "更新时间");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            for (WechatUser user : userList) {
                List<Object> row = new ArrayList<>();
                row.add(user.getWuId());
                row.add(user.getWuOpenid());
                row.add(user.getWuNickname());
                row.add(user.getWuSex());
                row.add(user.getWuCity());
                row.add(user.getWuCountry());
                row.add(user.getWuProvince());
                row.add(user.getWuLanguage());
                row.add(user.getWuHeadimg());
                row.add(user.getWuUnionid());
                row.add(user.getWuHeadimgurl());
                row.add(TimeUtil.longToString(user.getWuCreateTime(), "yyyy-MM-dd"));
                row.add(TimeUtil.longToString(user.getWuUpdateTime(), "yyyy-MM-dd"));
                rows.add(row);
            }
        }
        data.setRows(rows);
        return data;
    }
}
