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
 * @version 2020/10/14 13:27
 * @desc 后台管理-演唱会
 */
@Controller
@RequestMapping("/admin/zyx/concert")
public class AdminConcertController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConcertController.class);

    @Autowired
    private IFrsZyxNewsService concertService;

    @RequestMapping("list")
    public String list() {
        return "admin/zyx/concert/list";
    }

    /**
     * 演唱会列表
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(ModelMap mm, String keyword,Integer current, Integer size) {
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
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.CONCERT)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = concertService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-演唱会列表异常", e);
            mm.addAttribute("errMsg", "演唱会列表异常");
            return JacksonMapper.newErrorInstance("后台管理-演唱会列表异常");
        }
    }

    /**
     * 编辑演唱会界面
     *
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            if (null == id) {
                mm.addAttribute("title", "演唱会添加");
            } else {
                mm.addAttribute("title", "演唱会编辑");
                FrsZyxNews concert = concertService.getById(id);
                mm.addAttribute("concert", concert);
            }
            return "admin/zyx/concert/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取演唱会异常。", e);
            mm.addAttribute("errMsg", "获取演唱会异常");
            return "error/error";
        }
    }

    /**
     * 保存演唱会
     *
     * @param concert
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews concert) {
        try {
            if (concert.getZnId() == null) {
                concert.setZnNcId(ZyxNewsConst.CONCERT);
                concert.setZnCreateTime(TimeUtil.dateToLong());
                concert.setZnIsValid(ZyxNewsConst.VALID);
                concertService.save(concert);
            } else {
                concert.setZnUpdateTime(TimeUtil.dateTolong());
                concert.setZnUpdateUserId(AdminUserUtil.getUserId());
                concert.setZnUpdateUserName(AdminUserUtil.getShowName());
                concertService.updateById(concert);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改演唱会异常。", e);
            mm.addAttribute("errMsg", "保存或修改演唱会异常");
            return "error/error";
        }
    }

    /**
     * 删除演唱会
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JsonNode del(Long id) {
        try {
            FrsZyxNews concert = concertService.getById(id);
            concert.setZnIsValid(ZyxNewsConst.NOT_VALID);
            concertService.updateById(concert);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除演唱会异常。", e);
            return JacksonMapper.newErrorInstance("删除演唱会异常");
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
            FrsZyxNews concert = concertService.getById(id);
            concert.setZnIsValid(ZyxNewsConst.VALID);
            concertService.updateById(concert);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复演唱会异常。", e);
            return JacksonMapper.newErrorInstance("恢复演唱会异常");
        }
    }
}
