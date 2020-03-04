package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsConcert;
import com.wx.no_five_row_six.entity.FrsConcertProgram;
import com.wx.no_five_row_six.service.impl.FrsConcertProgramServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsConcertServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("api/concert")
public class ConcertController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcertController.class);

    @Autowired
    private FrsConcertServiceImpl concertService;
    @Autowired
    private FrsConcertProgramServiceImpl programService;

    /**
     * 列表
     *
     * @param mm
     * @param current
     * @param size
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, Integer current, Integer size, String keyword) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        Integer state = 1;
        QueryWrapper<FrsConcert> queryWrapper = null;
        IPage<FrsConcert> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper = new QueryWrapper<FrsConcert>()
                    .like(StringUtils.isNotEmpty(keyword), "fc_address", keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), "fc_title", keyword)
                    .eq("fc_is_valid", state)
                    .orderByDesc("fc_sort", "fc_time");
            page = concertService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("演唱会列表异常", e);
            mm.addAttribute("errMsg", "演唱会列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        return "pc/concert/list";
    }

    /**
     * 演唱会详情页面
     *
     * @param mm
     * @param concertId
     * @param runningId
     * @return
     */
    @RequestMapping("/concertDetail")
    public String concertDetail(ModelMap mm, Long concertId, Long runningId) {
        try {
            FrsConcert concert = concertService.getById(concertId);
            QueryWrapper<FrsConcertProgram> queryWrapper = new QueryWrapper<FrsConcertProgram>().eq("fcp_fc_id", concertId).eq("fcp_is_valid", 1).orderByDesc("fcp_sort");
            if (runningId == null) {
                FrsConcertProgram program = programService.list(queryWrapper).get(0);
                runningId = program.getFcpId();
            }
            queryWrapper.notLike("fcp_id", runningId);
            List<FrsConcertProgram> programList = programService.list(queryWrapper);
            FrsConcertProgram runningMv = programService.getById(runningId);
            mm.addAttribute("runningMv", runningMv);
            mm.addAttribute("concert", concert);
            //推荐视频
            mm.addAttribute("programList", programList);
            return "pc/concert/detail";
        } catch (Exception e) {
            e.printStackTrace();
            mm.addAttribute("errMsg", "演唱会详情查询出错");
            return "error/error";
        }
    }

    /**
     * 列表ajax
     *
     * @param current
     * @param size
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("/listAjax")
    public JsonNode listAjax(Integer current, Integer size, String keyword) {
        Integer state = 1;
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        QueryWrapper<FrsConcert> queryWrapper = null;
        IPage<FrsConcert> page = new Page<>(current, size);
        try {
            queryWrapper = new QueryWrapper<FrsConcert>()
                    .like(StringUtils.isNotEmpty(keyword), "fc_name", keyword)
                    .eq("fc_is_valid", state)
                    .orderByDesc("fc_sort", "fc_time");
            page = concertService.page(page, queryWrapper);
            for (FrsConcert concert : page.getRecords()) {
                concert.setFcTimeStr(TimeUtil.longToString(concert.getFcTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            String err = "演唱会列表查询异常";
            LOGGER.error(err, e);
            return JacksonMapper.newErrorInstance(err);
        }
        return JacksonMapper.newDataInstance(page);
    }

    @ResponseBody
    @RequestMapping("detail")
    public JsonNode detail(Long concertId, Long runningId) {
        Map<Integer, Object> map = new HashMap<>();
        try {
            QueryWrapper<FrsConcertProgram> queryWrapper = new QueryWrapper<FrsConcertProgram>().eq("fcp_is_valid", 1).orderByDesc("fcp_sort").eq("fcp_fc_id", concertId);
            if (runningId != null) {
//                消除重复代码
            } else {
                FrsConcertProgram program = programService.list(queryWrapper).get(0);
                runningId = program.getFcpId();
            }
            FrsConcertProgram runningMv = programService.getById(runningId);
            queryWrapper.notLike("fcp_id", runningId);
            List<FrsConcertProgram> programList = programService.list(queryWrapper);
            FrsConcert concert = concertService.getById(concertId);
            map.put(0, concert);
            map.put(1, runningMv);
            map.put(2, programList);
            return JacksonMapper.newDataInstance(map);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情跳转出错");
        }
    }
}


