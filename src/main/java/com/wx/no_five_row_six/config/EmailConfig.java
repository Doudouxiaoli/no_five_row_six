package com.wx.no_five_row_six.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail")
public class EmailConfig {
    private  String smtp;
    private  String username;
    private  String password;
    private  String swtich;

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSwtich() {
        return swtich;
    }

    public void setSwtich(String swtich) {
        this.swtich = swtich;
    }
}
