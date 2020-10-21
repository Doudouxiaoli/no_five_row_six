package com.wx.no_five_row_six.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dxl
 * @version 2020/9/27 17:40
 */

public class ZyxNewsConst {
    /**
     * 专辑
     */
    public static final Long ALBUM = 0L;
    /**
     * 舞蹈
     */
    public static final Long DANCE = 1L;
    /**
     * 演唱会
     */
    public static final Long CONCERT = 2L;
    /**
     * 代言
     */
    public static final Long ENDORSEMENT = 3L;
    /**
     * 影视
     */
    public static final Long FILM = 4L;
    public static Map<Long, String> moduleNameMap = new HashMap<>();

    static {
        moduleNameMap.put(ALBUM, "专辑");
        moduleNameMap.put(DANCE, "舞蹈");
        moduleNameMap.put(CONCERT, "演唱会");
        moduleNameMap.put(ENDORSEMENT, "代言");
        moduleNameMap.put(FILM, "影视");
    }

    /**
     * 取模块名称
     *
     * @param id
     * @return
     */
    public static String getModelName(Long id) {
        if (moduleNameMap.containsKey(id)) {
            return moduleNameMap.get(id);
        }
        return "";
    }

    public static Map<Long, String> moduleUrlMap = new HashMap<>();

    static {
        moduleUrlMap.put(ALBUM, "admin/zyx/album");
        moduleUrlMap.put(DANCE, "admin/zyx/dance");
        moduleUrlMap.put(CONCERT, "admin/zyx/concert");
        moduleUrlMap.put(ENDORSEMENT, "admin/zyx/endorsement");
        moduleUrlMap.put(FILM, "admin/zyx/film");
    }

    /**
     * 取模块跳转路径
     *
     * @param id
     * @return
     */
    public static String getModelUrl(Long id) {
        if (moduleUrlMap.containsKey(id)) {
            return moduleUrlMap.get(id);
        }
        return "";
    }

    /**
     * 现场
     */
    public static Integer LIVING = 0;
    /**
     * 线上
     */
    public static Integer ONLINE = 1;
    /**
     * 父集
     */
    public static Integer PARENT = 0;
    /**
     * 子集
     */
    public static Integer CHILDREN = 1;
    /**
     * 无效
     */
    public static Integer NOT_VALID = 0;
    /**
     * 有效
     */
    public static Integer VALID = 1;

    /**
     * 代言常量 代言食物
     */
    public static Integer ENDORSEMENT_FOOT_ID = 0;
    /**
     * 代言美妆
     */
    public static Integer ENDORSEMENT_MAKEUP_ID = 1;
    /**
     * 代言服装
     */
    public static Integer ENDORSEMENT_CLOTHES_ID = 2;
    /**
     * 代言轻奢
     */
    public static Integer ENDORSEMENT_LUXURY_ID = 3;
    /**
     * 代言游戏
     */
    public static Integer ENDORSEMENT_GAME_ID = 4;

    public static Map<Integer, String> endorsementMap = new HashMap<>();

    static {
        endorsementMap.put(ENDORSEMENT_FOOT_ID, "食物");
        endorsementMap.put(ENDORSEMENT_MAKEUP_ID, "美妆");
        endorsementMap.put(ENDORSEMENT_CLOTHES_ID, "服饰");
        endorsementMap.put(ENDORSEMENT_LUXURY_ID, "轻奢品");
        endorsementMap.put(ENDORSEMENT_GAME_ID, "游戏");
    }

    /**
     * 取代言类型
     *
     * @param id
     * @return
     */
    public static String getEndorsementType(Integer id) {
        if (endorsementMap.containsKey(id)) {
            return endorsementMap.get(id);
        }
        return "";
    }

    /**
     * 电视剧
     */
    public static final Integer FILM_TV_ID = 0;
    /**
     * 电影
     */
    public static final Integer FILM_MOVIE_ID = 1;
    /**
     * 综艺
     */
    public static final Integer FILM_VARIETY_ID = 2;
    public static Map<Integer, String> filmMap = new HashMap<>();

    static {
        filmMap.put(FILM_TV_ID, "电视剧");
        filmMap.put(FILM_MOVIE_ID, "电影");
        filmMap.put(FILM_VARIETY_ID, "综艺");
    }

    /**
     * 取影视作品类型
     *
     * @param id
     * @return
     */
    public static String getFilmType(Integer id) {
        if (filmMap.containsKey(id)) {
            return filmMap.get(id);
        }
        return "";
    }

}
