package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 访问记录日志表
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public class FrsZyxVisitLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "zvl_id", type = IdType.AUTO)
    private Long zvlId;

    /**
     * 用户主键
     */
    private Long zvlUserId;

    /**
     * 访问页面开始时间
     */
    private Long zvlStartTime;

    /**
     * 访问页面结束时间
     */
    private Long zvlEndTime;

    /**
     * 访问页面时长(秒)
     */
    private Long zvlDuration;

    /**
     * 观看模块
     */
    private String zvlVisitModule;

    /**
     * 资讯所属模块
     */
    private String zvlNewsModule;

    /**
     * 资讯主键
     */
    private Long zvlNewsId;

    /**
     * 是否包含视频(0：不包含，1：包含)
     */
    private Integer zvlIncludeVideo;

    /**
     * ip地址
     */
    private String zvlIp;

    /**
     * 是否点赞(0：未点赞；1: 点赞；2：取消点赞)
     */
    private Integer zvlLike;

    /**
     * 点赞时间/取消点赞时间
     */
    private Long zvlLikeTime;

    /**
     * 是否收藏(0：未收藏；1: 收藏；2：取消收藏)
     */
    private Integer zvlFavorite;

    /**
     * 收藏时间/取消收藏时间
     */
    private Long zvlFavoriteTime;

    /**
     * 视频观看记录主键
     */
    private Long zvlVrId;


    public Long getZvlId() {
        return zvlId;
    }

    public void setZvlId(Long zvlId) {
        this.zvlId = zvlId;
    }

    public Long getZvlUserId() {
        return zvlUserId;
    }

    public void setZvlUserId(Long zvlUserId) {
        this.zvlUserId = zvlUserId;
    }

    public Long getZvlStartTime() {
        return zvlStartTime;
    }

    public void setZvlStartTime(Long zvlStartTime) {
        this.zvlStartTime = zvlStartTime;
    }

    public Long getZvlEndTime() {
        return zvlEndTime;
    }

    public void setZvlEndTime(Long zvlEndTime) {
        this.zvlEndTime = zvlEndTime;
    }

    public Long getZvlDuration() {
        return zvlDuration;
    }

    public void setZvlDuration(Long zvlDuration) {
        this.zvlDuration = zvlDuration;
    }

    public String getZvlVisitModule() {
        return zvlVisitModule;
    }

    public void setZvlVisitModule(String zvlVisitModule) {
        this.zvlVisitModule = zvlVisitModule;
    }

    public String getZvlNewsModule() {
        return zvlNewsModule;
    }

    public void setZvlNewsModule(String zvlNewsModule) {
        this.zvlNewsModule = zvlNewsModule;
    }

    public Long getZvlNewsId() {
        return zvlNewsId;
    }

    public void setZvlNewsId(Long zvlNewsId) {
        this.zvlNewsId = zvlNewsId;
    }

    public Integer getZvlIncludeVideo() {
        return zvlIncludeVideo;
    }

    public void setZvlIncludeVideo(Integer zvlIncludeVideo) {
        this.zvlIncludeVideo = zvlIncludeVideo;
    }

    public String getZvlIp() {
        return zvlIp;
    }

    public void setZvlIp(String zvlIp) {
        this.zvlIp = zvlIp;
    }

    public Integer getZvlLike() {
        return zvlLike;
    }

    public void setZvlLike(Integer zvlLike) {
        this.zvlLike = zvlLike;
    }

    public Long getZvlLikeTime() {
        return zvlLikeTime;
    }

    public void setZvlLikeTime(Long zvlLikeTime) {
        this.zvlLikeTime = zvlLikeTime;
    }

    public Integer getZvlFavorite() {
        return zvlFavorite;
    }

    public void setZvlFavorite(Integer zvlFavorite) {
        this.zvlFavorite = zvlFavorite;
    }

    public Long getZvlFavoriteTime() {
        return zvlFavoriteTime;
    }

    public void setZvlFavoriteTime(Long zvlFavoriteTime) {
        this.zvlFavoriteTime = zvlFavoriteTime;
    }

    public Long getZvlVrId() {
        return zvlVrId;
    }

    public void setZvlVrId(Long zvlVrId) {
        this.zvlVrId = zvlVrId;
    }

    @Override
    public String toString() {
        return "FrsZyxVisitLog{" +
        "zvlId=" + zvlId +
        ", zvlUserId=" + zvlUserId +
        ", zvlStartTime=" + zvlStartTime +
        ", zvlEndTime=" + zvlEndTime +
        ", zvlDuration=" + zvlDuration +
        ", zvlVisitModule=" + zvlVisitModule +
        ", zvlNewsModule=" + zvlNewsModule +
        ", zvlNewsId=" + zvlNewsId +
        ", zvlIncludeVideo=" + zvlIncludeVideo +
        ", zvlIp=" + zvlIp +
        ", zvlLike=" + zvlLike +
        ", zvlLikeTime=" + zvlLikeTime +
        ", zvlFavorite=" + zvlFavorite +
        ", zvlFavoriteTime=" + zvlFavoriteTime +
        ", zvlVrId=" + zvlVrId +
        "}";
    }
}
