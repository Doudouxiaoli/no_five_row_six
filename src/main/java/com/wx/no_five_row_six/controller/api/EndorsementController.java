package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsEndorsement;
import com.wx.no_five_row_six.entity.FrsSong;
import com.wx.no_five_row_six.service.impl.FrsEndorsementServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/endorsement")
public class EndorsementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndorsementController.class);

    @Autowired
    private FrsEndorsementServiceImpl endorsementService;

    /**
     * 列表
     *
     * @param mm
     * @param current
     * @param size
     * @param type
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, Integer current, Integer size, String keyword, Integer type) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        Integer state = 1;
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
            LOGGER.error("专辑列表异常", e);
            mm.addAttribute("errMsg", "专辑列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("state", state);
        mm.addAttribute("type", type);
        if (Const.ENDORSEMENT_FOOT_ID == type) {
            return "pc/endorsement/listFood";
        } else if (Const.ENDORSEMENT_MAKEUP_ID == type) {
            return "pc/endorsement/listMakeup";
        } else if (Const.ENDORSEMENT_CLOTHES_ID == type) {
            return "pc/endorsement/listClothes";
        } else if (Const.ENDORSEMENT_LUXURY_ID == type) {
            return "pc/endorsement/listLuxury";
        } else if (Const.ENDORSEMENT_GAME_ID == type) {
            return "pc/endorsement/listGame";
        } else {
            return "error/error";
        }
    }

    /**
     * 专辑详情页面
     *
     * @param mm
     * @param id
     * @param type
     * @return
     */
    @RequestMapping("/endorsementDetail")
    public String endorsementDetail(ModelMap mm, Long id, Integer type) {
        try {
            FrsEndorsement endorsement = endorsementService.getById(id);
            QueryWrapper<FrsEndorsement> queryWrapper = new QueryWrapper<FrsEndorsement>()
                    .eq("fe_fa_id", id)
                    .eq("fe_is_valid", 1)
                    .orderByDesc("fe_sort");
            mm.addAttribute("endorsement", endorsement);
            mm.addAttribute("type", Const.getType(type));
            return "pc/endorsement/endorsementDetail";
        } catch (Exception e) {
            e.printStackTrace();
            mm.addAttribute("errMsg", "专辑详情查询出错");
            return "error/error";
        }
    }

    /**
     * 列表ajax
     *
     * @param current
     * @param size
     * @param keyword
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("/listAjax")
    public JsonNode listAjax(Integer current, Integer size, String keyword, Integer type) {
        Integer state = 1;
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        QueryWrapper<FrsEndorsement> queryWrapper = null;
        IPage<FrsEndorsement> page = new Page<>(current, size);
        try {
            queryWrapper = new QueryWrapper<FrsEndorsement>()
                    .like(StringUtils.isNotEmpty(keyword), "fs_name", keyword)
                    .eq("fs_is_valid", state)
                    .orderByDesc("fs_sort", "fa_time");
            page = endorsementService.page(page, queryWrapper);
//            for (FrsEndorsement endorsement : page.getRecords()) {
//                endorsement.setFaTimeStr(TimeUtil.longToString(endorsement.getFaTime()));
//            }
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
    public JsonNode detail(Long endorsementId) {
        try {
            List<FrsEndorsement> endorsement = endorsementService.list(new QueryWrapper<FrsEndorsement>().eq("fe_id", endorsementId));
            return JacksonMapper.newDataInstance(endorsement);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情跳转出错");
        }
    }
}





