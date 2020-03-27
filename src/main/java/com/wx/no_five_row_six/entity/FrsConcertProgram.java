package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class FrsConcertProgram implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fcp_id", type = IdType.AUTO)
    private Long fcpId;

    /**
     * 排序值
     */
    private Integer fcpSort;

    /**
     * 演唱会id
     */
    private Long fcpFcId;

    /**
     * 表演曲目
     */
    private String fcpName;

    /**
     * 视频id
     */
    private String fcpVid;

    /**
     * 视频封图
     */
    private String fcpImg;

    /**
     * 收藏数
     */
    private Integer fcpCollectionNum;

    /**
     * 创建时间
     */
    private Long fcpCreateTime;

    /**
     * 修改时间
     */
    private Long fcpUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fcpIsValid;

    /**
     * 观看数
     */
    private Integer fcpHitsNum;


    public Long getFcpId() {
        return fcpId;
    }

    public void setFcpId(Long fcpId) {
        this.fcpId = fcpId;
    }

    public Integer getFcpSort() {
        return fcpSort;
    }

    public void setFcpSort(Integer fcpSort) {
        this.fcpSort = fcpSort;
    }

    public Long getFcpFcId() {
        return fcpFcId;
    }

    public void setFcpFcId(Long fcpFcId) {
        this.fcpFcId = fcpFcId;
    }

    public String getFcpName() {
        return fcpName;
    }

    public void setFcpName(String fcpName) {
        this.fcpName = fcpName;
    }

    public String getFcpVid() {
        return fcpVid;
    }

    public void setFcpVid(String fcpVid) {
        this.fcpVid = fcpVid;
    }

    public String getFcpImg() {
        return fcpImg;
    }

    public void setFcpImg(String fcpImg) {
        this.fcpImg = fcpImg;
    }

    public Integer getFcpCollectionNum() {
        return fcpCollectionNum;
    }

    public void setFcpCollectionNum(Integer fcpCollectionNum) {
        this.fcpCollectionNum = fcpCollectionNum;
    }

    public Long getFcpCreateTime() {
        return fcpCreateTime;
    }

    public void setFcpCreateTime(Long fcpCreateTime) {
        this.fcpCreateTime = fcpCreateTime;
    }

    public Long getFcpUpdateTime() {
        return fcpUpdateTime;
    }

    public void setFcpUpdateTime(Long fcpUpdateTime) {
        this.fcpUpdateTime = fcpUpdateTime;
    }

    public Integer getFcpIsValid() {
        return fcpIsValid;
    }

    public void setFcpIsValid(Integer fcpIsValid) {
        this.fcpIsValid = fcpIsValid;
    }

    public Integer getFcpHitsNum() {
        return fcpHitsNum;
    }

    public void setFcpHitsNum(Integer fcpHitsNum) {
        this.fcpHitsNum = fcpHitsNum;
    }

    @Override
    public String toString() {
        return "FrsConcertProgram{" +
        "fcpId=" + fcpId +
        ", fcpSort=" + fcpSort +
        ", fcpFcId=" + fcpFcId +
        ", fcpName=" + fcpName +
        ", fcpVid=" + fcpVid +
        ", fcpImg=" + fcpImg +
        ", fcpCollectionNum=" + fcpCollectionNum +
        ", fcpCreateTime=" + fcpCreateTime +
        ", fcpUpdateTime=" + fcpUpdateTime +
        ", fcpIsValid=" + fcpIsValid +
        ", fcpHitsNum=" + fcpHitsNum +
        "}";
    }
}
