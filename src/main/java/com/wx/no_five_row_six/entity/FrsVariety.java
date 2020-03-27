package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 综艺表
 * </p>
 *
 * @author dxl
 * @since 2020-03-27
 */
public class FrsVariety implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fv_id", type = IdType.AUTO)
    private Long fvId;

    /**
     * 排序值
     */
    private Integer fvSort;

    /**
     * 作品id
     */
    private Long fvFmvId;

    /**
     * 综艺名
     */
    private String fvName;

    /**
     * 当前集数
     */
    private Integer fvIndex;

    /**
     * 视频id
     */
    private String fvVid;

    /**
     * 点赞数
     */
    private Integer fvLikedNum;

    /**
     * 收藏数
     */
    private Integer fvCollectionNum;

    /**
     * 播出时间
     */
    private Long fvTime;

    /**
     * 创建时间
     */
    private Long fvCreateTime;

    /**
     * 修改时间
     */
    private Long fvUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fvIsValid;

    /**
     * 标题
     */
    private String fvTitle;

    /**
     * 观看量
     */
    private Integer fvHitsNum;
    /**
     * 封面
     */
    private String fvImg;

    @TableField(exist = false)
    private String fvTimeStr;

    public Long getFvId() {
        return fvId;
    }

    public void setFvId(Long fvId) {
        this.fvId = fvId;
    }

    public Integer getFvSort() {
        return fvSort;
    }

    public void setFvSort(Integer fvSort) {
        this.fvSort = fvSort;
    }

    public Long getFvFmvId() {
        return fvFmvId;
    }

    public void setFvFmvId(Long fvFmvId) {
        this.fvFmvId = fvFmvId;
    }

    public String getFvName() {
        return fvName;
    }

    public void setFvName(String fvName) {
        this.fvName = fvName;
    }

    public Integer getFvIndex() {
        return fvIndex;
    }

    public void setFvIndex(Integer fvIndex) {
        this.fvIndex = fvIndex;
    }

    public String getFvVid() {
        return fvVid;
    }

    public void setFvVid(String fvVid) {
        this.fvVid = fvVid;
    }

    public Integer getFvLikedNum() {
        return fvLikedNum;
    }

    public void setFvLikedNum(Integer fvLikedNum) {
        this.fvLikedNum = fvLikedNum;
    }

    public Integer getFvCollectionNum() {
        return fvCollectionNum;
    }

    public void setFvCollectionNum(Integer fvCollectionNum) {
        this.fvCollectionNum = fvCollectionNum;
    }

    public Long getFvTime() {
        return fvTime;
    }

    public void setFvTime(Long fvTime) {
        this.fvTime = fvTime;
    }

    public Long getFvCreateTime() {
        return fvCreateTime;
    }

    public void setFvCreateTime(Long fvCreateTime) {
        this.fvCreateTime = fvCreateTime;
    }

    public Long getFvUpdateTime() {
        return fvUpdateTime;
    }

    public void setFvUpdateTime(Long fvUpdateTime) {
        this.fvUpdateTime = fvUpdateTime;
    }

    public Integer getFvIsValid() {
        return fvIsValid;
    }

    public void setFvIsValid(Integer fvIsValid) {
        this.fvIsValid = fvIsValid;
    }

    public String getFvTitle() {
        return fvTitle;
    }

    public void setFvTitle(String fvTitle) {
        this.fvTitle = fvTitle;
    }

    public Integer getFvHitsNum() {
        return fvHitsNum;
    }

    public void setFvHitsNum(Integer fvHitsNum) {
        this.fvHitsNum = fvHitsNum;
    }

    public String getFvImg() {
        return fvImg;
    }

    public void setFvImg(String fvImg) {
        this.fvImg = fvImg;
    }

    public String getFvTimeStr() {
        return fvTimeStr;
    }

    public void setFvTimeStr(String fvTimeStr) {
        this.fvTimeStr = fvTimeStr;
    }

    @Override
    public String toString() {
        return "FrsVariety{" +
                "fvId=" + fvId +
                ", fvSort=" + fvSort +
                ", fvFmvId=" + fvFmvId +
                ", fvName='" + fvName + '\'' +
                ", fvIndex=" + fvIndex +
                ", fvVid='" + fvVid + '\'' +
                ", fvLikedNum=" + fvLikedNum +
                ", fvCollectionNum=" + fvCollectionNum +
                ", fvTime=" + fvTime +
                ", fvCreateTime=" + fvCreateTime +
                ", fvUpdateTime=" + fvUpdateTime +
                ", fvIsValid=" + fvIsValid +
                ", fvTitle='" + fvTitle + '\'' +
                ", fvHitsNum=" + fvHitsNum +
                ", fvImg='" + fvImg + '\'' +
                ", fvTimeStr='" + fvTimeStr + '\'' +
                '}';
    }
}
