package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsEndorsement;
import com.wx.no_five_row_six.service.impl.FrsEndorsementServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/endorsement")
public class AdminEndorsementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminEndorsementController.class);
    @Autowired
    private FrsEndorsementServiceImpl endorsementService;

    /**
     * 代言列表
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
        QueryWrapper<FrsEndorsement> queryWrapper = null;
        IPage<FrsEndorsement> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper = new QueryWrapper<FrsEndorsement>().like(StringUtils.isNotEmpty(keyword), "fe_name", keyword)
                    .eq("fe_is_valid", state)
                    .eq("fe_type", type)
                    .orderByDesc("fe_sort");
            page = endorsementService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-代言列表异常", e);
            mm.addAttribute("errMsg", "代言列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("type", type);
        if (Const.ENDORSEMENT_FOOT_ID == type) {
            return "admin/endorsement/listFood";
        } else if (Const.ENDORSEMENT_MAKEUP_ID == type) {
            return "admin/endorsement/listMakeup";
        } else if (Const.ENDORSEMENT_CLOTHES_ID == type) {
            return "admin/endorsement/listClothes";
        } else if (Const.ENDORSEMENT_LUXURY_ID == type) {
            return "admin/endorsement/listLuxury";
        } else if (Const.ENDORSEMENT_GAME_ID == type) {
            return "admin/endorsement/listGame";
        } else {
            return "error/error";
        }
    }

    /**
     * 添加代言界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String newsAddUI(ModelMap mm, Integer type) {
        if (null != type) {
            mm.addAttribute("type", type);
            mm.addAttribute("typeName", Const.getType(type));
            mm.addAttribute("errMsg", "后台管理-代言>添加:类型传值错误");
            return "admin/endorsement/edit";
        } else {
            return "error/error";
        }
    }

    /**
     * 编辑代言界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            FrsEndorsement endorsement = endorsementService.getById(id);
            mm.addAttribute("goods", endorsement);
            Integer type = endorsement.getFeType();
            mm.addAttribute("type", type);
            mm.addAttribute("typeName", Const.getType(type));
            return "admin/endorsement/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取代言异常。", e);
            mm.addAttribute("errMsg", "获取代言异常");
            return "error/error";
        }
    }

    /**
     * 保存代言
     *
     * @param endorsement
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsEndorsement endorsement) {
        try {
            if (endorsement.getFeId() == null) {
                endorsement.setFeStartTime(TimeUtil.stringToLong(endorsement.getFeStartTimeStr(), "yyyy-MM-dd"));
                endorsement.setFeEndTime(TimeUtil.stringToLong(endorsement.getFeEndTimeStr(), "yyyy-MM-dd"));
                endorsement.setFeCreateTime(TimeUtil.dateToLong());
                endorsement.setFeIsValid(1);
                endorsementService.save(endorsement);
            } else {
                endorsement.setFeStartTime(TimeUtil.stringToLong(endorsement.getFeStartTimeStr(), "yyyy-MM-dd"));
                endorsement.setFeEndTime(TimeUtil.stringToLong(endorsement.getFeEndTimeStr(), "yyyy-MM-dd"));
                endorsement.setFeUpdateTime(TimeUtil.dateTolong());
                endorsementService.updateById(endorsement);
            }
            return "redirect:list?type=" + endorsement.getFeType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改代言异常。", e);
            mm.addAttribute("errMsg", "保存或修改代言异常");
            return "error/error";
        }
    }

    /**
     * 删除代言
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsEndorsement endorsement = endorsementService.getById(id);
            endorsement.setFeIsValid(0);
            endorsementService.updateById(endorsement);
            return "redirect:list?type=" + endorsement.getFeType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除代言异常。", e);
            mm.addAttribute("errMsg", "删除代言异常");
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
            FrsEndorsement endorsement = endorsementService.getById(id);
            endorsement.setFeIsValid(1);
            endorsementService.updateById(endorsement);
            return "redirect:list?type=" + endorsement.getFeType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复代言异常。", e);
            mm.addAttribute("errMsg", "恢复代言异常");
            return "error/error";
        }
    }
}
