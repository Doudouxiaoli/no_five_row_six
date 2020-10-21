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
 * @date 2020/2/28
 * @desc 专辑
 */
@Controller
@RequestMapping("api/album")
public class AlbumController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private IFrsZyxNewsService albumService;
    @Autowired
    private IFrsZyxNewsService songService;
    @Autowired
    private IFrsZyxVisitLogService visitLogService;

    /**
     * 专辑列表
     *
     * @param current
     * @param size
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public JsonNode list(Integer current, Integer size, String keyword) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        try {
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword)
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.ALBUM)
                    .eq(FrsZyxNews::getZnIsValid, ZyxNewsConst.VALID)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = albumService.page(page, queryWrapper);
            return JacksonMapper.newDataInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            String err = "专辑列表查询异常";
            LOGGER.error(err, e);
            return JacksonMapper.newErrorInstance(err);
        }
    }

    /**
     * 详情
     *
     * @param albumId 专辑id
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public JsonNode detail(Long albumId) {
        Map<Integer, Object> map = new HashMap<>();
        try {
            FrsZyxNews album = albumService.getById(albumId);
            QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
            queryWrapper.lambda().eq(FrsZyxNews::getZnFromId, albumId)
                    .eq(FrsZyxNews::getZnIsValid, ZyxNewsConst.VALID);
            List<FrsZyxNews> songList = songService.list(queryWrapper);
            map.put(0, album);
            map.put(1, songList);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情跳转出错");
        }
        return JacksonMapper.newDataInstance(map);
    }

    /**
     * 保存一条访问记录
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("saveVisit")
    public JsonNode saveVisit(Long id, HttpServletRequest request) {
        FrsZyxNews song = songService.getById(id);
        visitLogService.saveVisit(id, request, ZyxNewsConst.ALBUM.toString(), song.getZnTitle());
        return JacksonMapper.newSuccessInstance();
    }
}


