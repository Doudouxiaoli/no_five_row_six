package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public class FrsZyxLikeRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "zlr_id", type = IdType.AUTO)
    private Long zlrId;

    /**
     * 用户主键
     */
    private Long zlrUserId;

    /**
     * 资讯主键
     */
    private Long zlrNewsId;

    /**
     * 点赞时间
     */
    private Long zlrTime;

    /**
     * 评论记录id
     */
    private Long zlrCommentId;

    /**
     * 类型（0：资讯点赞，1：评论点赞）
     */
    private Integer zlrType;


    public Long getZlrId() {
        return zlrId;
    }

    public void setZlrId(Long zlrId) {
        this.zlrId = zlrId;
    }

    public Long getZlrUserId() {
        return zlrUserId;
    }

    public void setZlrUserId(Long zlrUserId) {
        this.zlrUserId = zlrUserId;
    }

    public Long getZlrNewsId() {
        return zlrNewsId;
    }

    public void setZlrNewsId(Long zlrNewsId) {
        this.zlrNewsId = zlrNewsId;
    }

    public Long getZlrTime() {
        return zlrTime;
    }

    public void setZlrTime(Long zlrTime) {
        this.zlrTime = zlrTime;
    }

    public Long getZlrCommentId() {
        return zlrCommentId;
    }

    public void setZlrCommentId(Long zlrCommentId) {
        this.zlrCommentId = zlrCommentId;
    }

    public Integer getZlrType() {
        return zlrType;
    }

    public void setZlrType(Integer zlrType) {
        this.zlrType = zlrType;
    }

    @Override
    public String toString() {
        return "FrsZyxLikeRecord{" +
        "zlrId=" + zlrId +
        ", zlrUserId=" + zlrUserId +
        ", zlrNewsId=" + zlrNewsId +
        ", zlrTime=" + zlrTime +
        ", zlrCommentId=" + zlrCommentId +
        ", zlrType=" + zlrType +
        "}";
    }
}
