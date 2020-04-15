// ======================================
// Project Name:meddb-starter
// Package Name:com.wx.no_five_row_six
// File Name:SystemConfig.java
// Create Date:2019年10月16日  15:54
// ======================================
package com.wx.no_five_row_six.config;

import com.wx.no_five_row_six.common.Const;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李旭光
 * @version 2019年10月16日  15:54
 */
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    private String domain;

    private String version;

    private boolean debug;

    private String environment;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
        Const.SYS_DOMAIN=domain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
        Const.SYS_ENVIRONMENT=environment;
    }
}