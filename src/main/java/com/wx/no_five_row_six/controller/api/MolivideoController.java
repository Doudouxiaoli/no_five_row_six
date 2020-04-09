package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsMolivideo;
import com.wx.no_five_row_six.entity.FrsMovie;
import com.wx.no_five_row_six.entity.FrsTv;
import com.wx.no_five_row_six.entity.FrsVariety;
import com.wx.no_five_row_six.service.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private FrsViewRecordServiceImpl viewRecordService;

    /**
     * 影视作品列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("index")
    public JsonNode index() {
        QueryWrapper<FrsMolivideo> q1 = null;
        QueryWrapper<FrsMolivideo> q2 = null;
        QueryWrapper<FrsMolivideo> q3 = null;
        Map<Integer, List> map = new HashMap<>();
        try {
            q1 = new QueryWrapper<FrsMolivideo>().
                    eq("fmv_is_valid", 1)
                    .eq("fmv_type", Const.MOLIVIDEO_MOVIE_ID)
                    .last("limit 3");
            // 查询条件
            List<FrsMolivideo> movieList = molivideoService.list(q1);
            q2 = new QueryWrapper<FrsMolivideo>()
                    .eq("fmv_is_valid", 1)
                    .eq("fmv_type", Const.MOLIVIDEO_TV_ID)
                    .last("limit 3");
            List<FrsMolivideo> tvList = molivideoService.list(q2);
            q3 = new QueryWrapper<FrsMolivideo>()
                    .eq("fmv_is_valid", 1)
                    .eq("fmv_type", Const.MOLIVIDEO_VARIETY_ID)
                    .last("limit 3");
            List<FrsMolivideo> varietyList = molivideoService.list(q3);
            map.put(1, movieList);
            map.put(2, tvList);
            map.put(3, varietyList);
            return JacksonMapper.newDataInstance(map);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "后台管理-影视作品列表异常";
            LOGGER.error(errMsg, e);
            return JacksonMapper.newErrorInstance(errMsg);
        }
    }

    /**
     * 获取电视剧/电影/综艺列表
     *
     * @param keyword
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public JsonNode list(String keyword, Integer type) {
        QueryWrapper<FrsMolivideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(null != type, FrsMolivideo::getFmvType, type)
                .eq(FrsMolivideo::getFmvIsValid, 1)
                .like(StringUtils.isNotEmpty(keyword), FrsMolivideo::getFmvName, keyword)
                .orderByDesc(FrsMolivideo::getFmvSort);
        List<FrsMolivideo> list = molivideoService.list(queryWrapper);
        Map<Integer, Object> map = new HashMap<>();
//        列表
        map.put(0, list);
        if (Const.MOLIVIDEO_MOVIE_ID == type) {
            map.put(1, "movie");
        } else if (Const.MOLIVIDEO_TV_ID == type) {
            map.put(1, "tv");
        } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
            map.put(1, "variety");
        } else {
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
        map.put(2, Const.getType(type));
        return JacksonMapper.newDataInstance(map);
    }

    /**
     * 电影详情
     *
     * @param fmvId
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("movieDetail")
    public JsonNode movieDetail(Long fmvId, Long id, HttpServletRequest request) {
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
        } else {
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
        waitQW.lambda().notLike(null != runningId, FrsMovie::getFmId, runningId).eq(FrsMovie::getFmIsValid, 1).orderByDesc(FrsMovie::getFmSort).last("limit 4");
        List<FrsMovie> waitingList = movieService.list(waitQW);
//        保存访问记录
        viewRecordService.saveVisit(runningId, request, Const.MODEL_TYPE_MOVIE, runningMovie.getFmName());
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, runningMovie);
        map.put(2, waitingList);
        return JacksonMapper.newDataInstance(map);
    }

    /**
     * 电视剧详情
     *
     * @param fmvId
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("tvDetail")
    public JsonNode tvDetail(Long fmvId, Long id, HttpServletRequest request) {
        QueryWrapper<FrsTv> queryWrapper = new QueryWrapper<>();
        FrsTv runningTv = null;
        queryWrapper.lambda().eq(FrsTv::getFtIsValid, 1).orderByAsc(FrsTv::getFtSort);
        if (null != fmvId) {
            queryWrapper.lambda().eq(FrsTv::getFtFmvId, fmvId);
            runningTv = tvService.getOne(queryWrapper, false);
        } else if (null != id) {
            runningTv = tvService.getById(id);
            queryWrapper.lambda().eq(FrsTv::getFtFmvId, runningTv.getFtFmvId());
        } else {
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
        List<FrsTv> waitingList = tvService.list(queryWrapper);
//        保存浏览记录
        viewRecordService.saveVisit(id, request, Const.MODEL_TYPE_TV, runningTv.getFtName());
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, runningTv);
        map.put(2, waitingList);
        return JacksonMapper.newDataInstance(map);
    }

    /**
     * 综艺详情
     *
     * @param fmvId
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("varietyDetail")
    public JsonNode varietyDetail(Long fmvId, Long id, HttpServletRequest request) {
        QueryWrapper<FrsVariety> queryWrapper = new QueryWrapper<>();
        FrsVariety runningVariety = null;
        queryWrapper.lambda().eq(FrsVariety::getFvIsValid, 1).orderByAsc(FrsVariety::getFvSort);
        if (null != fmvId) {
            queryWrapper.lambda().eq(FrsVariety::getFvFmvId, fmvId);
            runningVariety = varietyService.getOne(queryWrapper, false);
        } else if (null != id) {
            runningVariety = varietyService.getById(id);
            queryWrapper.lambda().eq(FrsVariety::getFvFmvId, runningVariety.getFvFmvId());
        } else {
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
        List<FrsVariety> waitingList = varietyService.list(queryWrapper);
//        保存访问记录
        viewRecordService.saveVisit(runningVariety.getFvId(), request, Const.MODEL_TYPE_VARIETY, runningVariety.getFvName());
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, runningVariety);
        map.put(2, waitingList);
        return JacksonMapper.newDataInstance(map);
    }
}
