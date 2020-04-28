package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsDance;
import com.wx.no_five_row_six.service.impl.FrsDanceServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsViewRecordServiceImpl;
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

@Controller
@RequestMapping("/api/dance")
public class DanceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DanceController.class);
    @Autowired
    private FrsDanceServiceImpl danceService;
    @Autowired
    private FrsViewRecordServiceImpl viewRecordService;

    /**
     * 列表
     * @param keyword
     * @return
     */

    @ResponseBody
    @RequestMapping("/list")
    public JsonNode list(String keyword) {
        QueryWrapper<FrsDance> queryWrapper = new QueryWrapper<FrsDance>();
        try {
            queryWrapper.lambda()
                    .like(StringUtils.isNotEmpty(keyword), FrsDance::getFdTvName,keyword)
                    .eq(FrsDance::getFdIsValid, 1)
                    .orderByDesc(FrsDance::getFdSort,FrsDance::getFdTime);
            List<FrsDance> list = danceService.list(queryWrapper);
            return JacksonMapper.newDataInstance(list);
        } catch (Exception e) {
            e.printStackTrace();
            String err = "舞蹈列表查询异常";
            LOGGER.error(err, e);
            return JacksonMapper.newErrorInstance(err);
        }
    }

    /**
     * 详情
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("detail")
    public JsonNode detail(Long id, HttpServletRequest request) {
        Map<Integer, Object> map = new HashMap<>();
        try {
            QueryWrapper<FrsDance> queryWrapper = new QueryWrapper<FrsDance>();
            queryWrapper.lambda().eq(FrsDance::getFdIsValid, 1).orderByDesc(FrsDance::getFdSort);
            if (id ==null) {
                FrsDance dance = danceService.list(queryWrapper).get(0);
                id = dance.getFdId();
            }
            FrsDance runningDance = danceService.getById(id);
            queryWrapper.lambda().notLike(FrsDance::getFdId, id);
            List<FrsDance> waitingList = danceService.list(queryWrapper);
            //            保存访问记录
            viewRecordService.saveVisit(id, request, Const.MODEL_TYPE_DANCE, runningDance.getFdTvName());
            map.put(1, runningDance);
            map.put(2, waitingList);
            return JacksonMapper.newDataInstance(map);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情跳转出错");
        }
    }
}
