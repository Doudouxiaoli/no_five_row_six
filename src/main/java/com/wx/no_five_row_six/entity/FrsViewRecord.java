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
    private String fvlIp;

    /**
     * 是否点赞(0：未点赞；1: 点赞)
     */
    private Integer fvlLike;

    /**
     * 点赞时间/取消点赞时间
     */
    private Long fvlLikeTime;

    /**
     * 是否收藏(0：未收藏；1: 收藏)
     */
    private Integer fvlFavorite;

    /**
     * 收藏时间/取消收藏时间
     */
    private Long fvlFavoriteTime;


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

    public String getFvlIp() {
        return fvlIp;
    }

    public void setFvlIp(String fvlIp) {
        this.fvlIp = fvlIp;
    }

    public Integer getFvlLike() {
        return fvlLike;
    }

    public void setFvlLike(Integer fvlLike) {
        this.fvlLike = fvlLike;
    }

    public Long getFvlLikeTime() {
        return fvlLikeTime;
    }

    public void setFvlLikeTime(Long fvlLikeTime) {
        this.fvlLikeTime = fvlLikeTime;
    }

    public Integer getFvlFavorite() {
        return fvlFavorite;
    }

    public void setFvlFavorite(Integer fvlFavorite) {
        this.fvlFavorite = fvlFavorite;
    }

    public Long getFvlFavoriteTime() {
        return fvlFavoriteTime;
    }

    public void setFvlFavoriteTime(Long fvlFavoriteTime) {
        this.fvlFavoriteTime = fvlFavoriteTime;
    }

    @Override
    public String toString() {
        return "FrsViewRecord{" +
        "fvrId=" + fvrId +
        ", fvrUserId=" + fvrUserId +
        ", fvrContentId=" + fvrContentId +
        ", fvrContentName=" + fvrContentName +
        ", fvrTypeName=" + fvrTypeName +
        ", fvrTime=" + fvrTime +
        ", fvrTypeId=" + fvrTypeId +
        ", fvlIp=" + fvlIp +
        ", fvlLike=" + fvlLike +
        ", fvlLikeTime=" + fvlLikeTime +
        ", fvlFavorite=" + fvlFavorite +
        ", fvlFavoriteTime=" + fvlFavoriteTime +
        "}";
    }
}
