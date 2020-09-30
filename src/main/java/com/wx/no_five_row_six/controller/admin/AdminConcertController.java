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
//@RequestMapping("/admin/concert")
//public class AdminConcertController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConcertController.class);
//
//    @Autowired
//    private FrsConcertServiceImpl concertService;
//
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
//        QueryWrapper<FrsConcert> queryWrapper = null;
//        IPage<FrsConcert> page = new Page<>(current, size);
//        try {
//            queryWrapper = new QueryWrapper<FrsConcert>().like(StringUtils.isNotEmpty(keyword), "fc_address", keyword)
//                    .or().like(StringUtils.isNotEmpty(keyword), "fc_title", keyword)
//                    .ge(StringUtils.isNotEmpty(startDate), "fc_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
//                    .le(StringUtils.isNotEmpty(endDate), "fc_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
//                    .eq("fc_is_valid", state)
//                    .orderByDesc("fc_sort", "fc_time");
//            page = concertService.page(page, queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-演唱会列表异常", e);
//            mm.addAttribute("errMsg", "演唱会列表异常");
//            return "error/error";
//        }
//        mm.addAttribute("page", page);
//        mm.addAttribute("keyword", keyword);
//        mm.addAttribute("startDate", startDate);
//        mm.addAttribute("endDate", endDate);
//        mm.addAttribute("state", state);
//        return "admin/concert/list";
//    }
//
//    /**
//     * 添加演唱会界面
//     *
//     * @return
//     */
//    @RequestMapping("addUI")
//    public String newsAddUI() {
//        return "admin/concert/add";
//    }
//
//    /**
//     * 编辑演唱会界面
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("editUI")
//    public String editUI(ModelMap mm, Long id) {
//        try {
//            FrsConcert concert = concertService.getById(id);
//            if (StringUtils.isNotEmpty(concert.getFcContent())) {
//                String content = concert.getFcContent().replace("\"", "\'");
//                concert.setFcContent(content);
//            }
//            mm.addAttribute("concert", concert);
//            return "admin/concert/edit";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-获取演唱会异常。", e);
//            mm.addAttribute("errMsg", "获取演唱会异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 保存演唱会
//     *
//     * @param concert
//     * @return
//     */
//    @RequestMapping("saveOrUpdate")
//    public String saveOrUpdate(ModelMap mm, FrsConcert concert) {
//        try {
//            if (concert.getFcId() == null) {
//                concert.setFcTime(TimeUtil.stringToLong(concert.getFcTimeStr(), "yyyy-MM-dd HH:mm"));
//                concert.setFcCreateTime(TimeUtil.dateToLong());
//                concert.setFcIsValid(1);
//                concertService.save(concert);
//            } else {
//                concert.setFcTime(TimeUtil.stringToLong(concert.getFcTimeStr(), "yyyy-MM-dd HH:mm"));
//                concert.setFcUpdateTime(TimeUtil.dateTolong());
//                concertService.updateById(concert);
//            }
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-保存或修改演唱会异常。", e);
//            mm.addAttribute("errMsg", "保存或修改演唱会异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 删除演唱会
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("del")
//    public String del(ModelMap mm, Long id) {
//        try {
//            FrsConcert concert = concertService.getById(id);
//            concert.setFcIsValid(0);
//            concertService.updateById(concert);
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-删除演唱会异常。", e);
//            mm.addAttribute("errMsg", "删除演唱会异常");
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
//            FrsConcert concert = concertService.getById(id);
//            concert.setFcIsValid(1);
//            concertService.updateById(concert);
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-恢复演唱会异常。", e);
//            mm.addAttribute("errMsg", "恢复演唱会异常");
//            return "error/error";
//        }
//    }
//}
