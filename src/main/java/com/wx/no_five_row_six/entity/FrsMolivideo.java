package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 影视作品表
 * </p>
 *
 * @author dxl
 * @since 2020-01-23
 */
public class FrsMolivideo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fmv_id", type = IdType.AUTO)
    private Long fmvId;

    /**
     * 排序值
     */
    private Integer fmvSort;

    /**
     * 类型 0:电影 1:电视剧 2:综艺
     */
    private Integer fmvType;

    /**
     * 类型名称
     */
    private String fmvTypeName;

    /**
     * 作品名称
     */
    private String fmvName;

    /**
     * 演员表
     */
    private String fmvActors;

    /**
     * 导演姓名
     */
    private String fmvDirector;

    /**
     * 宣传照
     */
    private String fmvImg;

    /**
     * 点赞数
     */
    private Integer fmvLikedNum;

    /**
     * 收藏数
     */
    private Integer fmvCollectionNum;

    /**
     * 创建时间
     */
    private Long fmvCreateTime;

    /**
     * 修改时间
     */
    private Long fmvUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fmvIsValid;


    public Long getFmvId() {
        return fmvId;
    }

    public void setFmvId(Long fmvId) {
        this.fmvId = fmvId;
    }

    public Integer getFmvSort() {
        return fmvSort;
    }

    public void setFmvSort(Integer fmvSort) {
        this.fmvSort = fmvSort;
    }

    public Integer getFmvType() {
        return fmvType;
    }

    public void setFmvType(Integer fmvType) {
        this.fmvType = fmvType;
    }

    public String getFmvTypeName() {
        return fmvTypeName;
    }

    public void setFmvTypeName(String fmvTypeName) {
        this.fmvTypeName = fmvTypeName;
    }

    public String getFmvName() {
        return fmvName;
    }

    public void setFmvName(String fmvName) {
        this.fmvName = fmvName;
    }

    public String getFmvActors() {
        return fmvActors;
    }

    public void setFmvActors(String fmvActors) {
        this.fmvActors = fmvActors;
    }

    public String getFmvDirector() {
        return fmvDirector;
    }

    public void setFmvDirector(String fmvDirector) {
        this.fmvDirector = fmvDirector;
    }

    public String getFmvImg() {
        return fmvImg;
    }

    public void setFmvImg(String fmvImg) {
        this.fmvImg = fmvImg;
    }

    public Integer getFmvLikedNum() {
        return fmvLikedNum;
    }

    public void setFmvLikedNum(Integer fmvLikedNum) {
        this.fmvLikedNum = fmvLikedNum;
    }

    public Integer getFmvCollectionNum() {
        return fmvCollectionNum;
    }

    public void setFmvCollectionNum(Integer fmvCollectionNum) {
        this.fmvCollectionNum = fmvCollectionNum;
    }

    public Long getFmvCreateTime() {
        return fmvCreateTime;
    }

    public void setFmvCreateTime(Long fmvCreateTime) {
        this.fmvCreateTime = fmvCreateTime;
    }

    public Long getFmvUpdateTime() {
        return fmvUpdateTime;
    }

    public void setFmvUpdateTime(Long fmvUpdateTime) {
        this.fmvUpdateTime = fmvUpdateTime;
    }

    public Integer getFmvIsValid() {
        return fmvIsValid;
    }

    public void setFmvIsValid(Integer fmvIsValid) {
        this.fmvIsValid = fmvIsValid;
    }

    @Override
    public String toString() {
        return "FrsMolivideo{" +
        "fmvId=" + fmvId +
        ", fmvSort=" + fmvSort +
        ", fmvType=" + fmvType +
        ", fmvTypeName=" + fmvTypeName +
        ", fmvName=" + fmvName +
        ", fmvActors=" + fmvActors +
        ", fmvDirector=" + fmvDirector +
        ", fmvImg=" + fmvImg +
        ", fmvLikedNum=" + fmvLikedNum +
        ", fmvCollectionNum=" + fmvCollectionNum +
        ", fmvCreateTime=" + fmvCreateTime +
        ", fmvUpdateTime=" + fmvUpdateTime +
        ", fmvIsValid=" + fmvIsValid +
        "}";
    }
}
