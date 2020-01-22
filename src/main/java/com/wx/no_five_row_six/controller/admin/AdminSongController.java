package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsSong;
import com.wx.no_five_row_six.service.impl.FrsAlbumServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsSongServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/album/song")
public class AdminSongController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSongController.class);
    @Autowired
    private FrsSongServiceImpl songService;
    @Autowired
    private FrsAlbumServiceImpl albumService;

    /**
     * 歌曲列表
     *
     * @param mm
     * @param keyword
     * @param state
     * @param current
     * @param size
     * @param albumId
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, String keyword, Integer state, Integer current, Integer size, Long albumId) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        if (state == null) {
            state = 1;
        }
        QueryWrapper<FrsSong> queryWrapper = null;
        IPage<FrsSong> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper = new QueryWrapper<FrsSong>().like(StringUtils.isNotEmpty(keyword), "fs_name", keyword)
                    .eq("fs_is_valid", state)
                    .eq("fs_fa_id", albumId)
                    .orderByDesc("fs_sort");
            page = songService.page(page, queryWrapper);
            mm.addAttribute("fs_fa_id", albumId);
            mm.addAttribute("fs_fa_name", albumService.getById(albumId).getFaName());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-歌曲列表异常", e);
            mm.addAttribute("errMsg", "歌曲列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("state", state);
        return "admin/album/song/list";
    }

    /**
     * 添加歌曲界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String addUI(ModelMap mm, Long fs_fa_id) {
        mm.addAttribute("fs_fa_id", fs_fa_id);
        mm.addAttribute("fs_fa_name", albumService.getById(fs_fa_id).getFaName());
        return "admin/album/song/add";
    }

    /**
     * 编辑歌曲界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            FrsSong song = songService.getById(id);
            mm.addAttribute("song", song);
            mm.addAttribute("fs_fa_id", song.getFsFaId());
            mm.addAttribute("fs_fa_name", song.getFsFaName());
            return "admin/album/song/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取歌曲异常。", e);
            mm.addAttribute("errMsg", "获取歌曲异常");
            return "error/error";
        }
    }

    /**
     * 更新保存
     *
     * @param song
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsSong song) {
        try {
            if (song.getFsId() == null) {
                song.setFsCreateTime(TimeUtil.dateToLong());
                song.setFsIsValid(1);
                songService.save(song);

            } else {
                song.setFsUpdateTime(TimeUtil.dateToLong());
                songService.updateById(song);
            }
            return "redirect:list?albumId=" + song.getFsFaId();
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
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsSong song = songService.getById(id);
            song.setFsIsValid(0);
            songService.updateById(song);
            return "redirect:list?albumId=" + song.getFsFaId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除歌曲异常。", e);
            mm.addAttribute("errMsg", "删除歌曲异常");
            return "error/error";
        }
    }

    /**
     * 恢复
     *
     * @param id
     * @return
     */
    @RequestMapping("reBack")
    public String reBack(ModelMap mm, Long id) {
        try {
            FrsSong song = songService.getById(id);
            song.setFsIsValid(1);
            songService.updateById(song);
            return "redirect:list?albumId=" + song.getFsFaId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复歌曲异常。", e);
            mm.addAttribute("errMsg", "恢复歌曲异常");
            return "error/error";
        }
    }
}
