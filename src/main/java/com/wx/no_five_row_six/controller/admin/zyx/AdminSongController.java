package com.wx.no_five_row_six.controller.admin.zyx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.ZyxNewsConst;
import com.wx.no_five_row_six.common.security.AdminUserUtil;
import com.wx.no_five_row_six.entity.FrsZyxNews;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dxl
 * @version 2020/10/12 13:27
 * @desc 后台管理-歌曲
 */
@Controller
@RequestMapping("/admin/zyx/song")
public class AdminSongController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSongController.class);

    @Autowired
    private IFrsZyxNewsService songService;

    @RequestMapping("list")
    public String list(Long fromId, ModelMap mm) {
        mm.addAttribute("fromId", fromId);
        return "admin/zyx/album/song/list";
    }

    /**
     * 歌曲列表
     *
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(String keyword, Integer current, Integer size, Long fromId) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnAddress, keyword)
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.SONG)
                    .eq(null != fromId, FrsZyxNews::getZnFromId, fromId);
            page = songService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-歌曲列表异常", e);
            return JacksonMapper.newErrorInstance("后台管理-歌曲列表异常");
        }
    }

    /**
     * 编辑歌曲界面
     *
     * @param id     节目id
     * @param fromId 歌曲id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id, Long fromId) {
        try {
            if (null == id) {
                mm.addAttribute("title", "歌曲添加");
            } else {
                mm.addAttribute("title", "歌曲编辑");
                FrsZyxNews song = songService.getById(id);
                fromId = song.getZnFromId();
                mm.addAttribute("song", song);
            }
            mm.addAttribute("fromId", fromId);
            return "admin/zyx/album/song/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取歌曲异常。", e);
            mm.addAttribute("errMsg", "获取歌曲异常");
            return "error/error";
        }
    }

    /**
     * 保存歌曲
     *
     * @param song
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews song) {
        try {
            if (null == song.getZnFromId()) {
                return "error/error";
            } else {
                if (null == song.getZnId()) {
                    FrsZyxNews album = songService.getById(song.getZnFromId());
                    if (null != album) {
                        song.setZnFrom(album.getZnTitle());
                    }
                    song.setZnNcId(ZyxNewsConst.SONG);
                    song.setZnCreateTime(TimeUtil.dateToLong());
                    song.setZnIsValid(ZyxNewsConst.VALID);
                    songService.save(song);
                } else {
                    song.setZnUpdateTime(TimeUtil.dateTolong());
                    song.setZnUpdateUserId(AdminUserUtil.getUserId());
                    song.setZnUpdateUserName(AdminUserUtil.getShowName());
                    songService.updateById(song);
                }
                return "redirect:list";
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改歌曲异常。", e);
            mm.addAttribute("errMsg", "保存或修改歌曲异常");
            return "error/error";
        }
    }

    /**
     * 删除歌曲
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JsonNode del(Long id) {
        try {
            FrsZyxNews song = songService.getById(id);
            song.setZnIsValid(ZyxNewsConst.NOT_VALID);
            songService.updateById(song);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除歌曲异常。", e);
            return JacksonMapper.newErrorInstance("删除歌曲异常");
        }
    }

    /**
     * 恢复
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("reBack")
    public JsonNode reBack(Long id) {
        try {
            FrsZyxNews song = songService.getById(id);
            song.setZnIsValid(ZyxNewsConst.VALID);
            songService.updateById(song);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复歌曲异常。", e);
            return JacksonMapper.newErrorInstance("恢复歌曲异常");
        }
    }

}
