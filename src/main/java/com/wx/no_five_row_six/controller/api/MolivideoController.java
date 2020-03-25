package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsMolivideo;
import com.wx.no_five_row_six.entity.FrsMovie;
import com.wx.no_five_row_six.entity.FrsTv;
import com.wx.no_five_row_six.entity.FrsVariety;
import com.wx.no_five_row_six.service.impl.FrsMolivideoServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsMovieServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsTvServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsVarietyServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/molivideo")
@Controller
public class MolivideoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MolivideoController.class);

    @Autowired
    private FrsMolivideoServiceImpl molivideoService;
    @Autowired
    private FrsMovieServiceImpl movieService;
    @Autowired
    private FrsTvServiceImpl tvService;
    @Autowired
    private FrsVarietyServiceImpl varietyService;

    /**
     * 影视作品列表
     *
     * @param mm
     * @param state
     * @return
     */
    @RequestMapping(value = {"/index"})
    public String index(ModelMap mm, Integer state) {
        if (state == null) {
            state = 1;
        }
        QueryWrapper<FrsMolivideo> q1 = null;
        QueryWrapper<FrsMolivideo> q2 = null;
        QueryWrapper<FrsMolivideo> q3 = null;
        try {
            q1 = new QueryWrapper<FrsMolivideo>().
                    eq("fmv_is_valid", state)
                    .eq("fmv_type", Const.MOLIVIDEO_MOVIE_ID)
                    .last("limit 3");
            // 查询条件
            List<FrsMolivideo> movieList = molivideoService.list(q1);
            q2 = new QueryWrapper<FrsMolivideo>()
                    .eq("fmv_is_valid", state)
                    .eq("fmv_type", Const.MOLIVIDEO_TV_ID)
                    .last("limit 3");
            List<FrsMolivideo> tvList = molivideoService.list(q2);
            q3 = new QueryWrapper<FrsMolivideo>()
                    .eq("fmv_is_valid", state)
                    .eq("fmv_type", Const.MOLIVIDEO_VARIETY_ID)
                    .last("limit 3");
            List<FrsMolivideo> varietyList = molivideoService.list(q3);
            mm.addAttribute("movieList", movieList);
            mm.addAttribute("tvList", tvList);
            mm.addAttribute("varietyList", varietyList);
            return "pc/molivideo/index";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-影视作品列表异常", e);
            mm.addAttribute("errMsg", "影视作品列表异常");
            return "error/error";
        }

    }

    /**
     * 获取电视剧/电影/综艺列表
     *
     * @param mm
     * @param keyword
     * @return
     */
    @RequestMapping("list")
    public String list(ModelMap mm, String keyword, Integer type) {
        QueryWrapper<FrsMolivideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(null != type, FrsMolivideo::getFmvType, type)
                .eq(FrsMolivideo::getFmvIsValid, 1)
                .like(StringUtils.isNotEmpty(keyword), FrsMolivideo::getFmvName, keyword)
                .orderByDesc(FrsMolivideo::getFmvSort);
        List<FrsMolivideo> list = molivideoService.list(queryWrapper);
        mm.addAttribute("list", list);
        if (Const.MOLIVIDEO_MOVIE_ID == type) {
            mm.addAttribute("url", "movie");
        } else if (Const.MOLIVIDEO_TV_ID == type) {
            mm.addAttribute("url", "tv");
        } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
            mm.addAttribute("url", "variety");
        } else {
            mm.addAttribute("url", "");
        }
        mm.addAttribute("TypeName", Const.getType(type));
        return "pc/molivideo/list";
    }

    /**
     * 电影详情
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("movieDetail")
    public String movieDetail(ModelMap mm, Long fmvId, Long id) {
        QueryWrapper<FrsMovie> waitQW = new QueryWrapper<>();
        Long runningId = null;
        FrsMovie runningMovie = null;
        if (null != fmvId) {
            QueryWrapper<FrsMovie> runQW = new QueryWrapper<>();
            runQW.lambda().eq(FrsMovie::getFmFmvId, fmvId).eq(FrsMovie::getFmIsValid, 1).orderByDesc(FrsMovie::getFmSort);
            runningMovie = movieService.getOne(runQW, false);
            runningId = runningMovie.getFmId();

        } else if (null != id) {
            runningMovie = movieService.getById(id);
            runningId = id;
        }
        waitQW.lambda().notLike(null != runningId, FrsMovie::getFmId, runningId).eq(FrsMovie::getFmIsValid, 1).orderByDesc(FrsMovie::getFmSort).last("limit 4");
        List<FrsMovie> waitingList = movieService.list(waitQW);
        mm.addAttribute("waitingList", waitingList);
        mm.addAttribute("runningMovie", runningMovie);
        return "pc/molivideo/movieDetail";
    }

    /**
     * 电视剧详情
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("tvDetail")
    public String tvDetail(ModelMap mm, Long fmvId, Long id) {
        QueryWrapper<FrsTv> queryWrapper = new QueryWrapper<>();
        Long runningId = null;
        FrsTv runningTv = null;
        queryWrapper.lambda().eq(FrsTv::getFtIsValid, 1).orderByAsc(FrsTv::getFtSort);
        if (null != fmvId) {
            queryWrapper.lambda().eq(FrsTv::getFtFmvId, fmvId);
            runningTv = tvService.getOne(queryWrapper, false);
            runningId = runningTv.getFtId();
        } else if (null != id) {
            runningTv = tvService.getById(id);
            queryWrapper.lambda().eq(FrsTv::getFtFmvId, runningTv.getFtFmvId());
            runningId = id;
        }
        queryWrapper.lambda().notLike(null != runningId, FrsTv::getFtId, runningId).last("limit 4");
        List<FrsTv> waitingList = tvService.list(queryWrapper);
        mm.addAttribute("waitingList", waitingList);
        mm.addAttribute("runningTv", runningTv);
        return "pc/molivideo/tvDetail";
    }

    /**
     * 综艺详情
     *
     * @param mm
     * @param id
     * @return
     */
    @RequestMapping("varietyDetail")
    public String varietyDetail(ModelMap mm, Long fmvId, Long id) {
        QueryWrapper<FrsVariety> queryWrapper = new QueryWrapper<>();
        Long runningId = null;
        FrsVariety runningVariety = null;
        queryWrapper.lambda().eq(FrsVariety::getFvIsValid, 1).orderByAsc(FrsVariety::getFvSort);
        if (null != fmvId) {
            queryWrapper.lambda().eq(FrsVariety::getFvFmvId, fmvId);
            runningVariety = varietyService.getOne(queryWrapper, false);
            runningId = runningVariety.getFvId();
        } else if (null != id) {
            runningVariety = varietyService.getById(id);
            queryWrapper.lambda().eq(FrsVariety::getFvFmvId, runningVariety.getFvFmvId());
            runningId = id;
        }
        queryWrapper.lambda().notLike(null != runningId, FrsVariety::getFvId, runningId).last("limit 4");
        List<FrsVariety> waitingList = varietyService.list(queryWrapper);
        mm.addAttribute("waitingList", waitingList);
        mm.addAttribute("runningVariety", runningVariety);
        return "pc/molivideo/varietyDetail";
    }
}
