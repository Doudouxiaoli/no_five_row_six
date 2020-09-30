package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 通用表
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public class FrsZyxNews implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "zn_id", type = IdType.AUTO)
    private Long znId;

    /**
     * 模板id
     */
    private Long znNcId;

    /**
     * 来源id
     */
    private Long znFromId;

    /**
     * 来源名称
     */
    private String znFrom;

    /**
     * 当前期数
     */
    private Long znPace;

    /**
     * 标题
     */
    private String znTitle;

    /**
     * 地点
     */
    private String znAddress;

    /**
     * 备用名称1
     */
    private String znTitleOne;

    /**
     * 备用名称2
     */
    private String znTitleTwo;

    /**
     * 缩略图路径
     */
    private String znThumbnailPath;

    /**
     * 轮播图路径
     */
    private String znCarouselPath;

    /**
     * 简介
     */
    private String znIntro;

    /**
     * 内容
     */
    private String znContent;

    /**
     * 视频路径
     */
    private String znVideoPath;

    /**
     * 头部路径
     */
    private String znBannerPicPath;

    /**
     * 时长
     */
    private String znLength;

    /**
     * 显示用发布日期
     */
    private Long znDate;

    /**
     * 开始时间
     */
    private Long znStartTime;

    /**
     * 结束时间
     */
    private Long znEndTime;

    /**
     * 点击量
     */
    private Integer znHits;

    /**
     * 点赞量
     */
    private Integer znLikeNum;

    /**
     * 收藏量
     */
    private Integer znBookNum;

    /**
     * 发布人id
     */
    private Long znPublishUserId;

    /**
     * 发布人姓名
     */
    private String znPublishUserName;

    /**
     * 发布时间
     */
    private Long znCreateTime;

    /**
     * 更新人id
     */
    private Long znUpdateUserId;

    /**
     * 更新人姓名
     */
    private String znUpdateUserName;

    /**
     * 更新时间
     */
    private Long znUpdateTime;

    /**
     * 是否有效(0：无效；1：有效)
     */
    private Integer znIsValid;

    /**
     * 是否显示轮播图,(0,不显示,1:显示)
     */
    private Integer znIsCarousel;

    /**
     * 直播结束时间
     */
    private Long znEndDate;

    /**
     * 分类id（可选多个）
     */
    private String znNcIds;

    /**
     * 内容标签（以逗号分隔）
     */
    private String znTagIds;


    public Long getZnId() {
        return znId;
    }

    public void setZnId(Long znId) {
        this.znId = znId;
    }

    public Long getZnNcId() {
        return znNcId;
    }

    public void setZnNcId(Long znNcId) {
        this.znNcId = znNcId;
    }

    public Long getZnFromId() {
        return znFromId;
    }

    public void setZnFromId(Long znFromId) {
        this.znFromId = znFromId;
    }

    public String getZnFrom() {
        return znFrom;
    }

    public void setZnFrom(String znFrom) {
        this.znFrom = znFrom;
    }

    public Long getZnPace() {
        return znPace;
    }

    public void setZnPace(Long znPace) {
        this.znPace = znPace;
    }

    public String getZnTitle() {
        return znTitle;
    }

    public void setZnTitle(String znTitle) {
        this.znTitle = znTitle;
    }

    public String getZnAddress() {
        return znAddress;
    }

    public void setZnAddress(String znAddress) {
        this.znAddress = znAddress;
    }

    public String getZnTitleOne() {
        return znTitleOne;
    }

    public void setZnTitleOne(String znTitleOne) {
        this.znTitleOne = znTitleOne;
    }

    public String getZnTitleTwo() {
        return znTitleTwo;
    }

    public void setZnTitleTwo(String znTitleTwo) {
        this.znTitleTwo = znTitleTwo;
    }

    public String getZnThumbnailPath() {
        return znThumbnailPath;
    }

    public void setZnThumbnailPath(String znThumbnailPath) {
        this.znThumbnailPath = znThumbnailPath;
    }

    public String getZnCarouselPath() {
        return znCarouselPath;
    }

    public void setZnCarouselPath(String znCarouselPath) {
        this.znCarouselPath = znCarouselPath;
    }

    public String getZnIntro() {
        return znIntro;
    }

    public void setZnIntro(String znIntro) {
        this.znIntro = znIntro;
    }

    public String getZnContent() {
        return znContent;
    }

    public void setZnContent(String znContent) {
        this.znContent = znContent;
    }

    public String getZnVideoPath() {
        return znVideoPath;
    }

    public void setZnVideoPath(String znVideoPath) {
        this.znVideoPath = znVideoPath;
    }

    public String getZnBannerPicPath() {
        return znBannerPicPath;
    }

    public void setZnBannerPicPath(String znBannerPicPath) {
        this.znBannerPicPath = znBannerPicPath;
    }

    public String getZnLength() {
        return znLength;
    }

    public void setZnLength(String znLength) {
        this.znLength = znLength;
    }

    public Long getZnDate() {
        return znDate;
    }

    public void setZnDate(Long znDate) {
        this.znDate = znDate;
    }

    public Long getZnStartTime() {
        return znStartTime;
    }

    public void setZnStartTime(Long znStartTime) {
        this.znStartTime = znStartTime;
    }

    public Long getZnEndTime() {
        return znEndTime;
    }

    public void setZnEndTime(Long znEndTime) {
        this.znEndTime = znEndTime;
    }

    public Integer getZnHits() {
        return znHits;
    }

    public void setZnHits(Integer znHits) {
        this.znHits = znHits;
    }

    public Integer getZnLikeNum() {
        return znLikeNum;
    }

    public void setZnLikeNum(Integer znLikeNum) {
        this.znLikeNum = znLikeNum;
    }

    public Integer getZnBookNum() {
        return znBookNum;
    }

    public void setZnBookNum(Integer znBookNum) {
        this.znBookNum = znBookNum;
    }

    public Long getZnPublishUserId() {
        return znPublishUserId;
    }

    public void setZnPublishUserId(Long znPublishUserId) {
        this.znPublishUserId = znPublishUserId;
    }

    public String getZnPublishUserName() {
        return znPublishUserName;
    }

    public void setZnPublishUserName(String znPublishUserName) {
        this.znPublishUserName = znPublishUserName;
    }

    public Long getZnCreateTime() {
        return znCreateTime;
    }

    public void setZnCreateTime(Long znCreateTime) {
        this.znCreateTime = znCreateTime;
    }

    public Long getZnUpdateUserId() {
        return znUpdateUserId;
    }

    public void setZnUpdateUserId(Long znUpdateUserId) {
        this.znUpdateUserId = znUpdateUserId;
    }

    public String getZnUpdateUserName() {
        return znUpdateUserName;
    }

    public void setZnUpdateUserName(String znUpdateUserName) {
        this.znUpdateUserName = znUpdateUserName;
    }

    public Long getZnUpdateTime() {
        return znUpdateTime;
    }

    public void setZnUpdateTime(Long znUpdateTime) {
        this.znUpdateTime = znUpdateTime;
    }

    public Integer getZnIsValid() {
        return znIsValid;
    }

    public void setZnIsValid(Integer znIsValid) {
        this.znIsValid = znIsValid;
    }

    public Integer getZnIsCarousel() {
        return znIsCarousel;
    }

    public void setZnIsCarousel(Integer znIsCarousel) {
        this.znIsCarousel = znIsCarousel;
    }

    public Long getZnEndDate() {
        return znEndDate;
    }

    public void setZnEndDate(Long znEndDate) {
        this.znEndDate = znEndDate;
    }

    public String getZnNcIds() {
        return znNcIds;
    }

    public void setZnNcIds(String znNcIds) {
        this.znNcIds = znNcIds;
    }

    public String getZnTagIds() {
        return znTagIds;
    }

    public void setZnTagIds(String znTagIds) {
        this.znTagIds = znTagIds;
    }

    @Override
    public String toString() {
        return "FrsZyxNews{" +
        "znId=" + znId +
        ", znNcId=" + znNcId +
        ", znFromId=" + znFromId +
        ", znFrom=" + znFrom +
        ", znPace=" + znPace +
        ", znTitle=" + znTitle +
        ", znAddress=" + znAddress +
        ", znTitleOne=" + znTitleOne +
        ", znTitleTwo=" + znTitleTwo +
        ", znThumbnailPath=" + znThumbnailPath +
        ", znCarouselPath=" + znCarouselPath +
        ", znIntro=" + znIntro +
        ", znContent=" + znContent +
        ", znVideoPath=" + znVideoPath +
        ", znBannerPicPath=" + znBannerPicPath +
        ", znLength=" + znLength +
        ", znDate=" + znDate +
        ", znStartTime=" + znStartTime +
        ", znEndTime=" + znEndTime +
        ", znHits=" + znHits +
        ", znLikeNum=" + znLikeNum +
        ", znBookNum=" + znBookNum +
        ", znPublishUserId=" + znPublishUserId +
        ", znPublishUserName=" + znPublishUserName +
        ", znCreateTime=" + znCreateTime +
        ", znUpdateUserId=" + znUpdateUserId +
        ", znUpdateUserName=" + znUpdateUserName +
        ", znUpdateTime=" + znUpdateTime +
        ", znIsValid=" + znIsValid +
        ", znIsCarousel=" + znIsCarousel +
        ", znEndDate=" + znEndDate +
        ", znNcIds=" + znNcIds +
        ", znTagIds=" + znTagIds +
        "}";
    }
}
