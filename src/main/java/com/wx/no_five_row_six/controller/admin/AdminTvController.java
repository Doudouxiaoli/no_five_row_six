package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsTv;
import com.wx.no_five_row_six.service.impl.FrsMolivideoServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsTvServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/molivideo/tv")
public class AdminTvController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTvController.class);

    @Autowired
    private FrsTvServiceImpl tvService;

    @Autowired
    private FrsMolivideoServiceImpl molivideoService;

    /**
     * 电视剧列表
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
        QueryWrapper<FrsTv> queryWrapper = null;
        IPage<FrsTv> page = new Page<>(current, size);
        try {
            queryWrapper = new QueryWrapper<FrsTv>().like(StringUtils.isNotEmpty(keyword), "ft_name", keyword)
                    .ge(StringUtils.isNotEmpty(startDate), "ft_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
                    .le(StringUtils.isNotEmpty(endDate), "ft_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
                    .eq("ft_is_valid", state)
                    .eq("ft_fmv_id", workId)
                    .orderByDesc("ft_sort", "ft_time");
            page = tvService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-电视剧列表异常", e);
            mm.addAttribute("errMsg", "电视剧列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("workId", workId);
        mm.addAttribute("type",molivideoService.getById(workId).getFmvType());
        return "admin/molivideo/tv/detail/list";
    }

    /**
     * 添加电视剧界面
     *
     * @return
     */
    @RequestMapping("addUI")
    public String newsAddUI(ModelMap mm, Long workId) {
        try {
            mm.addAttribute("workId", workId);
            String workName = molivideoService.getById(workId).getFmvName();
            mm.addAttribute("workName", workName);
            return "admin/molivideo/tv/detail/add";
        } catch (Exception e) {
            mm.addAttribute("errMsg", "后台管理-电视剧>添加:类型传值错误");
            e.printStackTrace();
        }
        mm.addAttribute("errMsg", "获取电视剧异常");
        return "error/error";
    }

    /**
     * 编辑电视剧界面
     *
     * @param id
     * @return
     */
    @RequestMapping("editUI")
    public String editUI(ModelMap mm, Long id) {
        try {
            FrsTv tv = tvService.getById(id);
            if (tv != null) {
                Long workId = tv.getFtFmvId();
                String workName = tv.getFtName();
                mm.addAttribute("workId", workId);
                mm.addAttribute("workName", workName);
                mm.addAttribute("tv", tv);
                return "admin/molivideo/tv/detail/edit";
            } else {
                mm.addAttribute("errMsg", "后台管理-电视剧>编辑:类型传值错误");
                return "error/error";
            }
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
     * @param tv
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsTv tv) {
        try {
            if (tv.getFtId() == null) {
                tv.setFtTime(TimeUtil.stringToLong(tv.getFtTimeStr(), "yyyy-MM-dd"));
                tv.setFtCreateTime(TimeUtil.dateToLong());
                tv.setFtIsValid(1);
                tvService.save(tv);
            } else {
                tv.setFtTime(TimeUtil.stringToLong(tv.getFtTimeStr(), "yyyy-MM-dd"));
                tv.setFtUpdateTime(TimeUtil.dateTolong());
                tvService.updateById(tv);
            }
            return "redirect:list?workId=" + tv.getFtFmvId();
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
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsTv tv = tvService.getById(id);
            tv.setFtIsValid(0);
            tvService.updateById(tv);
            return "redirect:list?workId=" + tv.getFtFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除电视剧异常。", e);
            mm.addAttribute("errMsg", "删除电视剧异常");
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
            FrsTv tv = tvService.getById(id);
            tv.setFtIsValid(1);
            tvService.updateById(tv);
            return "redirect:list?workId=" + tv.getFtFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复电视剧异常。", e);
            mm.addAttribute("errMsg", "恢复电视剧异常");
            return "error/error";
        }
    }

}
