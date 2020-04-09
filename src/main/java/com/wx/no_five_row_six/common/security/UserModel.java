package com.wx.no_five_row_six.common.security;

import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.entity.FrsWechatUser;

import java.io.Serializable;

public class UserModel implements Serializable {

    FrsWechatUser wechatUser;
    FrsUser user;

    public FrsWechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(FrsWechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public FrsUser getUser() {
        return user;
    }

    public void setUser(FrsUser user) {
        this.user = user;
    }
}
