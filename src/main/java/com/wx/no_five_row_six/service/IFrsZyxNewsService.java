package com.wx.no_five_row_six.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.no_five_row_six.entity.FrsZyxNews;

import java.util.List;

/**
 * <p>
 * 通用表 服务类
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public interface IFrsZyxNewsService extends IService<FrsZyxNews> {
    /**
     * 条件获取列表
     *
     * @param moduleId 模块id 0专辑 1舞蹈 2演唱会 3代言4影视
     * @param tagId    代言 0 食物 1美妆 2服装 3轻奢 4游戏
     *                 影视类型 0电视剧 1电影 2综艺
     * @return
     */
    List<FrsZyxNews> getChildList(Long moduleId, Integer tagId);

    /**
     * 获取父节点id列表
     *
     * @param moduleId 模块id 0专辑 1舞蹈 2演唱会 3代言4影视
     * @param tagId    代言 0 食物 1美妆 2服装 3轻奢 4游戏
     *                 影视类型 0电视剧 1电影 2综艺
     * @return
     */
    List ids(Long moduleId, Integer tagId);

    /**
     * 改变节点状态
     *
     * @param id 节点id
     */
    void changeValid(Long id);

    /**
     * 文章通用点赞
     */
    void addNewsLike(Long newsId);
}
