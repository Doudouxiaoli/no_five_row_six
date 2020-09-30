//package com.wx.no_five_row_six.controller.admin;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.wx.common.util.TimeUtil;
//import com.wx.no_five_row_six.common.Const;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/admin/dance")
//public class AdminDanceController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDanceController.class);
//
//    @Autowired
//    private FrsDanceServiceImpl danceService;
//
//    /**
//     * 舞蹈列表
//     *
//     * @param mm
//     * @param keyword
//     * @param current
//     * @param size
//     * @param startDate
//     * @param endDate
//     * @return
//     */
//    @RequestMapping(value = {"/list"})
//    public String list(ModelMap mm, String keyword, Integer state, Integer current, Integer size, String startDate, String endDate) {
//        if (current == null) {
//            current = 1;
//        }
//        if (size == null) {
//            size = Const.ADMIN_ROWSPERPAGE_MORE;
//        }
//        if (state == null) {
//            state = 1;
//        }
//        QueryWrapper<FrsDance> queryWrapper = null;
//        IPage<FrsDance> page = new Page<>(current, size);
//        try {
//            // 查询条件
//            queryWrapper = new QueryWrapper<FrsDance>().like(StringUtils.isNotEmpty(keyword), "fd_tv_name", keyword)
//                    .ge(StringUtils.isNotEmpty(startDate), "fd_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
//                    .le(StringUtils.isNotEmpty(endDate), "fd_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
//                    .eq("fd_is_valid", state)
//                    .orderByDesc("fd_sort", "fd_time");
//            page = danceService.page(page, queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-舞蹈列表异常", e);
//            mm.addAttribute("errMsg", "舞蹈列表异常");
//            return "error/error";
//        }
//        mm.addAttribute("page", page);
//        mm.addAttribute("keyword", keyword);
//        mm.addAttribute("startDate", startDate);
//        mm.addAttribute("endDate", endDate);
//        mm.addAttribute("state", state);
//        return "admin/dance/list";
//    }
//
//    /**
//     * 添加舞蹈界面
//     *
//     * @return
//     */
//    @RequestMapping("addUI")
//    public String newsAddUI() {
//        return "admin/dance/add";
//    }
//
//    /**
//     * 编辑舞蹈界面
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("editUI")
//    public String editUI(ModelMap mm, Long id) {
//        try {
//            FrsDance dance = danceService.getById(id);
//            mm.addAttribute("dance", dance);
//            return "admin/dance/edit";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-获取舞蹈异常。", e);
//            mm.addAttribute("errMsg", "获取舞蹈异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 保存舞蹈
//     *
//     * @param dance
//     * @return
//     */
//    @RequestMapping("saveOrUpdate")
//    public String saveOrUpdate(ModelMap mm, FrsDance dance) {
//        try {
//            if (dance.getFdId() == null) {
//                dance.setFdTime(TimeUtil.stringToLong(dance.getFdTimeStr(), "yyyy-MM-dd"));
//                dance.setFdCreateTime(TimeUtil.dateToLong());
//                dance.setFdIsValid(1);
//                danceService.save(dance);
//            } else {
//                dance.setFdTime(TimeUtil.stringToLong(dance.getFdTimeStr(), "yyyy-MM-dd"));
//                dance.setFdUpdateTime(TimeUtil.dateTolong());
//                danceService.updateById(dance);
//            }
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-保存或修改舞蹈异常。", e);
//            mm.addAttribute("errMsg", "保存或修改舞蹈异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 删除舞蹈
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("del")
//    public String del(ModelMap mm, Long id) {
//        try {
//            FrsDance dance = danceService.getById(id);
//            dance.setFdIsValid(0);
//            danceService.updateById(dance);
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-删除舞蹈异常。", e);
//            mm.addAttribute("errMsg", "删除舞蹈异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 恢复
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("reBack")
//    public String reBack(ModelMap mm, Long id) {
//        try {
//            FrsDance dance = danceService.getById(id);
//            dance.setFdIsValid(1);
//            danceService.updateById(dance);
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-恢复舞蹈异常。", e);
//            mm.addAttribute("errMsg", "恢复舞蹈异常");
//            return "error/error";
//        }
//    }
//}
