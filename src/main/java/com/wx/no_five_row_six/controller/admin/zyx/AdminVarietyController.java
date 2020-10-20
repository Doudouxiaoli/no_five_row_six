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
 * @version 2020/10/19 10:00
 * @desc 后台管理-影视作品-综艺
 */
@Controller
@RequestMapping("/admin/zyx/variety")
public class AdminVarietyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminVarietyController.class);
    @Autowired
    private IFrsZyxNewsService varietyService;

    /**
     * 综艺列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "admin/zyx/variety/list";
    }

    /**
     * 综艺列表AJAX
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
                    .eq(FrsZyxNews::getZnTagIds, ZyxNewsConst.FILM_VARIETY_ID)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = varietyService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-综艺列表异常", e);
            mm.addAttribute("errMsg", "综艺列表异常");
            return JacksonMapper.newErrorInstance("后台管理-综艺列表异常");
        }
    }

    /**
     * 综艺编辑界面
     *
     * @param id 综艺id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            if (null == id) {
                mm.addAttribute("title", "综艺添加");
            } else {
                mm.addAttribute("title", "综艺编辑");
                FrsZyxNews varietyParent = varietyService.getById(id);
                mm.addAttribute("varietyParent", varietyParent);
            }
            return "admin/zyx/variety/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取综艺异常。", e);
            mm.addAttribute("errMsg", "获取综艺异常");
            return "error/error";
        }
    }

    /**
     * 保存综艺
     *
     * @param movie
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews movie) {
        try {
            if (movie.getZnId() == null) {
                movie.setZnNcId(ZyxNewsConst.FILM);
                movie.setZnTagIds(ZyxNewsConst.FILM_VARIETY_ID);
                movie.setZnCreateTime(TimeUtil.dateToLong());
                movie.setZnIsValid(ZyxNewsConst.VALID);
                varietyService.save(movie);
            } else {
                movie.setZnUpdateTime(TimeUtil.dateTolong());
                movie.setZnUpdateUserId(AdminUserUtil.getUserId());
                movie.setZnUpdateUserName(AdminUserUtil.getShowName());
                varietyService.updateById(movie);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改综艺异常。", e);
            mm.addAttribute("errMsg", "保存或修改综艺异常");
            return "error/error";
        }
    }

    /**
     * 删除综艺
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JsonNode del(Long id) {
        try {
            FrsZyxNews movie = varietyService.getById(id);
            movie.setZnIsValid(ZyxNewsConst.NOT_VALID);
            varietyService.updateById(movie);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除综艺异常。", e);
            return JacksonMapper.newErrorInstance("删除综艺异常");
        }
    }

    /**
     * 恢复综艺
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("reBack")
    public JsonNode reBack(Long id) {
        try {
            FrsZyxNews movie = varietyService.getById(id);
            movie.setZnIsValid(ZyxNewsConst.VALID);
            varietyService.updateById(movie);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复综艺异常。", e);
            return JacksonMapper.newErrorInstance("恢复综艺异常");
        }
    }

    /**
     * 单期综艺详情列表
     *
     * @param fromId 综艺id
     * @param mm
     * @return
     */
    @RequestMapping("moreList")
    public String moreList(Integer fromId, ModelMap mm) {
        mm.addAttribute("fromId", fromId);
        return "admin/zyx/variety/more/list";
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
            page = varietyService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-单期综艺列表异常", e);
            return JacksonMapper.newErrorInstance("后台管理-单期综艺列表异常");
        }
    }

    /**
     * 单期综艺编辑界面
     *
     * @param pk 单期综艺id(编辑时使用)
     * @param pk 综艺id（添加时使用）
     * @return
     */
    @RequestMapping("moreEdit")
    public String moreEdit(ModelMap mm, Long pk, Long fromId) {
        try {
            if (null == fromId) {
                mm.addAttribute("errMsg", "参数传递不完整！");
                return "error/error";
            }
            FrsZyxNews varietyFrom = varietyService.getById(fromId);
            String from = varietyFrom.getZnTitle();
            if (null == pk) {
                mm.addAttribute("title", from + "添加");
            } else {
                mm.addAttribute("title", from + "编辑");
                FrsZyxNews varietyChild = varietyService.getById(pk);
                fromId = varietyChild.getZnFromId();
                mm.addAttribute("varietyChild", varietyChild);
            }
            mm.addAttribute("from", from);
            mm.addAttribute("fromId", fromId);
            return "admin/zyx/variety/more/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取单期综艺异常。", e);
            mm.addAttribute("errMsg", "获取单期综艺异常");
            return "error/error";
        }
    }

    /**
     * 保存单期综艺
     *
     * @param varietyChild
     * @return
     */
    @ResponseBody
    @RequestMapping("moreSave")
    public JsonNode moreSave(FrsZyxNews varietyChild) {
        try {
            if (null==varietyChild.getZnId()) {
                varietyChild.setZnCreateTime(TimeUtil.dateToLong());
                varietyChild.setZnIsValid(ZyxNewsConst.VALID);
                varietyService.save(varietyChild);
            } else {
                varietyChild.setZnUpdateTime(TimeUtil.dateTolong());
                varietyChild.setZnUpdateUserId(AdminUserUtil.getUserId());
                varietyChild.setZnUpdateUserName(AdminUserUtil.getShowName());
                varietyService.updateById(varietyChild);
            }
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改单期综艺异常。", e);
            return JacksonMapper.newErrorInstance("保存或修改单期综艺异常");
        }
    }
}
