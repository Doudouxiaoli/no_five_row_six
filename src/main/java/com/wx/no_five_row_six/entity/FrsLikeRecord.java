package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 点赞记录表
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
public class FrsLikeRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "flr_id", type = IdType.AUTO)
    private Long flrId;

    /**
     * 用户主键
     */
    private Long flrUserId;

    /**
     * 内容id
     */
    private Integer flrContentId;

    /**
     * 题目
     */
    private String flrContentName;

    /**
     * 内容来源
     */
    private String flrTypeName;

    /**
     * 点赞时间
     */
    private Long flrTime;

    /**
     * 模板id
     */
    private Integer flrTypeId;

    /**
     * 0:取消 1:点赞
     */
    private Integer flrState;


    public Long getFlrId() {
        return flrId;
    }

    public void setFlrId(Long flrId) {
        this.flrId = flrId;
    }

    public Long getFlrUserId() {
        return flrUserId;
    }

    public void setFlrUserId(Long flrUserId) {
        this.flrUserId = flrUserId;
    }

    public Integer getFlrContentId() {
        return flrContentId;
    }

    public void setFlrContentId(Integer flrContentId) {
        this.flrContentId = flrContentId;
    }

    public String getFlrContentName() {
        return flrContentName;
    }

    public void setFlrContentName(String flrContentName) {
        this.flrContentName = flrContentName;
    }

    public String getFlrTypeName() {
        return flrTypeName;
    }

    public void setFlrTypeName(String flrTypeName) {
        this.flrTypeName = flrTypeName;
    }

    public Long getFlrTime() {
        return flrTime;
    }

    public void setFlrTime(Long flrTime) {
        this.flrTime = flrTime;
    }

    public Integer getFlrTypeId() {
        return flrTypeId;
    }

    public void setFlrTypeId(Integer flrTypeId) {
        this.flrTypeId = flrTypeId;
    }

    public Integer getFlrState() {
        return flrState;
    }

    public void setFlrState(Integer flrState) {
        this.flrState = flrState;
    }

    @Override
    public String toString() {
        return "FrsLikeRecord{" +
        "flrId=" + flrId +
        ", flrUserId=" + flrUserId +
        ", flrContentId=" + flrContentId +
        ", flrContentName=" + flrContentName +
        ", flrTypeName=" + flrTypeName +
        ", flrTime=" + flrTime +
        ", flrTypeId=" + flrTypeId +
        ", flrState=" + flrState +
        "}";
    }
}
