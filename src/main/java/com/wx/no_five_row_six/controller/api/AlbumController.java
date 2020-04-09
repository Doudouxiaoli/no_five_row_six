package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsAlbum;
import com.wx.no_five_row_six.entity.FrsSong;
import com.wx.no_five_row_six.service.impl.FrsAlbumServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsSongServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/album")
public class AlbumController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private FrsAlbumServiceImpl albumService;
    @Autowired
    private FrsSongServiceImpl songService;

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
        QueryWrapper<FrsAlbum> queryWrapper = null;
        IPage<FrsAlbum> page = new Page<>(current, size);
        try {
            queryWrapper = new QueryWrapper<FrsAlbum>()
                    .like(StringUtils.isNotEmpty(keyword), "fa_name", keyword)
                    .eq("fa_is_valid", state)
                    .orderByDesc("fa_sort", "fa_time");
            page = albumService.page(page, queryWrapper);
            for (FrsAlbum album : page.getRecords()) {
                album.setFaTimeStr(TimeUtil.longToString(album.getFaTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            String err = "专辑列表查询异常";
            LOGGER.error(err, e);
            return JacksonMapper.newErrorInstance(err);
        }
        return JacksonMapper.newDataInstance(page);
    }

    @RequestMapping("/detail")
    @ResponseBody
    public JsonNode detail(Long albumId) {
        Map<Integer, List> map = new HashMap<>();
        try {
            List<FrsAlbum> album = albumService.list(new QueryWrapper<FrsAlbum>().eq("fa_id", albumId));
            QueryWrapper<FrsSong> queryWrapper = new QueryWrapper<FrsSong>().eq("fs_fa_id", albumId).eq("fs_is_valid", 1).orderByDesc("fs_sort");
            List<FrsSong> songList = songService.list(queryWrapper);
            map.put(0, album);
            map.put(1, songList);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情跳转出错");
        }
        return JacksonMapper.newDataInstance(map);
    }

}


