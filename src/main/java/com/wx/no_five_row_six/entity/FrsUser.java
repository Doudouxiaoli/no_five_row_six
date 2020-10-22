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
 * @since 2020-10-22
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
     * 手机号
     */
    private String fuPhone;

    /**
     * 省份id
     */
    private Long fuProvinceId;

    /**
     * 城市id
     */
    private Long fuCityId;

    /**
     * 区县id
     */
    private Long fuRegionId;

    /**
     * 省份
     */
    private String fuProvince;

    /**
     * 城市
     */
    private String fuCity;

    /**
     * 区县
     */
    private String fuRegion;

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

    public String getFuPhone() {
        return fuPhone;
    }

    public void setFuPhone(String fuPhone) {
        this.fuPhone = fuPhone;
    }

    public Long getFuProvinceId() {
        return fuProvinceId;
    }

    public void setFuProvinceId(Long fuProvinceId) {
        this.fuProvinceId = fuProvinceId;
    }

    public Long getFuCityId() {
        return fuCityId;
    }

    public void setFuCityId(Long fuCityId) {
        this.fuCityId = fuCityId;
    }

    public Long getFuRegionId() {
        return fuRegionId;
    }

    public void setFuRegionId(Long fuRegionId) {
        this.fuRegionId = fuRegionId;
    }

    public String getFuProvince() {
        return fuProvince;
    }

    public void setFuProvince(String fuProvince) {
        this.fuProvince = fuProvince;
    }

    public String getFuCity() {
        return fuCity;
    }

    public void setFuCity(String fuCity) {
        this.fuCity = fuCity;
    }

    public String getFuRegion() {
        return fuRegion;
    }

    public void setFuRegion(String fuRegion) {
        this.fuRegion = fuRegion;
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
        ", fuPhone=" + fuPhone +
        ", fuProvinceId=" + fuProvinceId +
        ", fuCityId=" + fuCityId +
        ", fuRegionId=" + fuRegionId +
        ", fuProvince=" + fuProvince +
        ", fuCity=" + fuCity +
        ", fuRegion=" + fuRegion +
        ", fuCreateTime=" + fuCreateTime +
        ", fuUpdateTime=" + fuUpdateTime +
        "}";
    }
}
