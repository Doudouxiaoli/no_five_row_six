package com.wx.no_five_row_six.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 歌曲表
 * </p>
 *
 * @author dxl
 * @since 2020-01-15
 */
public class FrsSong implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fs_id", type = IdType.AUTO)
    private Long fsId;

    /**
     * 排序值
     */
    private Integer fsSort;

    /**
     * 专辑id
     */
    private Long fsFaId;

    /**
     * 专辑名称
     */
    private String fsFaName;

    /**
     * 歌曲名
     */
    private String fsName;

    /**
     * mv
     */
    private String fsVid;

    /**
     * 音乐播放地址
     */
    private String fsLink;
    /**
     * 歌手
     */
    private String fsSinger;
    /**
     * 时长
     */
    private String fsLength;
    /**
     * 收藏数
     */
    private Integer fsCollectionNum;

    /**
     * 创建时间
     */
    private Long fsCreateTime;

    /**
     * 修改时间
     */
    private Long fsUpdateTime;

    /**
     * 状态 0:删除 1:显示
     */
    private Integer fsIsValid;


    public Long getFsId() {
        return fsId;
    }

    public void setFsId(Long fsId) {
        this.fsId = fsId;
    }

    public Integer getFsSort() {
        return fsSort;
    }

    public void setFsSort(Integer fsSort) {
        this.fsSort = fsSort;
    }

    public Long getFsFaId() {
        return fsFaId;
    }

    public void setFsFaId(Long fsFaId) {
        this.fsFaId = fsFaId;
    }

    public String getFsFaName() {
        return fsFaName;
    }

    public void setFsFaName(String fsFaName) {
        this.fsFaName = fsFaName;
    }

    public String getFsName() {
        return fsName;
    }

    public void setFsName(String fsName) {
        this.fsName = fsName;
    }

    public String getFsVid() {
        return fsVid;
    }

    public void setFsVid(String fsVid) {
        this.fsVid = fsVid;
    }

    public String getFsLink() {
        return fsLink;
    }

    public void setFsLink(String fsLink) {
        this.fsLink = fsLink;
    }

    public Integer getFsCollectionNum() {
        return fsCollectionNum;
    }

    public void setFsCollectionNum(Integer fsCollectionNum) {
        this.fsCollectionNum = fsCollectionNum;
    }

    public Long getFsCreateTime() {
        return fsCreateTime;
    }

    public void setFsCreateTime(Long fsCreateTime) {
        this.fsCreateTime = fsCreateTime;
    }

    public Long getFsUpdateTime() {
        return fsUpdateTime;
    }

    public void setFsUpdateTime(Long fsUpdateTime) {
        this.fsUpdateTime = fsUpdateTime;
    }

    public Integer getFsIsValid() {
        return fsIsValid;
    }

    public void setFsIsValid(Integer fsIsValid) {
        this.fsIsValid = fsIsValid;
    }

    public String getFsSinger() {
        return fsSinger;
    }

    public void setFsSinger(String fsSinger) {
        this.fsSinger = fsSinger;
    }

    public String getFsLength() {
        return fsLength;
    }

    public void setFsLength(String fsLength) {
        this.fsLength = fsLength;
    }

    @Override
    public String toString() {
        return "FrsSong{" +
                "fsId=" + fsId +
                ", fsSort=" + fsSort +
                ", fsFaId=" + fsFaId +
                ", fsFaName='" + fsFaName + '\'' +
                ", fsName='" + fsName + '\'' +
                ", fsVid='" + fsVid + '\'' +
                ", fsLink='" + fsLink + '\'' +
                ", fsSinger='" + fsSinger + '\'' +
                ", fsLength='" + fsLength + '\'' +
                ", fsCollectionNum=" + fsCollectionNum +
                ", fsCreateTime=" + fsCreateTime +
                ", fsUpdateTime=" + fsUpdateTime +
                ", fsIsValid=" + fsIsValid +
                '}';
    }
}
