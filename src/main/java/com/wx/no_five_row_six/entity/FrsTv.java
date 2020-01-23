package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 电视剧表
 * </p>
 *
 * @author dxl
 * @since 2020-01-23
 */
public class FrsTv implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ft_id", type = IdType.AUTO)
    private Long ftId;

    /**
     * 排序值
     */
    private Integer ftSort;

    /**
     * 作品id
     */
    private Long ftFmvId;

    /**
     * 电视剧名
     */
    private String ftName;

    /**
     * 当前集数
     */
    private Integer ftIndex;

    /**
     * 视频id
     */
    private String ftVid;

    /**
     * 点赞数
     */
    private Integer ftLikedNum;

    /**
     * 收藏数
     */
    private Integer ftCollectionNum;

    /**
     * 播出时间
     */
    private Long ftTime;

    /**
     * 创建时间
     */
    private Long ftCreateTime;

    /**
     * 修改时间
     */
    private Long ftUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer ftIsValid;

    @TableField(exist = false)
    private String ftTimeStr;

    public Long getFtId() {
        return ftId;
    }

    public void setFtId(Long ftId) {
        this.ftId = ftId;
    }

    public Integer getFtSort() {
        return ftSort;
    }

    public void setFtSort(Integer ftSort) {
        this.ftSort = ftSort;
    }

    public Long getFtFmvId() {
        return ftFmvId;
    }

    public void setFtFmvId(Long ftFmvId) {
        this.ftFmvId = ftFmvId;
    }

    public String getFtName() {
        return ftName;
    }

    public void setFtName(String ftName) {
        this.ftName = ftName;
    }

    public Integer getFtIndex() {
        return ftIndex;
    }

    public void setFtIndex(Integer ftIndex) {
        this.ftIndex = ftIndex;
    }

    public String getFtVid() {
        return ftVid;
    }

    public void setFtVid(String ftVid) {
        this.ftVid = ftVid;
    }

    public Integer getFtLikedNum() {
        return ftLikedNum;
    }

    public void setFtLikedNum(Integer ftLikedNum) {
        this.ftLikedNum = ftLikedNum;
    }

    public Integer getFtCollectionNum() {
        return ftCollectionNum;
    }

    public void setFtCollectionNum(Integer ftCollectionNum) {
        this.ftCollectionNum = ftCollectionNum;
    }

    public Long getFtTime() {
        return ftTime;
    }

    public void setFtTime(Long ftTime) {
        this.ftTime = ftTime;
    }

    public Long getFtCreateTime() {
        return ftCreateTime;
    }

    public void setFtCreateTime(Long ftCreateTime) {
        this.ftCreateTime = ftCreateTime;
    }

    public Long getFtUpdateTime() {
        return ftUpdateTime;
    }

    public void setFtUpdateTime(Long ftUpdateTime) {
        this.ftUpdateTime = ftUpdateTime;
    }

    public Integer getFtIsValid() {
        return ftIsValid;
    }

    public void setFtIsValid(Integer ftIsValid) {
        this.ftIsValid = ftIsValid;
    }

    public String getFtTimeStr() {
        return ftTimeStr;
    }

    public void setFtTimeStr(String ftTimeStr) {
        this.ftTimeStr = ftTimeStr;
    }

    @Override
    public String toString() {
        return "FrsTv{" +
                "ftId=" + ftId +
                ", ftSort=" + ftSort +
                ", ftFmvId=" + ftFmvId +
                ", ftName='" + ftName + '\'' +
                ", ftIndex=" + ftIndex +
                ", ftVid='" + ftVid + '\'' +
                ", ftLikedNum=" + ftLikedNum +
                ", ftCollectionNum=" + ftCollectionNum +
                ", ftTime=" + ftTime +
                ", ftCreateTime=" + ftCreateTime +
                ", ftUpdateTime=" + ftUpdateTime +
                ", ftIsValid=" + ftIsValid +
                ", ftTimeStr='" + ftTimeStr + '\'' +
                '}';
    }
}
