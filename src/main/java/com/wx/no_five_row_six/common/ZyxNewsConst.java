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
     * 歌曲
     */
    public static final Long SONG = 1L;
    /**
     * 舞蹈
     */
    public static final Long DANCE = 2L;
    /**
     * 演唱会
     */
    public static final Long CONCERT = 3L;
    /**
     * 节目单
     */
    public static final Long PROGRAM = 4L;
    /**
     * 代言
     */
    public static final Long ENDORSEMENT = 5L;
    /**
     * 电视剧
     */
    public static final Long TV = 6L;
    /**
     * 电影
     */
    public static final Long MOVIE = 7L;
    /**
     * 综艺
     */
    public static final Long VARIETY = 8L;

    /**
     * web图片存储本地路径 start
     */
    public static String PIC_PATH_ALBUM = "up/zyx/album/pic/";//专辑封面
    public static String PIC_PATH_DANCE = "up/zyx/dance/pic/";//舞蹈封面
    public static String PIC_PATH_CONCERT = "up/zyx/concert/pic/";//演唱会封面
    public static String PIC_PATH_CONCERT_PROGRAM = "up/zyx/concert/program/pic/";//演唱会节目封面

    public static String PIC_PATH_MOVIE = "up/zyx/movie/pic/";//电影海报
    public static String PIC_PATH_TV = "up/zyx/tv/pic/";//电视剧海报
    public static String PIC_PATH_VARIETY = "up/zyx/variety/pic/";//综艺海报

    public static String PIC_PATH_ENDORSEMENT_FOOD = "up/zyx/endorsement/food/pic/";//代言食物宣传照
    public static String PIC_PATH_ENDORSEMENT_MAKEUP = "up/zyx/endorsement/makeup/pic/";//代言美妆宣传照
    public static String PIC_PATH_ENDORSEMENT_CLOTHES = "up/zyx/endorsement/clothes/pic/";//代言服饰宣传照
    public static String PIC_PATH_ENDORSEMENT_LUXURY = "up/zyx/endorsement/luxury/pic/";//代言轻奢品宣传照
    public static String PIC_PATH_ENDORSEMENT_GAME = "up/zyx/endorsement/game/pic/";//代言游戏宣传照

    /** web图片存储本地路径 end */


    /**
     * web图片存储类型 start
     */
    public static String PIC_TYPE_ALBUM = "album";// 专辑
    public static String PIC_TYPE_DANCE = "dance";//舞蹈
    public static String PIC_TYPE_CONCERT = "concert";//演唱会
    public static String PIC_TYPE_CONCERT_PROGRAM = "program";//演唱会节目
    public static String PIC_TYPE_MOVIE = "movie";//电影
    public static String PIC_TYPE_TV = "tv";//电视剧
    public static String PIC_TYPE_VARIETY = "variety";//综艺
    public static String PIC_TYPE_ENDORSEMENT_FOOD = "food";//食物
    public static String PIC_TYPE_ENDORSEMENT_MAKEUP = "makeup";//美妆
    public static String PIC_TYPE_ENDORSEMENT_CLOTHES = "clothes";//服饰
    public static String PIC_TYPE_ENDORSEMENT_LUXURY = "luxury";//轻奢品
    public static String PIC_TYPE_ENDORSEMENT_GAME = "game";//游戏
    /**
     * web图片存储类型 end
     */
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
    public static Map<Integer, String> endorsement = new HashMap<>();

    static {
        endorsement.put(ENDORSEMENT_FOOT_ID, "食物");
        endorsement.put(ENDORSEMENT_MAKEUP_ID, "美妆");
        endorsement.put(ENDORSEMENT_CLOTHES_ID, "服饰");
        endorsement.put(ENDORSEMENT_LUXURY_ID, "轻奢品");
        endorsement.put(ENDORSEMENT_GAME_ID, "游戏");
    }

    /**
     * 取代言类型
     *
     * @param id
     * @return
     */
    public static String getEndorsementType(Integer id) {
        if (endorsement.containsKey(id)) {
            return endorsement.get(id);
        }
        return "";
    }

    public static Map<Long, String> moduleNameMap = new HashMap<>();

    static {
        moduleNameMap.put(ALBUM, "专辑");
        moduleNameMap.put(SONG, "歌曲");
        moduleNameMap.put(DANCE, "舞蹈");
        moduleNameMap.put(CONCERT, "演唱会");
        moduleNameMap.put(PROGRAM, "节目单");
        moduleNameMap.put(ENDORSEMENT, "代言");
        moduleNameMap.put(TV, "电视剧");
        moduleNameMap.put(MOVIE, "电影");
        moduleNameMap.put(VARIETY, "综艺");
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
        moduleUrlMap.put(ALBUM, "admin/zyx/ablum");
        moduleUrlMap.put(SONG, "admin/zyx/song");
        moduleUrlMap.put(DANCE, "admin/zyx/dance");
        moduleUrlMap.put(CONCERT, "admin/zyx/dance");
        moduleUrlMap.put(PROGRAM, "admin/zyx/program");
        moduleUrlMap.put(ENDORSEMENT, "admin/zyx/endorsement");
        moduleUrlMap.put(TV, "admin/zyx/tv");
        moduleUrlMap.put(MOVIE, "admin/zyx/movie");
        moduleUrlMap.put(VARIETY, "admin/zyx/variety");
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
}
