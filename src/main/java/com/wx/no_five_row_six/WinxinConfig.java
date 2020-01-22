package com.wx.no_five_row_six;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 高雪
 * @version 2019年12月11日
 */
@ConfigurationProperties(prefix = "wx")
public class WinxinConfig {

    private String appId;

    private String secret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}