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
 * @desc 后台管理-专辑
 */
@Controller
@RequestMapping("/admin/zyx/album")
public class AdminAlbumController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAlbumController.class);

    @Autowired
    private IFrsZyxNewsService albumService;

    @RequestMapping("list")
    public String list() {
        return "admin/zyx/album/list";
    }

    /**
     * 专辑列表
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(ModelMap mm, String keyword, String startDate, String endDate, Integer current, Integer size) {
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
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.ALBUM)
                    .lt(StringUtils.isNotEmpty(endDate), FrsZyxNews::getZnDate, endDate)
                    .gt(StringUtils.isNotEmpty(startDate), FrsZyxNews::getZnDate, startDate);
            page = albumService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-专辑列表异常", e);
            mm.addAttribute("errMsg", "专辑列表异常");
            return JacksonMapper.newErrorInstance("后台管理-专辑列表异常");
        }

    }

    /**
     * 编辑专辑界面
     *
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            if (null == id) {
                mm.addAttribute("title", "专辑添加");
            } else {
                mm.addAttribute("title", "专辑编辑");
                FrsZyxNews album = albumService.getById(id);
                mm.addAttribute("album", album);
            }
            return "admin/zyx/album/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取专辑异常。", e);
            mm.addAttribute("errMsg", "获取专辑异常");
            return "error/error";
        }
    }

    /**
     * 保存专辑
     *
     * @param album
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews album) {
        try {
            if (album.getZnId() == null) {
                album.setZnNcId(ZyxNewsConst.ALBUM);
                album.setZnCreateUserId(AdminUserUtil.getUserId());
                album.setZnCreateUserName(AdminUserUtil.getShowName());
                album.setZnCreateTime(TimeUtil.dateToLong());
                album.setZnIsValid(ZyxNewsConst.VALID);
                albumService.save(album);
            } else {
                album.setZnUpdateTime(TimeUtil.dateTolong());
                album.setZnUpdateUserId(AdminUserUtil.getUserId());
                album.setZnUpdateUserName(AdminUserUtil.getShowName());
                albumService.updateById(album);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改专辑异常。", e);
            mm.addAttribute("errMsg", "保存或修改专辑异常");
            return "error/error";
        }
    }
}
