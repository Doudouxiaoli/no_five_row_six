package com.wx.no_five_row_six.common;

import org.springframework.mobile.device.LiteDeviceResolver;

/**
 * @author gx
 * @date 2018/11/15
 */
public class Const {

    /**
     * PC前端列表暂不分页
     */
    public static Integer WEB_PC_ROWSPERPAGE = -1;

    /**
     * 前端列表每页个数
     */
    public static Integer WEB_ROWSPERPAGE = 5;
    /**
     * 后台列表每页个数 5
     */
    public static Integer ADMIN_ROWSPERPAGE_LESS = 5;
    /**
     * 后台列表每页个数 10
     */
    public static Integer ADMIN_ROWSPERPAGE_MORE = 10;

    /**
     * 文件存储的根目录
     */
    public static final String BASE_FOLDER = "up";
    /**
     * 默认存储本地路径
     */
    public static String PIC_PATH_DEFAULT = "up/default/pic/";
    /**
     * 微信头像路径
     */
    public static String HEADIMG_PATH = "up/headimg/";


    public static String AUDIO_PATH = "up/audio/";
    public static String VIDEO_PATH = "up/video/";
    public static String FILE_PATH = "up/file/";

    /**
     * ppt源文件存放路径
     */
    public static String PPT_PATH = "up/ppt/";
    /**
     * ppt转图片存储本地路径
     */
    public static String PPT_IMG_PATH = "up/ppt/img/";
    /**
     * ppt临时存放地址
     */
    public static String PPT_FILE_PATH = "up/ppt/file/";

    /**
     * 项目相关存储本地路径 start
     */
    public static String RESOURCES_PATH = "up/resources/file/";


    /**
     * web图片存储本地路径 start
     */
    public static String PIC_PATH_ALBUM = "up/album/pic/";//专辑封面
    public static String PIC_PATH_CONCERT = "up/concert/pic/";//演唱会封面
    public static String PIC_PATH_DANCE = "up/dance/pic/";//舞蹈封面

    public static String PIC_PATH_MOLIVIDEO_MOVIE = "up/molivideo/movie/pic/";//影视作品中电影海报
    public static String PIC_PATH_MOLIVIDEO_TV = "up/molivideo/tv/pic/";//影视作品中电视剧海报
    public static String PIC_PATH_MOLIVIDEO_VARIETY = "up/molivideo/variety/pic/";//影视作品中综艺海报

    public static String PIC_PATH_ENDORSEMENT_FOOD="up/endorsement/food/pic/";//代言食物宣传照
    public static String PIC_PATH_ENDORSEMENT_MAKEUP="up/endorsement/makeup/pic/";//代言美妆宣传照
    public static String PIC_PATH_ENDORSEMENT_CLOTHES="up/endorsement/clothes/pic/";//代言服饰宣传照
    public static String PIC_PATH_ENDORSEMENT_LUXURY="up/endorsement/luxury/pic/";//代言轻奢品宣传照
    public static String PIC_PATH_ENDORSEMENT_GAME="up/endorsement/game/pic/";//代言游戏宣传照

    public static String PIC_PATH_SHARECODE = "up/shareCode/pic/";// 小程序分享二维码存储路径


    /** web图片存储本地路径 end */


    /**
     * web图片存储类型 start
     */
    public static String PIC_TYPE_ALBUM = "album";// 专辑
    public static String PIC_TYPE_CONCERT = "concert";//演唱会
    public static String PIC_TYPE_DANCE="dance";//舞蹈
    public static String PIC_TYPE_MOLIVIDEO_MOVIE = "movie";//电影
    public static String PIC_TYPE_MOLIVIDEO_TV = "tv";//电视剧
    public static String PIC_TYPE_MOLIVIDEO_VARIETY = "variety";//综艺
    public static String PIC_TYPE_ENDORSEMENT_FOOD="food";//食物
    public static String PIC_TYPE_ENDORSEMENT_MAKEUP="makeup";//美妆
    public static String PIC_TYPE_ENDORSEMENT_CLOTHES="clothes";//服饰
    public static String PIC_TYPE_ENDORSEMENT_LUXURY="luxury";//轻奢品
    public static String PIC_TYPE_ENDORSEMENT_GAME="game";//游戏
    /**
     * web图片存储类型 end
     */

    public static String SYS_DOMAIN;
    public static String SYS_ENVIRONMENT;

    public static LiteDeviceResolver liteDeviceResolver = new LiteDeviceResolver();

    /**
     * 代言常量
     */
    public static Integer ENDORSEMENT_FOOT_ID = 0;
    public static String ENDORSEMENT_FOOT_TYPE = "食物";
    public static Integer ENDORSEMENT_MAKEUP_ID = 1;
    public static String ENDORSEMENT_MAKEUP_TYPE = "美妆";
    public static Integer ENDORSEMENT_CLOTHES_ID = 2;
    public static String ENDORSEMENT_CLOTHES_TYPE = "服饰";
    public static Integer ENDORSEMENT_LUXURY_ID = 3;
    public static String ENDORSEMENT_LUXURY_TYPE = "轻奢品";
    public static Integer ENDORSEMENT_GAME_ID = 4;
    public static String ENDORSEMENT_GAME_TYPE = "游戏";
    /**
     * 影视作品常量
     */
    public static Integer MOLIVIDEO_MOVIE_ID = 1;
    public static String MOLIVIDEO_MOVIE_TYPE = "电影";
    public static Integer MOLIVIDEO_TV_ID = 2;
    public static String MOLIVIDEO_TV_TYPE = "电视剧";
    public static Integer MOLIVIDEO_VARIETY_ID = 3;
    public static String MOLIVIDEO_VARIETY_TYPE = "综艺";
}
