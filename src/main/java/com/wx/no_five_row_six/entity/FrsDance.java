package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 舞蹈表
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
public class FrsDance implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fd_id", type = IdType.AUTO)
    private Long fdId;

    /**
     * 排序值
     */
    private Integer fdSort;

    /**
     * 节目名称
     */
    private String fdTvName;

    /**
     * 封面
     */
    private String fdImg;

    /**
     * 舞蹈视频id
     */
    private String fdVid;

    /**
     * 点赞数
     */
    private Integer fdLikedNum;

    /**
     * 收藏数
     */
    private Integer fdCollectionNum;

    /**
     * 表演时间
     */
    private Long fdTime;

    /**
     * 创建时间
     */
    private Long fdCreateTime;

    /**
     * 修改时间
     */
    private Long fdUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fdIsValid;


    public Long getFdId() {
        return fdId;
    }

    public void setFdId(Long fdId) {
        this.fdId = fdId;
    }

    public Integer getFdSort() {
        return fdSort;
    }

    public void setFdSort(Integer fdSort) {
        this.fdSort = fdSort;
    }

    public String getFdTvName() {
        return fdTvName;
    }

    public void setFdTvName(String fdTvName) {
        this.fdTvName = fdTvName;
    }

    public String getFdImg() {
        return fdImg;
    }

    public void setFdImg(String fdImg) {
        this.fdImg = fdImg;
    }

    public String getFdVid() {
        return fdVid;
    }

    public void setFdVid(String fdVid) {
        this.fdVid = fdVid;
    }

    public Integer getFdLikedNum() {
        return fdLikedNum;
    }

    public void setFdLikedNum(Integer fdLikedNum) {
        this.fdLikedNum = fdLikedNum;
    }

    public Integer getFdCollectionNum() {
        return fdCollectionNum;
    }

    public void setFdCollectionNum(Integer fdCollectionNum) {
        this.fdCollectionNum = fdCollectionNum;
    }

    public Long getFdTime() {
        return fdTime;
    }

    public void setFdTime(Long fdTime) {
        this.fdTime = fdTime;
    }

    public Long getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(Long fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    public Long getFdUpdateTime() {
        return fdUpdateTime;
    }

    public void setFdUpdateTime(Long fdUpdateTime) {
        this.fdUpdateTime = fdUpdateTime;
    }

    public Integer getFdIsValid() {
        return fdIsValid;
    }

    public void setFdIsValid(Integer fdIsValid) {
        this.fdIsValid = fdIsValid;
    }

    @Override
    public String toString() {
        return "FrsDance{" +
        "fdId=" + fdId +
        ", fdSort=" + fdSort +
        ", fdTvName=" + fdTvName +
        ", fdImg=" + fdImg +
        ", fdVid=" + fdVid +
        ", fdLikedNum=" + fdLikedNum +
        ", fdCollectionNum=" + fdCollectionNum +
        ", fdTime=" + fdTime +
        ", fdCreateTime=" + fdCreateTime +
        ", fdUpdateTime=" + fdUpdateTime +
        ", fdIsValid=" + fdIsValid +
        "}";
    }
}
