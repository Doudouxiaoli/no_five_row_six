package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsMolivideo;
import com.wx.no_five_row_six.service.impl.FrsMolivideoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/molivideo")
public class AdminMolivideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminMolivideoController.class);

    @Autowired
    private FrsMolivideoServiceImpl molivideoService;

    /**
     * 影视作品列表
     *
     * @param mm
     * @param keyword
     * @param state
     * @param current
     * @param size
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, String keyword, Integer state, Integer current, Integer size, String startDate, String endDate, Integer type) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        if (state == null) {
            state = 1;
        }
        QueryWrapper<FrsMolivideo> queryWrapper = null;
        IPage<FrsMolivideo> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper = new QueryWrapper<FrsMolivideo>().like(StringUtils.isNotEmpty(keyword), "fm_name", keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), "fm_actors", keyword)
                    .ge(StringUtils.isNotEmpty(startDate), "fm_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
                    .le(StringUtils.isNotEmpty(endDate), "fm_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
                    .eq("fm_is_valid", state)
                    .eq("fm_type", type)
                    .orderByDesc("fm_sort","fm_time");
            page = molivideoService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-影视作品列表异常", e);
            mm.addAttribute("errMsg", "影视作品列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("type", type);
        if (Const.MOLIVIDEO_MOVIE_ID == type) {
            return "admin/molivideo/movie/list";
        } else if (Const.MOLIVIDEO_TV_ID == type) {
            return "admin/molivideo/tv/list";
        } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
            return "admin/molivideo/variety/list";
        } else {
            return "error/error";
        }
    }

    /**
     * 添加影视作品界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String newsAddUI(ModelMap mm, Integer type) {
        mm.addAttribute("type", type);
        if (Const.MOLIVIDEO_MOVIE_ID == type) {
            mm.addAttribute("typeName", Const.MOLIVIDEO_MOVIE_TYPE);
            return "admin/molivideo/movie/add";
        } else if (Const.MOLIVIDEO_TV_ID == type) {
            mm.addAttribute("typeName", Const.MOLIVIDEO_TV_TYPE);
            return "admin/molivideo/tv/add";
        } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
            mm.addAttribute("typeName", Const.MOLIVIDEO_VARIETY_TYPE);
            return "admin/molivideo/variety/add";
        } else {
            mm.addAttribute("errMsg", "后台管理-影视作品>电影添加:类型传值错误");
            return "error/error";
        }
    }

    /**
     * 编辑影视作品界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            FrsMolivideo molivideo = molivideoService.getById(id);
            Integer type = molivideo.getFmType();
            String typeName = molivideo.getFmTypeName();
            mm.addAttribute("molivideo", molivideo);
            mm.addAttribute("type", type);
            mm.addAttribute("typeName", typeName);
            if (Const.MOLIVIDEO_MOVIE_ID == type) {
                return "admin/molivideo/movie/edit";
            } else if (Const.MOLIVIDEO_TV_ID == type) {
                return "admin/molivideo/tv/edit";
            } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
                return "admin/molivideo/variety/edit";
            } else {
                mm.addAttribute("errMsg", "后台管理-影视作品>电影编辑:类型传值错误");
                return "error/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取影视作品异常。", e);
            mm.addAttribute("errMsg", "获取影视作品异常");
            return "error/error";
        }
    }

    /**
     * 保存影视作品
     *
     * @param molivideo
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsMolivideo molivideo) {
        try {
            if (molivideo.getFmId() == null) {
                molivideo.setFmTime(TimeUtil.stringToLong(molivideo.getFmTimeStr(), "yyyy-MM-dd"));
                molivideo.setFmCreateTime(TimeUtil.dateToLong());
                molivideo.setFmIsValid(1);
                molivideoService.save(molivideo);
            } else {
                molivideo.setFmTime(TimeUtil.stringToLong(molivideo.getFmTimeStr(), "yyyy-MM-dd"));
                molivideo.setFmUpdateTime(TimeUtil.dateTolong());
                molivideoService.updateById(molivideo);
            }
            return "redirect:list?type=" + molivideo.getFmType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改影视作品异常。", e);
            mm.addAttribute("errMsg", "保存或修改影视作品异常");
            return "error/error";
        }
    }

    /**
     * 删除影视作品
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsMolivideo molivideo = molivideoService.getById(id);
            molivideo.setFmIsValid(0);
            molivideoService.updateById(molivideo);
            return "redirect:list?type=" + molivideo.getFmType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除影视作品异常。", e);
            mm.addAttribute("errMsg", "删除影视作品异常");
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
            FrsMolivideo molivideo = molivideoService.getById(id);
            molivideo.setFmIsValid(1);
            molivideoService.updateById(molivideo);
            return "redirect:list?type=" + molivideo.getFmType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复影视作品异常。", e);
            mm.addAttribute("errMsg", "恢复影视作品异常");
            return "error/error";
        }
    }
}
