package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.ZyxNewsConst;
import com.wx.no_five_row_six.entity.FrsZyxNews;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dxl
 * @version 2020/9/27 18:36
 */
@Controller
public class AdminZyxNewsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminZyxNewsController.class);
    @Autowired
    IFrsZyxNewsService newsService;

    public String list(Long moduleId, ModelMap mm, String keyword, Integer state, Integer current, Integer size, String startDate,
                       String endDate) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        if (state == null) {
            state = 1;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        if (moduleId != null) {
            // 查询条件
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitleOne, keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitleTwo, keyword)
                    .ge(StringUtils.isNotEmpty(startDate), FrsZyxNews::getZnDate, TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
                    .le(StringUtils.isNotEmpty(endDate), FrsZyxNews::getZnDate, TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
                    .eq(FrsZyxNews::getZnIsValid, state);
            page = newsService.page(page, queryWrapper);
            if (ZyxNewsConst.ALBUM == moduleId) {
                return ZyxNewsConst.getModelUrl(moduleId) + "/list";
            }
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("moduleId", moduleId);
        return "error/error";
    }

    /**
     * 添加界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String newsAddUI(Long moduleId, ModelMap mm) {
        if (moduleId != null) {
            return ZyxNewsConst.getModelUrl(moduleId) + "/add";
        }
        mm.addAttribute("errMsg", "添加异常");
        return "error/error";
    }

    /**
     * 编辑界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            FrsZyxNews zyxNews = newsService.getById(id);
            Long moduleId = zyxNews.getZnNcId();

            mm.addAttribute("zyxNews", zyxNews);
            return ZyxNewsConst.getModelUrl(moduleId) + "/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-编辑时获取信息异常", e);
            mm.addAttribute("errMsg", "后台管理-编辑时获取信息异常");
            return "error/error";
        }
    }

    /**
     * 保存演唱会
     *
     * @param zyxNews
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsZyxNews zyxNews) {
        try {
            if (zyxNews.getZnId() == null) {
                if (ZyxNewsConst.PROGRAM == zyxNews.getZnNcId()) {
                    zyxNews.setZnFromId(ZyxNewsConst.CONCERT);
                    zyxNews.setZnFrom(ZyxNewsConst.getModelName(ZyxNewsConst.CONCERT));
                } else if (ZyxNewsConst.SONG == zyxNews.getZnNcId()) {
                    zyxNews.setZnFromId(ZyxNewsConst.ALBUM);
                    zyxNews.setZnFrom(ZyxNewsConst.getModelName(ZyxNewsConst.ALBUM));
                }
                zyxNews.setZnCreateTime(TimeUtil.dateToLong());
                zyxNews.setZnIsValid(ZyxNewsConst.VALID);
                newsService.save(zyxNews);
            } else {
                zyxNews.setZnUpdateTime(TimeUtil.dateTolong());
                newsService.updateById(zyxNews);
            }
            return "redirect:list?moduleId=" + zyxNews.getZnNcId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改信息异常", e);
            mm.addAttribute("errMsg", "后台管理-保存或修改信息异常");
            return "error/error";
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsZyxNews zyxNews = newsService.getById(id);
            zyxNews.setZnIsValid(ZyxNewsConst.NOT_VALID);
            newsService.updateById(zyxNews);
            return "redirect:list?moduleId" + zyxNews.getZnNcId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除信息异常", e);
            mm.addAttribute("errMsg", "后台管理-删除信息异常");
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
            FrsZyxNews zyxNews = newsService.getById(id);
            zyxNews.setZnIsValid(ZyxNewsConst.VALID);
            newsService.updateById(zyxNews);
            return "redirect:list?moduleId=" + zyxNews.getZnNcId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复信息异常。", e);
            mm.addAttribute("errMsg", "恢复信息异常");
            return "error/error";
        }
    }
}
