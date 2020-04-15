package com.wx.no_five_row_six.service.impl;

import com.wx.common.excel.ExcelData;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.entity.FrsWechatUser;
import com.wx.no_five_row_six.mapper.FrsWechatUserMapper;
import com.wx.no_five_row_six.service.IFrsWechatUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2020-04-09
 */
@Service
public class FrsWechatUserServiceImpl extends ServiceImpl<FrsWechatUserMapper, FrsWechatUser> implements IFrsWechatUserService {
    @Override
    public ExcelData exportWechatUserExcel(List<FrsWechatUser> userList) {
        ExcelData data = new ExcelData();
        data.setName("微信用户信息");
        List<String> titles = Arrays.asList("微信用户表主键", "openid", "昵称", "性别", "城市", "国家", "省份", "语言", "头像", "unionid",
                "头像本地路径", "创建时间", "更新时间");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            for (FrsWechatUser user : userList) {
                List<Object> row = new ArrayList<>();
                row.add(user.getFwuUserId());
                row.add(user.getFwuOpenid());
                row.add(user.getFwuNickname());
                row.add(user.getFwuSex());
                row.add(user.getFwuCity());
                row.add(user.getFwuCountry());
                row.add(user.getFwuProvince());
                row.add(user.getFwuLanguage());
                row.add(user.getFwuHeadimg());
                row.add(user.getFwuUnionid());
                row.add(user.getFwuHeadimgurl());
                row.add(TimeUtil.longToString(user.getFwuCreateTime(), "yyyy-MM-dd"));
                row.add(TimeUtil.longToString(user.getFwuUpdateTime(), "yyyy-MM-dd"));
                rows.add(row);
            }
        }
        data.setRows(rows);
        return data;
    }
}
