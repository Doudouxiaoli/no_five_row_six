package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 专辑表
 * </p>
 *
 * @author dxl
 * @since 2020-01-14
 */
public class FrsAlbum implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fa_id", type = IdType.AUTO)
    private Long faId;

    /**
     * 排序值
     */
    private Integer faSort;

    /**
     * 专辑名
     */
    private String faName;

    /**
     * 制作背景
     */
    private String faContent;

    /**
     * 专辑封面
     */
    private String faImg;


    /**
     * 发布时间
     */
    private Long faTime;

    /**
     * 创建时间
     */
    private Long faCreateTime;

    /**
     * 修改时间
     */
    private Long faUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer faIsValid;
    @TableField(exist = false)
    private String faTimeStr;

    public Long getFaId() {
        return faId;
    }

    public void setFaId(Long faId) {
        this.faId = faId;
    }

    public Integer getFaSort() {
        return faSort;
    }

    public void setFaSort(Integer faSort) {
        this.faSort = faSort;
    }

    public String getFaName() {
        return faName;
    }

    public void setFaName(String faName) {
        this.faName = faName;
    }

    public String getFaContent() {
        return faContent;
    }

    public void setFaContent(String faContent) {
        this.faContent = faContent;
    }

    public String getFaImg() {
        return faImg;
    }

    public void setFaImg(String faImg) {
        this.faImg = faImg;
    }

    public Long getFaTime() {
        return faTime;
    }

    public void setFaTime(Long faTime) {
        this.faTime = faTime;
    }

    public Long getFaCreateTime() {
        return faCreateTime;
    }

    public void setFaCreateTime(Long faCreateTime) {
        this.faCreateTime = faCreateTime;
    }

    public Long getFaUpdateTime() {
        return faUpdateTime;
    }

    public void setFaUpdateTime(Long faUpdateTime) {
        this.faUpdateTime = faUpdateTime;
    }

    public Integer getFaIsValid() {
        return faIsValid;
    }

    public void setFaIsValid(Integer faIsValid) {
        this.faIsValid = faIsValid;
    }

    public String getFaTimeStr() {
        return faTimeStr;
    }

    public void setFaTimeStr(String faTimeStr) {
        this.faTimeStr = faTimeStr;
    }

    @Override
    public String toString() {
        return "FrsAlbum{" +
                "faId=" + faId +
                ", faSort=" + faSort +
                ", faName='" + faName + '\'' +
                ", faContent='" + faContent + '\'' +
                ", faImg='" + faImg + '\'' +
                ", faTime=" + faTime +
                ", faCreateTime=" + faCreateTime +
                ", faUpdateTime=" + faUpdateTime +
                ", faIsValid=" + faIsValid +
                ", faTimeStr='" + faTimeStr + '\'' +
                '}';
    }
}
