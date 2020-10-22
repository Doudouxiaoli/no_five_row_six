package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 收藏表
 * </p>
 *
 * @author dxl
 * @since 2020-10-22
 */
public class FrsZyxBookmarkRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "zbr_id", type = IdType.AUTO)
    private Long zbrId;

    /**
     * 用户主键
     */
    private Long zbrUserId;

    /**
     * 内容id
     */
    private Integer zbrNewsId;

    /**
     * 内容名称
     */
    private String zbrNews;

    /**
     * 模块id
     */
    private Integer zbrModuleId;

    /**
     * 模板名称
     */
    private String zbrModule;

    /**
     * 来源id(应用于子集)
     */
    private Integer zbrFromId;

    /**
     * 来源名称
     */
    private String zbrFrom;

    /**
     * 点赞时间
     */
    private Long zbrTime;

    /**
     * 是否点赞(0:未点赞;1:点赞;2:取消点赞)
     */
    private Integer zbrState;


    public Long getZbrId() {
        return zbrId;
    }

    public void setZbrId(Long zbrId) {
        this.zbrId = zbrId;
    }

    public Long getZbrUserId() {
        return zbrUserId;
    }

    public void setZbrUserId(Long zbrUserId) {
        this.zbrUserId = zbrUserId;
    }

    public Integer getZbrNewsId() {
        return zbrNewsId;
    }

    public void setZbrNewsId(Integer zbrNewsId) {
        this.zbrNewsId = zbrNewsId;
    }

    public String getZbrNews() {
        return zbrNews;
    }

    public void setZbrNews(String zbrNews) {
        this.zbrNews = zbrNews;
    }

    public Integer getZbrModuleId() {
        return zbrModuleId;
    }

    public void setZbrModuleId(Integer zbrModuleId) {
        this.zbrModuleId = zbrModuleId;
    }

    public String getZbrModule() {
        return zbrModule;
    }

    public void setZbrModule(String zbrModule) {
        this.zbrModule = zbrModule;
    }

    public Integer getZbrFromId() {
        return zbrFromId;
    }

    public void setZbrFromId(Integer zbrFromId) {
        this.zbrFromId = zbrFromId;
    }

    public String getZbrFrom() {
        return zbrFrom;
    }

    public void setZbrFrom(String zbrFrom) {
        this.zbrFrom = zbrFrom;
    }

    public Long getZbrTime() {
        return zbrTime;
    }

    public void setZbrTime(Long zbrTime) {
        this.zbrTime = zbrTime;
    }

    public Integer getZbrState() {
        return zbrState;
    }

    public void setZbrState(Integer zbrState) {
        this.zbrState = zbrState;
    }

    @Override
    public String toString() {
        return "FrsZyxBookmarkRecord{" +
        "zbrId=" + zbrId +
        ", zbrUserId=" + zbrUserId +
        ", zbrNewsId=" + zbrNewsId +
        ", zbrNews=" + zbrNews +
        ", zbrModuleId=" + zbrModuleId +
        ", zbrModule=" + zbrModule +
        ", zbrFromId=" + zbrFromId +
        ", zbrFrom=" + zbrFrom +
        ", zbrTime=" + zbrTime +
        ", zbrState=" + zbrState +
        "}";
    }
}
