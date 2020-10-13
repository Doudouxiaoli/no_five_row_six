package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "su_id", type = IdType.AUTO)
    private Long suId;

    /**
     * 姓名
     */
    private String suName;

    /**
     * 登录名
     */
    private String suLoginName;

    /**
     * 密码
     */
    private String suPassword;

    /**
     * 创建时间
     */
    private Long suCreateTime;

    /**
     * 修改时间
     */
    private Long suUpdateTime;
    /**
     * 头像
     */
    private String suHeadImg;

    public Long getSuId() {
        return suId;
    }

    public void setSuId(Long suId) {
        this.suId = suId;
    }

    public String getSuName() {
        return suName;
    }

    public void setSuName(String suName) {
        this.suName = suName;
    }

    public String getSuLoginName() {
        return suLoginName;
    }

    public void setSuLoginName(String suLoginName) {
        this.suLoginName = suLoginName;
    }

    public String getSuPassword() {
        return suPassword;
    }

    public void setSuPassword(String suPassword) {
        this.suPassword = suPassword;
    }

    public Long getSuCreateTime() {
        return suCreateTime;
    }

    public void setSuCreateTime(Long suCreateTime) {
        this.suCreateTime = suCreateTime;
    }

    public Long getSuUpdateTime() {
        return suUpdateTime;
    }

    public void setSuUpdateTime(Long suUpdateTime) {
        this.suUpdateTime = suUpdateTime;
    }

    public String getSuHeadImg() {
        return suHeadImg;
    }

    public void setSuHeadImg(String suHeadImg) {
        this.suHeadImg = suHeadImg;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "suId=" + suId +
                ", suName='" + suName + '\'' +
                ", suLoginName='" + suLoginName + '\'' +
                ", suPassword='" + suPassword + '\'' +
                ", suCreateTime=" + suCreateTime +
                ", suUpdateTime=" + suUpdateTime +
                ", suHeadImg='" + suHeadImg + '\'' +
                '}';
    }
}
