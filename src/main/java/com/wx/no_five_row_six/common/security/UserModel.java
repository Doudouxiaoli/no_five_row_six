package com.wx.no_five_row_six.common.security;

import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.entity.WechatUser;

import java.io.Serializable;

public class UserModel implements Serializable {

    WechatUser wechatUser;
    FrsUser frsUser;

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public FrsUser getFrsUser() {
        return frsUser;
    }

    public void setFrsUser(FrsUser frsUser) {
        this.frsUser = frsUser;
    }
}
