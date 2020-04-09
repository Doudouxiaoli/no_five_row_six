package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 收藏记录表
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
public class FrsBookmarkRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fbr_id", type = IdType.AUTO)
    private Long fbrId;

    /**
     * 用户主键
     */
    private Long fbrUserId;

    /**
     * 收藏内容id
     */
    private Integer fbrContentId;

    /**
     * 收藏内容名称
     */
    private String fbrContentName;

    /**
     * 内容来源
     */
    private String fbrTypeName;

    /**
     * 收藏时间
     */
    private Long fbrTime;

    /**
     * 模板id
     */
    private Integer fbrTypeId;

    /**
     * 0:取消 1:收藏
     */
    private Integer fbrState;


    public Long getFbrId() {
        return fbrId;
    }

    public void setFbrId(Long fbrId) {
        this.fbrId = fbrId;
    }

    public Long getFbrUserId() {
        return fbrUserId;
    }

    public void setFbrUserId(Long fbrUserId) {
        this.fbrUserId = fbrUserId;
    }

    public Integer getFbrContentId() {
        return fbrContentId;
    }

    public void setFbrContentId(Integer fbrContentId) {
        this.fbrContentId = fbrContentId;
    }

    public String getFbrContentName() {
        return fbrContentName;
    }

    public void setFbrContentName(String fbrContentName) {
        this.fbrContentName = fbrContentName;
    }

    public String getFbrTypeName() {
        return fbrTypeName;
    }

    public void setFbrTypeName(String fbrTypeName) {
        this.fbrTypeName = fbrTypeName;
    }

    public Long getFbrTime() {
        return fbrTime;
    }

    public void setFbrTime(Long fbrTime) {
        this.fbrTime = fbrTime;
    }

    public Integer getFbrTypeId() {
        return fbrTypeId;
    }

    public void setFbrTypeId(Integer fbrTypeId) {
        this.fbrTypeId = fbrTypeId;
    }

    public Integer getFbrState() {
        return fbrState;
    }

    public void setFbrState(Integer fbrState) {
        this.fbrState = fbrState;
    }

    @Override
    public String toString() {
        return "FrsBookmarkRecord{" +
        "fbrId=" + fbrId +
        ", fbrUserId=" + fbrUserId +
        ", fbrContentId=" + fbrContentId +
        ", fbrContentName=" + fbrContentName +
        ", fbrTypeName=" + fbrTypeName +
        ", fbrTime=" + fbrTime +
        ", fbrTypeId=" + fbrTypeId +
        ", fbrState=" + fbrState +
        "}";
    }
}
