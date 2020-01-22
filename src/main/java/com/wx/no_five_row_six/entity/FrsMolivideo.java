package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 影视作品表
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
public class FrsMolivideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fm_id", type = IdType.AUTO)
    private Long fmId;

    /**
     * 排序值
     */
    private Integer fmSort;

    /**
     * 类型 0:电影 1:电视剧 2:综艺
     */
    private Integer fmType;
    /**
     * 类型名称
     */
    private String fmTypeName;

    /**
     * 宣传剧照
     */
    private String fmImg;

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
    @TableField(exist = false)
    private String fmTimeStr;

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
    /**
     * 演员
     */
    private String fmActors;
    /**
     * 导演
     */
    private String fmDirector;
    /**
     * 作品名称
     */
    private String fmName;

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

    public Integer getFmType() {
        return fmType;
    }

    public void setFmType(Integer fmType) {
        this.fmType = fmType;
    }

    public String getFmImg() {
        return fmImg;
    }

    public void setFmImg(String fmImg) {
        this.fmImg = fmImg;
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

    public String getFmActors() {
        return fmActors;
    }

    public void setFmActors(String fmActors) {
        this.fmActors = fmActors;
    }

    public String getFmDirector() {
        return fmDirector;
    }

    public void setFmDirector(String fmDirector) {
        this.fmDirector = fmDirector;
    }

    public String getFmTypeName() {
        return fmTypeName;
    }

    public void setFmTypeName(String fmTypeName) {
        this.fmTypeName = fmTypeName;
    }

    public String getFmTimeStr() {
        return fmTimeStr;
    }

    public void setFmTimeStr(String fmTimeStr) {
        this.fmTimeStr = fmTimeStr;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    @Override
    public String toString() {
        return "FrsMolivideo{" +
                "fmId=" + fmId +
                ", fmSort=" + fmSort +
                ", fmType=" + fmType +
                ", fmTypeName='" + fmTypeName + '\'' +
                ", fmImg='" + fmImg + '\'' +
                ", fmVid='" + fmVid + '\'' +
                ", fmLikedNum=" + fmLikedNum +
                ", fmCollectionNum=" + fmCollectionNum +
                ", fmTime=" + fmTime +
                ", fmTimeStr='" + fmTimeStr + '\'' +
                ", fmCreateTime=" + fmCreateTime +
                ", fmUpdateTime=" + fmUpdateTime +
                ", fmIsValid=" + fmIsValid +
                ", fmActors='" + fmActors + '\'' +
                ", fmDirector='" + fmDirector + '\'' +
                ", fmName='" + fmName + '\'' +
                '}';
    }
}
