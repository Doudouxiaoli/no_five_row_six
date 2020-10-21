package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.ZyxNewsConst;
import com.wx.no_five_row_six.entity.FrsZyxNews;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import com.wx.no_five_row_six.service.IFrsZyxVisitLogService;
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

/**
 * @author dxl
 * @date 2020/1/14
 * @desc 演唱会
 */
@Controller
@RequestMapping("api/concert")
public class ConcertController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcertController.class);

    @Autowired
    private IFrsZyxNewsService concertService;
    @Autowired
    private IFrsZyxNewsService programService;
    @Autowired
    private IFrsZyxVisitLogService viewRecordService;

    /**
     * 列表ajax
     *
     * @param current
     * @param size
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public JsonNode list(Integer current, Integer size, String keyword) {
        if (null == size) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        if (null == current) {
            current = 1;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        try {
            queryWrapper.lambda()
                    .like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword)
                    .eq(FrsZyxNews::getZnIsValid, ZyxNewsConst.VALID)
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.CONCERT);
            page = concertService.page(page, queryWrapper);
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
    public JsonNode detail(Long concertId, Long runningId, HttpServletRequest request) {
        Map<Integer, Object> map = new HashMap<>();
        try {
            QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
            queryWrapper.lambda()
                    .eq(FrsZyxNews::getZnIsValid, ZyxNewsConst.VALID)
                    .eq(FrsZyxNews::getZnFromId, concertId);
            if (runningId != null) {
                //                消除重复代码
            } else {
                FrsZyxNews program = programService.list(queryWrapper).get(0);
                runningId = program.getZnId();
            }
            FrsZyxNews runningMv = programService.getById(runningId);
            queryWrapper.notLike("fcp_id", runningId);
            List<FrsZyxNews> programList = programService.list(queryWrapper);
            FrsZyxNews concert = concertService.getById(concertId);
            //            保存访问记录
            viewRecordService.saveVisit(runningId, request, ZyxNewsConst.CONCERT.toString(), runningMv.getZnTitle());
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


