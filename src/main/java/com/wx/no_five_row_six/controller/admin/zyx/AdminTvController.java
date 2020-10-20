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
 * @version 2020/10/16 17:57
 * @desc 后台管理-影视作品-电视剧
 */
@Controller
@RequestMapping("/admin/zyx/tv")
public class AdminTvController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTvController.class);
    @Autowired
    private IFrsZyxNewsService tvService;

    /**
     * 电视剧列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "admin/zyx/tv/list";
    }

    /**
     * 电视剧列表AJAX
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(ModelMap mm, String keyword, Integer current, Integer size) {
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
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.FILM)
                    .eq(FrsZyxNews::getZnTagIds, ZyxNewsConst.FILM_TV_ID)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = tvService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-电视剧列表异常", e);
            mm.addAttribute("errMsg", "电视剧列表异常");
            return JacksonMapper.newErrorInstance("后台管理-电视剧列表异常");
        }
    }

    /**
     * 电视剧编辑界面
     *
     * @param id 电视剧id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            if (null == id) {
                mm.addAttribute("title", "电视剧添加");
            } else {
                mm.addAttribute("title", "电视剧编辑");
                FrsZyxNews tvParent = tvService.getById(id);
                mm.addAttribute("tvParent", tvParent);
            }
            return "admin/zyx/tv/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取电视剧异常。", e);
            mm.addAttribute("errMsg", "获取电视剧异常");
            return "error/error";
        }
    }

    /**
     * 保存电视剧
     *
     * @param movie
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews movie) {
        try {
            if (movie.getZnId() == null) {
                movie.setZnNcId(ZyxNewsConst.FILM);
                movie.setZnTagIds(ZyxNewsConst.FILM_TV_ID);
                movie.setZnCreateTime(TimeUtil.dateToLong());
                movie.setZnIsValid(ZyxNewsConst.VALID);
                tvService.save(movie);
            } else {
                movie.setZnUpdateTime(TimeUtil.dateTolong());
                movie.setZnUpdateUserId(AdminUserUtil.getUserId());
                movie.setZnUpdateUserName(AdminUserUtil.getShowName());
                tvService.updateById(movie);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改电视剧异常。", e);
            mm.addAttribute("errMsg", "保存或修改电视剧异常");
            return "error/error";
        }
    }

    /**
     * 删除电视剧
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JsonNode del(Long id) {
        try {
            FrsZyxNews movie = tvService.getById(id);
            movie.setZnIsValid(ZyxNewsConst.NOT_VALID);
            tvService.updateById(movie);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除电视剧异常。", e);
            return JacksonMapper.newErrorInstance("删除电视剧异常");
        }
    }

    /**
     * 恢复电视剧
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("reBack")
    public JsonNode reBack(Long id) {
        try {
            FrsZyxNews movie = tvService.getById(id);
            movie.setZnIsValid(ZyxNewsConst.VALID);
            tvService.updateById(movie);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复电视剧异常。", e);
            return JacksonMapper.newErrorInstance("恢复电视剧异常");
        }
    }

    /**
     * 单集电视剧详情列表
     *
     * @param fromId 电视剧id
     * @param mm
     * @return
     */
    @RequestMapping("moreList")
    public String moreList(Integer fromId, ModelMap mm) {
        mm.addAttribute("fromId", fromId);
        return "admin/zyx/tv/more/list";
    }

    @ResponseBody
    @RequestMapping("moreListAjax")
    public JsonNode moreListAjax(String keyword, Integer current, Integer size, Long fromId) {
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
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnPace, keyword)
                    .eq(FrsZyxNews::getZnFromId, fromId)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = tvService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-单集电视剧列表异常", e);
            return JacksonMapper.newErrorInstance("后台管理-单集电视剧列表异常");
        }
    }

    /**
     * 单集电视剧编辑界面
     *
     * @param pk 单集电视剧id(编辑时使用)
     * @param pk 电视剧id（添加时使用）
     * @return
     */
    @RequestMapping("moreEdit")
    public String moreEdit(ModelMap mm, Long pk, Long fromId) {
        try {
            if (null == fromId) {
                mm.addAttribute("errMsg", "参数传递不完整！");
                return "error/error";
            }
            FrsZyxNews tvFrom = tvService.getById(fromId);
            String from = tvFrom.getZnTitle();
            if (null == pk) {
                mm.addAttribute("title", from + "添加");
            } else {
                mm.addAttribute("title", from + "编辑");
                FrsZyxNews tvChild = tvService.getById(pk);
                fromId = tvChild.getZnFromId();
                mm.addAttribute("tvChild", tvChild);
            }
            mm.addAttribute("from", from);
            mm.addAttribute("fromId", fromId);
            return "admin/zyx/tv/more/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取单集电视剧异常。", e);
            mm.addAttribute("errMsg", "获取单集电视剧异常");
            return "error/error";
        }
    }

    /**
     * 保存单集电视剧
     *
     * @param tvChild
     * @return
     */
    @ResponseBody
    @RequestMapping("moreSave")
    public JsonNode moreSave(FrsZyxNews tvChild) {
        try {
            if (tvChild.getZnId() == null) {
                tvChild.setZnCreateTime(TimeUtil.dateToLong());
                tvChild.setZnIsValid(ZyxNewsConst.VALID);
                tvService.save(tvChild);
            } else {
                tvChild.setZnUpdateTime(TimeUtil.dateTolong());
                tvChild.setZnUpdateUserId(AdminUserUtil.getUserId());
                tvChild.setZnUpdateUserName(AdminUserUtil.getShowName());
                tvService.updateById(tvChild);
            }
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改单集电视剧异常。", e);
            return JacksonMapper.newErrorInstance("保存或修改单集电视剧异常");
        }
    }
}
