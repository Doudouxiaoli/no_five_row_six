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
 * @version 2020/10/15 13:27
 * @desc 后台管理-代言
 */
@Controller
@RequestMapping("/admin/zyx/endorsement")
public class AdminEndorsementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminEndorsementController.class);
    @Autowired
    private IFrsZyxNewsService endorsementService;

    /**
     * 代言列表
     *
     * @param mm
     * @param tagId 代言类型
     * @return
     */
    @RequestMapping(value = {"list"})
    public String list(ModelMap mm, Integer tagId) {
        mm.addAttribute("fromId", ZyxNewsConst.ENDORSEMENT);
        mm.addAttribute("tagId", tagId);
        if (ZyxNewsConst.ENDORSEMENT_FOOT_ID == tagId) {
            return "admin/zyx/endorsement/food";
        } else if (ZyxNewsConst.ENDORSEMENT_MAKEUP_ID == tagId) {
            return "admin/zyx/endorsement/makeup";
        } else if (ZyxNewsConst.ENDORSEMENT_CLOTHES_ID == tagId) {
            return "admin/zyx/endorsement/clothes";
        } else if (ZyxNewsConst.ENDORSEMENT_LUXURY_ID == tagId) {
            return "admin/zyx/endorsement/luxury";
        } else if (ZyxNewsConst.ENDORSEMENT_GAME_ID == tagId) {
            return "admin/zyx/endorsement/game";
        } else {
            return "error/error";
        }
    }

    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(String keyword, Integer current, Integer size, Integer tagId) {
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
                    .or()
                    .like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnFrom, keyword)
                    .eq(null != tagId, FrsZyxNews::getZnTagIds, tagId)
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.ENDORSEMENT);
            page = endorsementService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            String typeName = ZyxNewsConst.getEndorsementType(tagId);
            LOGGER.error("后台管理-" + typeName + "列表异常", e);
            return JacksonMapper.newErrorInstance("后台管理-" + typeName + "列表异常");
        }

    }

    /**
     * 编辑代言界面
     *
     * @param mm
     * @param id    代言产品id
     * @param tagId 代言类型
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id, Integer tagId) {
        try {
            String typeName = null;
            if (null == id) {
                typeName = ZyxNewsConst.getEndorsementType(tagId);
                mm.addAttribute("title", typeName + "添加");
            } else {
                FrsZyxNews endorsement = endorsementService.getById(id);
                typeName = ZyxNewsConst.getEndorsementType(endorsement.getZnTagIds());
                mm.addAttribute("title", typeName + "编辑");
                tagId = endorsement.getZnTagIds();
                mm.addAttribute("endorsement", endorsement);
            }
            mm.addAttribute("tagId", tagId);
            mm.addAttribute("fromId", ZyxNewsConst.ENDORSEMENT);
            return "admin/zyx/endorsement/edit";
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
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews endorsement) {
        try {
            if (null == endorsement.getZnTagIds()) {
                mm.addAttribute("errMsg", "参数传递不完整！");
                return "error/error";
            }
            if (endorsement.getZnId() == null) {
                endorsement.setZnNcId(ZyxNewsConst.ENDORSEMENT);
                endorsement.setZnCreateUserId(AdminUserUtil.getUserId());
                endorsement.setZnCreateUserName(AdminUserUtil.getShowName());
                endorsement.setZnCreateTime(TimeUtil.dateToLong());
                endorsement.setZnIsValid(ZyxNewsConst.VALID);
                endorsementService.save(endorsement);
            } else {
                endorsement.setZnUpdateTime(TimeUtil.dateTolong());
                endorsement.setZnUpdateUserId(AdminUserUtil.getUserId());
                endorsement.setZnUpdateUserName(AdminUserUtil.getShowName());
                endorsementService.updateById(endorsement);
            }
            return "redirect:list?tagId=" + endorsement.getZnTagIds();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改异常。", e);
            mm.addAttribute("errMsg", "保存或修改异常");
            return "error/error";
        }
    }
}
