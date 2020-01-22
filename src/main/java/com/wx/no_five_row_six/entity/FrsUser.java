package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
public class FrsUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fu_id", type = IdType.AUTO)
    private Long fuId;

    /**
     * 登录名
     */
    private String fuName;

    /**
     * 密码
     */
    private String fuPassword;

    /**
     * 创建时间
     */
    private Long fuCreateTime;

    /**
     * 修改时间
     */
    private Long fuUpdateTime;


    public Long getFuId() {
        return fuId;
    }

    public void setFuId(Long fuId) {
        this.fuId = fuId;
    }

    public String getFuName() {
        return fuName;
    }

    public void setFuName(String fuName) {
        this.fuName = fuName;
    }

    public String getFuPassword() {
        return fuPassword;
    }

    public void setFuPassword(String fuPassword) {
        this.fuPassword = fuPassword;
    }

    public Long getFuCreateTime() {
        return fuCreateTime;
    }

    public void setFuCreateTime(Long fuCreateTime) {
        this.fuCreateTime = fuCreateTime;
    }

    public Long getFuUpdateTime() {
        return fuUpdateTime;
    }

    public void setFuUpdateTime(Long fuUpdateTime) {
        this.fuUpdateTime = fuUpdateTime;
    }

    @Override
    public String toString() {
        return "FrsUser{" +
        "fuId=" + fuId +
        ", fuName=" + fuName +
        ", fuPassword=" + fuPassword +
        ", fuCreateTime=" + fuCreateTime +
        ", fuUpdateTime=" + fuUpdateTime +
        "}";
    }
}
