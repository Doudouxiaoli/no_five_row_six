package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 电影表
 * </p>
 *
 * @author dxl
 * @since 2020-01-23
 */
public class FrsMovie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fm_id", type = IdType.AUTO)
    private Long fmId;

    /**
     * 排序值
     */
    private Integer fmSort;

    /**
     * 作品id
     */
    private Long fmFmvId;

    /**
     * 电影名
     */
    private String fmName;

    /**
     * 视频id
     */
    private String fmVid;

    /**
     * 点赞数
     */
    private Integer fmLikedNum;

    /**
     * 收藏数
     */
    private Integer fmCollectionNum;

    /**
     * 播出时间
     */
    private Long fmTime;

    /**
     * 创建时间
     */
    private Long fmCreateTime;

    /**
     * 修改时间
     */
    private Long fmUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fmIsValid;

    @TableField(exist = false)
    private String fmTimeStr;

    public Long getFmId() {
        return fmId;
    }

    public void setFmId(Long fmId) {
        this.fmId = fmId;
    }

    public Integer getFmSort() {
        return fmSort;
    }

    public void setFmSort(Integer fmSort) {
        this.fmSort = fmSort;
    }

    public Long getFmFmvId() {
        return fmFmvId;
    }

    public void setFmFmvId(Long fmFmvId) {
        this.fmFmvId = fmFmvId;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    public String getFmVid() {
        return fmVid;
    }

    public void setFmVid(String fmVid) {
        this.fmVid = fmVid;
    }

    public Integer getFmLikedNum() {
        return fmLikedNum;
    }

    public void setFmLikedNum(Integer fmLikedNum) {
        this.fmLikedNum = fmLikedNum;
    }

    public Integer getFmCollectionNum() {
        return fmCollectionNum;
    }

    public void setFmCollectionNum(Integer fmCollectionNum) {
        this.fmCollectionNum = fmCollectionNum;
    }

    public Long getFmTime() {
        return fmTime;
    }

    public void setFmTime(Long fmTime) {
        this.fmTime = fmTime;
    }

    public Long getFmCreateTime() {
        return fmCreateTime;
    }

    public void setFmCreateTime(Long fmCreateTime) {
        this.fmCreateTime = fmCreateTime;
    }

    public Long getFmUpdateTime() {
        return fmUpdateTime;
    }

    public void setFmUpdateTime(Long fmUpdateTime) {
        this.fmUpdateTime = fmUpdateTime;
    }

    public Integer getFmIsValid() {
        return fmIsValid;
    }

    public void setFmIsValid(Integer fmIsValid) {
        this.fmIsValid = fmIsValid;
    }

    public String getFmTimeStr() {
        return fmTimeStr;
    }

    public void setFmTimeStr(String fmTimeStr) {
        this.fmTimeStr = fmTimeStr;
    }

    @Override
    public String toString() {
        return "FrsMovie{" +
                "fmId=" + fmId +
                ", fmSort=" + fmSort +
                ", fmFmvId=" + fmFmvId +
                ", fmName='" + fmName + '\'' +
                ", fmVid='" + fmVid + '\'' +
                ", fmLikedNum=" + fmLikedNum +
                ", fmCollectionNum=" + fmCollectionNum +
                ", fmTime=" + fmTime +
                ", fmCreateTime=" + fmCreateTime +
                ", fmUpdateTime=" + fmUpdateTime +
                ", fmIsValid=" + fmIsValid +
                ", fmTimeStr='" + fmTimeStr + '\'' +
                '}';
    }
}
