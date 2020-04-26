package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 浏览记录表
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
public class FrsViewRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fvr_id", type = IdType.AUTO)
    private Long fvrId;

    /**
     * 用户主键
     */
    private Long fvrUserId;

    /**
     * 内容id
     */
    private Integer fvrContentId;

    /**
     * 题目
     */
    private String fvrContentName;

    /**
     * 内容来源
     */
    private String fvrTypeName;

    /**
     * 浏览时间
     */
    private Long fvrTime;

    /**
     * 模板id
     */
    private Integer fvrTypeId;

    /**
     * ip地址
     */
    private String fvrIp;

    /**
     * 是否点赞(0：未点赞；1: 点赞)
     */
    private Integer fvrLike;

    /**
     * 点赞时间/取消点赞时间
     */
    private Long fvrLikeTime;

    /**
     * 是否收藏(0：未收藏；1: 收藏)
     */
    private Integer fvrFavorite;

    /**
     * 收藏时间/取消收藏时间
     */
    private Long fvrFavoriteTime;


    public Long getFvrId() {
        return fvrId;
    }

    public void setFvrId(Long fvrId) {
        this.fvrId = fvrId;
    }

    public Long getFvrUserId() {
        return fvrUserId;
    }

    public void setFvrUserId(Long fvrUserId) {
        this.fvrUserId = fvrUserId;
    }

    public Integer getFvrContentId() {
        return fvrContentId;
    }

    public void setFvrContentId(Integer fvrContentId) {
        this.fvrContentId = fvrContentId;
    }

    public String getFvrContentName() {
        return fvrContentName;
    }

    public void setFvrContentName(String fvrContentName) {
        this.fvrContentName = fvrContentName;
    }

    public String getFvrTypeName() {
        return fvrTypeName;
    }

    public void setFvrTypeName(String fvrTypeName) {
        this.fvrTypeName = fvrTypeName;
    }

    public Long getFvrTime() {
        return fvrTime;
    }

    public void setFvrTime(Long fvrTime) {
        this.fvrTime = fvrTime;
    }

    public Integer getFvrTypeId() {
        return fvrTypeId;
    }

    public void setFvrTypeId(Integer fvrTypeId) {
        this.fvrTypeId = fvrTypeId;
    }

    public String getFvrIp() {
        return fvrIp;
    }

    public void setFvrIp(String fvrIp) {
        this.fvrIp = fvrIp;
    }

    public Integer getFvrLike() {
        return fvrLike;
    }

    public void setFvrLike(Integer fvrLike) {
        this.fvrLike = fvrLike;
    }

    public Long getFvrLikeTime() {
        return fvrLikeTime;
    }

    public void setFvrLikeTime(Long fvrLikeTime) {
        this.fvrLikeTime = fvrLikeTime;
    }

    public Integer getFvrFavorite() {
        return fvrFavorite;
    }

    public void setFvrFavorite(Integer fvrFavorite) {
        this.fvrFavorite = fvrFavorite;
    }

    public Long getFvrFavoriteTime() {
        return fvrFavoriteTime;
    }

    public void setFvrFavoriteTime(Long fvrFavoriteTime) {
        this.fvrFavoriteTime = fvrFavoriteTime;
    }

    @Override
    public String toString() {
        return "FrsViewRecord{" +
                "fvrId=" + fvrId +
                ", fvrUserId=" + fvrUserId +
                ", fvrContentId=" + fvrContentId +
                ", fvrContentName='" + fvrContentName + '\'' +
                ", fvrTypeName='" + fvrTypeName + '\'' +
                ", fvrTime=" + fvrTime +
                ", fvrTypeId=" + fvrTypeId +
                ", fvrIp='" + fvrIp + '\'' +
                ", fvrLike=" + fvrLike +
                ", fvrLikeTime=" + fvrLikeTime +
                ", fvrFavorite=" + fvrFavorite +
                ", fvrFavoriteTime=" + fvrFavoriteTime +
                '}';
    }
}
