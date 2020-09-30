//package com.wx.no_five_row_six.controller.api;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.wx.common.jackson.JacksonMapper;
//import com.wx.no_five_row_six.common.Const;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author dxl
// * @date 2020/2/24
// * @desc 代言
// */
//@Controller
//@RequestMapping("/api/endorsement")
//public class EndorsementController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(EndorsementController.class);
//
//    @Autowired
//    private FrsEndorsementServiceImpl endorsementService;
//    @Autowired
//    private FrsViewRecordServiceImpl viewRecordService;
//
//    /**
//     * 首页
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = {"/index"})
//    public JsonNode index(String keyword) {
//        Integer state = 1;
//        QueryWrapper<FrsEndorsement> footQW = new QueryWrapper<FrsEndorsement>();
//        QueryWrapper<FrsEndorsement> makeupQW = new QueryWrapper<FrsEndorsement>();
//        QueryWrapper<FrsEndorsement> clothesQW = new QueryWrapper<FrsEndorsement>();
//        QueryWrapper<FrsEndorsement> luxuryQW = new QueryWrapper<FrsEndorsement>();
//        QueryWrapper<FrsEndorsement> gameQW = new QueryWrapper<FrsEndorsement>();
//        Map<Integer, List> map = new HashMap<>();
//        try {
//            // 查询条件
//            footQW.lambda().like(StringUtils.isNotEmpty(keyword), FrsEndorsement::getFeName, keyword)
//                    .eq(FrsEndorsement::getFeIsValid, state)
//                    .eq(FrsEndorsement::getFeType, Const.ENDORSEMENT_FOOT_ID)
//                    .orderByDesc(FrsEndorsement::getFeSort)
//                    .last("limit 3");
//            List<FrsEndorsement> foodList = endorsementService.list(footQW);
//            map.put(0, foodList);
//            makeupQW.lambda().like(StringUtils.isNotEmpty(keyword), FrsEndorsement::getFeName, keyword)
//                    .eq(FrsEndorsement::getFeIsValid, state)
//                    .eq(FrsEndorsement::getFeType, Const.ENDORSEMENT_MAKEUP_ID)
//                    .orderByDesc(FrsEndorsement::getFeSort)
//                    .last("limit 3");
//            List<FrsEndorsement> makeupList = endorsementService.list(makeupQW);
//            map.put(1, makeupList);
//            clothesQW.lambda().like(StringUtils.isNotEmpty(keyword), FrsEndorsement::getFeName, keyword)
//                    .eq(FrsEndorsement::getFeIsValid, state)
//                    .eq(FrsEndorsement::getFeType, Const.ENDORSEMENT_CLOTHES_ID)
//                    .orderByDesc(FrsEndorsement::getFeSort)
//                    .last("limit 3");
//            List<FrsEndorsement> clothesList = endorsementService.list(clothesQW);
//            map.put(2, clothesList);
//            luxuryQW.lambda().like(StringUtils.isNotEmpty(keyword), FrsEndorsement::getFeName, keyword)
//                    .eq(FrsEndorsement::getFeIsValid, state)
//                    .eq(FrsEndorsement::getFeType, Const.ENDORSEMENT_LUXURY_ID)
//                    .orderByDesc(FrsEndorsement::getFeSort)
//                    .last("limit 3");
//            List<FrsEndorsement> luxuryList = endorsementService.list(luxuryQW);
//            map.put(3, luxuryList);
//            gameQW.lambda().like(StringUtils.isNotEmpty(keyword), FrsEndorsement::getFeName, keyword)
//                    .eq(FrsEndorsement::getFeIsValid, state)
//                    .eq(FrsEndorsement::getFeType, Const.ENDORSEMENT_GAME_ID)
//                    .orderByDesc(FrsEndorsement::getFeSort)
//                    .last("limit 3");
//            List<FrsEndorsement> gameList = endorsementService.list(gameQW);
//            map.put(4, gameList);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            String errMsg = "列表异常";
//            LOGGER.error(errMsg, e);
//            return JacksonMapper.newErrorInstance(errMsg);
//        }
//        return JacksonMapper.newDataInstance(map);
//    }
//
//    /**
//     * 列表
//     *
//     * @param type 类型
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = {"/list"})
//    public JsonNode list(String keyword, Integer type) {
//        Integer state = 1;
//        QueryWrapper<FrsEndorsement> queryWrapper = null;
//        Map<Integer, Object> map = new HashMap<>();
//        try {
//            map.put(0, Const.getType(type));
//            // 查询条件
//            queryWrapper = new QueryWrapper<FrsEndorsement>().like(StringUtils.isNotEmpty(keyword), "fe_name", keyword)
//                    .eq("fe_is_valid", state)
//                    .eq(null != type, "fe_type", type)
//                    .orderByDesc("fe_sort");
//            List<FrsEndorsement> list = endorsementService.list(queryWrapper);
//            map.put(1, list);
//        } catch (Exception e) {
//            e.printStackTrace();
//            String errMsg = "列表异常";
//            LOGGER.error(errMsg, e);
//            return JacksonMapper.newErrorInstance(errMsg);
//        }
//        return JacksonMapper.newDataInstance(map);
//    }
//
//
//    /**
//     * 详情页面
//     *
//     * @param id 专辑ID
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/detail")
//    public JsonNode detail(Long id, HttpServletRequest request) {
//        try {
//            FrsEndorsement endorsement = endorsementService.getById(id);
//            //            保存访问记录
//            viewRecordService.saveVisit(id, request, Const.MODEL_TYPE_ENDORSEMENT, endorsement.getFeName());
//            return JacksonMapper.newDataInstance(endorsement);
//        } catch (Exception e) {
//            e.printStackTrace();
//            String errMsg = "详情查询出错";
//            return JacksonMapper.newErrorInstance(errMsg);
//        }
//    }
//}
//
//
//
//
//
