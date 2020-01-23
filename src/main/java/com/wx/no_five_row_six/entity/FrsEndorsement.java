package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 代言表
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
public class FrsEndorsement implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fe_id", type = IdType.AUTO)
    private Long feId;

    /**
     * 排序值
     */
    private Integer feSort;

    /**
     * 类型 0:食物 1:美妆 2:服饰 3:轻奢品 4:游戏
     */
    private Integer feType;

    /**
     * 定妆图
     */
    private String feImg;

    /**
     * 宣传视频id
     */
    private String feVid;

    /**
     * 点赞数
     */
    private Integer feLikedNum;

    /**
     * 收藏数
     */
    private Integer feCollectionNum;

    /**
     * 代言开始时间
     */
    private Long feStartTime;

    /**
     * 代言结束时间
     */
    private Long feEndTime;

    /**
     * 创建时间
     */
    private Long feCreateTime;

    /**
     * 修改时间
     */
    private Long feUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer feIsValid;

    @TableField(exist = false)
    private String feTimeStr;
    public Long getFeId() {
        return feId;
    }

    public void setFeId(Long feId) {
        this.feId = feId;
    }

    public Integer getFeSort() {
        return feSort;
    }

    public void setFeSort(Integer feSort) {
        this.feSort = feSort;
    }

    public Integer getFeType() {
        return feType;
    }

    public void setFeType(Integer feType) {
        this.feType = feType;
    }

    public String getFeImg() {
        return feImg;
    }

    public void setFeImg(String feImg) {
        this.feImg = feImg;
    }

    public String getFeVid() {
        return feVid;
    }

    public void setFeVid(String feVid) {
        this.feVid = feVid;
    }

    public Integer getFeLikedNum() {
        return feLikedNum;
    }

    public void setFeLikedNum(Integer feLikedNum) {
        this.feLikedNum = feLikedNum;
    }

    public Integer getFeCollectionNum() {
        return feCollectionNum;
    }

    public void setFeCollectionNum(Integer feCollectionNum) {
        this.feCollectionNum = feCollectionNum;
    }

    public Long getFeStartTime() {
        return feStartTime;
    }

    public void setFeStartTime(Long feStartTime) {
        this.feStartTime = feStartTime;
    }

    public Long getFeEndTime() {
        return feEndTime;
    }

    public void setFeEndTime(Long feEndTime) {
        this.feEndTime = feEndTime;
    }

    public Long getFeCreateTime() {
        return feCreateTime;
    }

    public void setFeCreateTime(Long feCreateTime) {
        this.feCreateTime = feCreateTime;
    }

    public Long getFeUpdateTime() {
        return feUpdateTime;
    }

    public void setFeUpdateTime(Long feUpdateTime) {
        this.feUpdateTime = feUpdateTime;
    }

    public Integer getFeIsValid() {
        return feIsValid;
    }

    public void setFeIsValid(Integer feIsValid) {
        this.feIsValid = feIsValid;
    }

    public String getFeTimeStr() {
        return feTimeStr;
    }

    public void setFeTimeStr(String feTimeStr) {
        this.feTimeStr = feTimeStr;
    }

    @Override
    public String toString() {
        return "FrsEndorsement{" +
                "feId=" + feId +
                ", feSort=" + feSort +
                ", feType=" + feType +
                ", feImg='" + feImg + '\'' +
                ", feVid='" + feVid + '\'' +
                ", feLikedNum=" + feLikedNum +
                ", feCollectionNum=" + feCollectionNum +
                ", feStartTime=" + feStartTime +
                ", feEndTime=" + feEndTime +
                ", feCreateTime=" + feCreateTime +
                ", feUpdateTime=" + feUpdateTime +
                ", feIsValid=" + feIsValid +
                ", feTimeStr='" + feTimeStr + '\'' +
                '}';
    }
}
