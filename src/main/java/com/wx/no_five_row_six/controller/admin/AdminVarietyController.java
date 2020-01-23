package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsVariety;
import com.wx.no_five_row_six.service.impl.FrsMolivideoServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsVarietyServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/molivideo/variety")
public class AdminVarietyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminVarietyController.class);

    @Autowired
    private FrsVarietyServiceImpl varietyService;

    @Autowired
    private FrsMolivideoServiceImpl molivideoService;

    /**
     * 综艺列表
     *
     * @param mm
     * @param keyword
     * @param state
     * @param current
     * @param size
     * @param startDate
     * @param endDate
     * @param workId
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, String keyword, Integer state, Integer current, Integer size, String startDate, String endDate, Long workId) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        if (state == null) {
            state = 1;
        }
        QueryWrapper<FrsVariety> queryWrapper = null;
        IPage<FrsVariety> page = new Page<>(current, size);
        try {
            queryWrapper = new QueryWrapper<FrsVariety>().like(StringUtils.isNotEmpty(keyword), "fv_name", keyword)
                    .ge(StringUtils.isNotEmpty(startDate), "fv_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
                    .le(StringUtils.isNotEmpty(endDate), "fv_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
                    .eq("fv_is_valid", state)
                    .eq("fv_fmv_id", workId)
                    .orderByDesc("fv_sort", "fv_time");
            page = varietyService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-综艺列表异常", e);
            mm.addAttribute("errMsg", "综艺列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("workId", workId);
//        返回综艺列表时使用
        mm.addAttribute("type",molivideoService.getById(workId).getFmvType());
        return "admin/molivideo/variety/detail/list";
    }

    /**
     * 添加综艺界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String newsAddUI(ModelMap mm, Long workId) {
        try {
            mm.addAttribute("workId", workId);
            String workName = molivideoService.getById(workId).getFmvName();
            mm.addAttribute("workName", workName);
            return "admin/molivideo/variety/detail/add";
        } catch (Exception e) {
            mm.addAttribute("errMsg", "后台管理-综艺>添加:类型传值错误");
            e.printStackTrace();
        }
        mm.addAttribute("errMsg", "获取综艺异常");
        return "error/error";
    }

    /**
     * 编辑综艺界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            FrsVariety variety = varietyService.getById(id);
            if (variety != null) {
                Long workId = variety.getFvFmvId();
                String workName = variety.getFvName();
                mm.addAttribute("workId", workId);
                mm.addAttribute("workName", workName);
                mm.addAttribute("variety", variety);
                return "admin/molivideo/variety/detail/edit";
            } else {
                mm.addAttribute("errMsg", "后台管理-综艺>编辑:类型传值错误");
                return "error/error";
            }
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
     * @param variety
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsVariety variety) {
        try {
            if (variety.getFvId() == null) {
                variety.setFvTime(TimeUtil.stringToLong(variety.getFvTimeStr(), "yyyy-MM-dd"));
                variety.setFvCreateTime(TimeUtil.dateToLong());
                variety.setFvIsValid(1);
                varietyService.save(variety);
            } else {
                variety.setFvTime(TimeUtil.stringToLong(variety.getFvTimeStr(), "yyyy-MM-dd"));
                variety.setFvUpdateTime(TimeUtil.dateTolong());
                varietyService.updateById(variety);
            }
            return "redirect:list?workId=" + variety.getFvFmvId();
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
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsVariety Variety = varietyService.getById(id);
            Variety.setFvIsValid(0);
            varietyService.updateById(Variety);
            return "redirect:list?workId=" + Variety.getFvFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除综艺异常。", e);
            mm.addAttribute("errMsg", "删除综艺异常");
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
            FrsVariety Variety = varietyService.getById(id);
            Variety.setFvIsValid(1);
            varietyService.updateById(Variety);
            return "redirect:list?workId=" + Variety.getFvFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复综艺异常。", e);
            mm.addAttribute("errMsg", "恢复综艺异常");
            return "error/error";
        }
    }

}
