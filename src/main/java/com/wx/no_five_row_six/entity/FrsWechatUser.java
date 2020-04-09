package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 微信用户表
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
public class FrsWechatUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "wu_id", type = IdType.AUTO)
    private Long wuId;

    /**
     * 用户id
     */
    private Long fwuUserId;

    /**
     * openid
     */
    private String fwuOpenid;

    /**
     * 昵称
     */
    private String fwuNickname;

    /**
     * 性别
     */
    private String fwuSex;

    /**
     * 城市
     */
    private String fwuCity;

    /**
     * 国家
     */
    private String fwuCountry;

    /**
     * 省份
     */
    private String fwuProvince;

    /**
     * 语言
     */
    private String fwuLanguage;

    /**
     * 头像
     */
    private String fwuHeadimgurl;

    /**
     * unionid
     */
    private String fwuUnionid;

    /**
     * 备注
     */
    private String fwuRemark;

    /**
     * 头像本地路径
     */
    private String fwuHeadimg;

    /**
     * 用户分组id
     */
    private String fwuGroupid;

    /**
     * 标签ID列表
     */
    private String fwuTagidList;

    /**
     * 是否订阅该公众号标识（0：未关注；1：关注）
     */
    private Integer fwuSubscribe;

    /**
     * 关注时间
     */
    private Long fwuSubscribeTime;

    /**
     * 创建时间
     */
    private Long fwuCreateTime;

    /**
     * 修改时间
     */
    private Long fwuUpdateTime;


    public Long getWuId() {
        return wuId;
    }

    public void setWuId(Long wuId) {
        this.wuId = wuId;
    }

    public Long getFwuUserId() {
        return fwuUserId;
    }

    public void setFwuUserId(Long fwuUserId) {
        this.fwuUserId = fwuUserId;
    }

    public String getFwuOpenid() {
        return fwuOpenid;
    }

    public void setFwuOpenid(String fwuOpenid) {
        this.fwuOpenid = fwuOpenid;
    }

    public String getFwuNickname() {
        return fwuNickname;
    }

    public void setFwuNickname(String fwuNickname) {
        this.fwuNickname = fwuNickname;
    }

    public String getFwuSex() {
        return fwuSex;
    }

    public void setFwuSex(String fwuSex) {
        this.fwuSex = fwuSex;
    }

    public String getFwuCity() {
        return fwuCity;
    }

    public void setFwuCity(String fwuCity) {
        this.fwuCity = fwuCity;
    }

    public String getFwuCountry() {
        return fwuCountry;
    }

    public void setFwuCountry(String fwuCountry) {
        this.fwuCountry = fwuCountry;
    }

    public String getFwuProvince() {
        return fwuProvince;
    }

    public void setFwuProvince(String fwuProvince) {
        this.fwuProvince = fwuProvince;
    }

    public String getFwuLanguage() {
        return fwuLanguage;
    }

    public void setFwuLanguage(String fwuLanguage) {
        this.fwuLanguage = fwuLanguage;
    }

    public String getFwuHeadimgurl() {
        return fwuHeadimgurl;
    }

    public void setFwuHeadimgurl(String fwuHeadimgurl) {
        this.fwuHeadimgurl = fwuHeadimgurl;
    }

    public String getFwuUnionid() {
        return fwuUnionid;
    }

    public void setFwuUnionid(String fwuUnionid) {
        this.fwuUnionid = fwuUnionid;
    }

    public String getFwuRemark() {
        return fwuRemark;
    }

    public void setFwuRemark(String fwuRemark) {
        this.fwuRemark = fwuRemark;
    }

    public String getFwuHeadimg() {
        return fwuHeadimg;
    }

    public void setFwuHeadimg(String fwuHeadimg) {
        this.fwuHeadimg = fwuHeadimg;
    }

    public String getFwuGroupid() {
        return fwuGroupid;
    }

    public void setFwuGroupid(String fwuGroupid) {
        this.fwuGroupid = fwuGroupid;
    }

    public String getFwuTagidList() {
        return fwuTagidList;
    }

    public void setFwuTagidList(String fwuTagidList) {
        this.fwuTagidList = fwuTagidList;
    }

    public Integer getFwuSubscribe() {
        return fwuSubscribe;
    }

    public void setFwuSubscribe(Integer fwuSubscribe) {
        this.fwuSubscribe = fwuSubscribe;
    }

    public Long getFwuSubscribeTime() {
        return fwuSubscribeTime;
    }

    public void setFwuSubscribeTime(Long fwuSubscribeTime) {
        this.fwuSubscribeTime = fwuSubscribeTime;
    }

    public Long getFwuCreateTime() {
        return fwuCreateTime;
    }

    public void setFwuCreateTime(Long fwuCreateTime) {
        this.fwuCreateTime = fwuCreateTime;
    }

    public Long getFwuUpdateTime() {
        return fwuUpdateTime;
    }

    public void setFwuUpdateTime(Long fwuUpdateTime) {
        this.fwuUpdateTime = fwuUpdateTime;
    }

    @Override
    public String toString() {
        return "FrsWechatUser{" +
        "wuId=" + wuId +
        ", fwuUserId=" + fwuUserId +
        ", fwuOpenid=" + fwuOpenid +
        ", fwuNickname=" + fwuNickname +
        ", fwuSex=" + fwuSex +
        ", fwuCity=" + fwuCity +
        ", fwuCountry=" + fwuCountry +
        ", fwuProvince=" + fwuProvince +
        ", fwuLanguage=" + fwuLanguage +
        ", fwuHeadimgurl=" + fwuHeadimgurl +
        ", fwuUnionid=" + fwuUnionid +
        ", fwuRemark=" + fwuRemark +
        ", fwuHeadimg=" + fwuHeadimg +
        ", fwuGroupid=" + fwuGroupid +
        ", fwuTagidList=" + fwuTagidList +
        ", fwuSubscribe=" + fwuSubscribe +
        ", fwuSubscribeTime=" + fwuSubscribeTime +
        ", fwuCreateTime=" + fwuCreateTime +
        ", fwuUpdateTime=" + fwuUpdateTime +
        "}";
    }
}
