package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 演唱会表
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
public class FrsConcert implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fc_id", type = IdType.AUTO)
    private Long fcId;

    /**
     * 排序值
     */
    private Integer fcSort;

    /**
     * 演唱会主题
     */
    private String fcTitle;

    /**
     * 演唱会地点
     */
    private String fcAddress;

    /**
     * 宣传图
     */
    private String fcImg;

    /**
     * 介绍内容
     */
    private String fcContent;

    /**
     * 点赞数
     */
    private Integer fcLikedNum;

    /**
     * 收藏数
     */
    private Integer fcCollectionNum;

    /**
     * 演出时间
     */
    private Long fcTime;
    /**
     * 音源类型 0:现场 1:线上
     */
    private Integer fcSongType;
    /**
     * 演唱曲目
     */
    private String fcSongs;
    /**
     * 场馆
     */
    private String fcVenue;
    /**
     * 创建时间
     */
    private Long fcCreateTime;

    /**
     * 修改时间
     */
    private Long fcUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fcIsValid;
    /**
     * 字符串转化
     */
    @TableField(exist = false)
    private String fcTimeStr;

    public Long getFcId() {
        return fcId;
    }

    public void setFcId(Long fcId) {
        this.fcId = fcId;
    }

    public Integer getFcSort() {
        return fcSort;
    }

    public void setFcSort(Integer fcSort) {
        this.fcSort = fcSort;
    }

    public String getFcTitle() {
        return fcTitle;
    }

    public void setFcTitle(String fcTitle) {
        this.fcTitle = fcTitle;
    }

    public String getFcAddress() {
        return fcAddress;
    }

    public void setFcAddress(String fcAddress) {
        this.fcAddress = fcAddress;
    }

    public String getFcImg() {
        return fcImg;
    }

    public void setFcImg(String fcImg) {
        this.fcImg = fcImg;
    }


    public String getFcContent() {
        return fcContent;
    }

    public void setFcContent(String fcContent) {
        this.fcContent = fcContent;
    }

    public Integer getFcLikedNum() {
        return fcLikedNum;
    }

    public void setFcLikedNum(Integer fcLikedNum) {
        this.fcLikedNum = fcLikedNum;
    }

    public Integer getFcCollectionNum() {
        return fcCollectionNum;
    }

    public void setFcCollectionNum(Integer fcCollectionNum) {
        this.fcCollectionNum = fcCollectionNum;
    }

    public Long getFcTime() {
        return fcTime;
    }

    public void setFcTime(Long fcTime) {
        this.fcTime = fcTime;
    }

    public Long getFcCreateTime() {
        return fcCreateTime;
    }

    public void setFcCreateTime(Long fcCreateTime) {
        this.fcCreateTime = fcCreateTime;
    }

    public Long getFcUpdateTime() {
        return fcUpdateTime;
    }

    public void setFcUpdateTime(Long fcUpdateTime) {
        this.fcUpdateTime = fcUpdateTime;
    }

    public Integer getFcIsValid() {
        return fcIsValid;
    }

    public void setFcIsValid(Integer fcIsValid) {
        this.fcIsValid = fcIsValid;
    }

    public Integer getFcSongType() {
        return fcSongType;
    }

    public void setFcSongType(Integer fcSongType) {
        this.fcSongType = fcSongType;
    }

    public String getFcSongs() {
        return fcSongs;
    }

    public void setFcSongs(String fcSongs) {
        this.fcSongs = fcSongs;
    }

    public String getFcVenue() {
        return fcVenue;
    }

    public void setFcVenue(String fcVenue) {
        this.fcVenue = fcVenue;
    }

    public String getFcTimeStr() {
        return fcTimeStr;
    }

    public void setFcTimeStr(String fcTimeStr) {
        this.fcTimeStr = fcTimeStr;
    }

    @Override
    public String toString() {
        return "FrsConcert{" +
                "fcId=" + fcId +
                ", fcSort=" + fcSort +
                ", fcTitle='" + fcTitle + '\'' +
                ", fcAddress='" + fcAddress + '\'' +
                ", fcImg='" + fcImg + '\'' +
                ", fcContent='" + fcContent + '\'' +
                ", fcLikedNum=" + fcLikedNum +
                ", fcCollectionNum=" + fcCollectionNum +
                ", fcTime=" + fcTime +
                ", fcSongType=" + fcSongType +
                ", fcSongs='" + fcSongs + '\'' +
                ", fcVenue='" + fcVenue + '\'' +
                ", fcCreateTime=" + fcCreateTime +
                ", fcUpdateTime=" + fcUpdateTime +
                ", fcIsValid=" + fcIsValid +
                ", fcTimeStr='" + fcTimeStr + '\'' +
                '}';
    }
}
