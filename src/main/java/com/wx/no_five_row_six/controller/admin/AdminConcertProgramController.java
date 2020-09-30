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
//@RequestMapping("/admin/concertProgram")
//public class AdminConcertProgramController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConcertProgramController.class);
//
//    @Autowired
//    private FrsConcertProgramServiceImpl programService;
//
//    @Autowired
//    private FrsConcertServiceImpl concertService;
//
//    /**
//     * 演唱会节目列表
//     *
//     * @param mm
//     * @param keyword
//     * @param state
//     * @param current
//     * @param size
//     * @param startDate
//     * @param endDate
//     * @param concertId
//     * @return
//     */
//    @RequestMapping(value = {"/list"})
//    public String list(ModelMap mm, String keyword, Integer state, Integer current, Integer size, String startDate, String endDate, Long concertId) {
//        if (current == null) {
//            current = 1;
//        }
//        if (size == null) {
//            size = Const.ADMIN_ROWSPERPAGE_MORE;
//        }
//        if (state == null) {
//            state = 1;
//        }
//        QueryWrapper<FrsConcertProgram> queryWrapper = null;
//        IPage<FrsConcertProgram> page = new Page<>(current, size);
//        try {
//            queryWrapper = new QueryWrapper<FrsConcertProgram>().like(StringUtils.isNotEmpty(keyword), "fcp_name", keyword)
//                    .eq("fcp_is_valid", state)
//                    .eq("fcp_fc_id", concertId)
//                    .orderByDesc("fcp_sort");
//            page = programService.page(page, queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-演唱会节目列表异常", e);
//            mm.addAttribute("errMsg", "演唱会节目列表异常");
//            return "error/error";
//        }
//        mm.addAttribute("page", page);
//        mm.addAttribute("keyword", keyword);
//        mm.addAttribute("startDate", startDate);
//        mm.addAttribute("endDate", endDate);
//        mm.addAttribute("state", state);
//        mm.addAttribute("concertId", concertId);
//        return "admin/concert/program/list";
//    }
//
//    /**
//     * 添加演唱会节目界面
//     *
//     * @return
//     */
//    @RequestMapping("addUI")
//    public String newsAddUI(ModelMap mm, Long concertId) {
//        try {
//            mm.addAttribute("concertId", concertId);
//            return "admin/concert/program/add";
//        } catch (Exception e) {
//            mm.addAttribute("errMsg", "后台管理-演唱会节目>添加:类型传值错误");
//            e.printStackTrace();
//        }
//        mm.addAttribute("errMsg", "获取演唱会节目异常");
//        return "error/error";
//    }
//
//    /**
//     * 编辑演唱会节目界面
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("editUI")
//    public String editUI(ModelMap mm, Long id) {
//        try {
//            FrsConcertProgram program = programService.getById(id);
//            if (program != null) {
//                Long concertId = program.getFcpFcId();
//                mm.addAttribute("concertId", concertId);
//                mm.addAttribute("program", program);
//                return "admin/concert/program/edit";
//            } else {
//                mm.addAttribute("errMsg", "后台管理-演唱会节目>编辑:类型传值错误");
//                return "error/error";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-获取演唱会节目异常。", e);
//            mm.addAttribute("errMsg", "获取演唱会节目异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 保存演唱会节目
//     *
//     * @param program
//     * @return
//     */
//    @RequestMapping("saveOrUpdate")
//    public String saveOrUpdate(ModelMap mm, FrsConcertProgram program) {
//        try {
//            if (program.getFcpId() == null) {
//                program.setFcpCreateTime(TimeUtil.dateToLong());
//                program.setFcpIsValid(1);
//                programService.save(program);
//            } else {
//                program.setFcpUpdateTime(TimeUtil.dateTolong());
//                programService.updateById(program);
//            }
//            return "redirect:list?concertId=" + program.getFcpFcId();
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-保存或修改演唱会节目异常。", e);
//            mm.addAttribute("errMsg", "保存或修改演唱会节目异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 删除演唱会节目
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("del")
//    public String del(ModelMap mm, Long id) {
//        try {
//            FrsConcertProgram program = programService.getById(id);
//            program.setFcpIsValid(0);
//            programService.updateById(program);
//            return "redirect:list?concertId=" + program.getFcpFcId();
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-删除演唱会节目异常。", e);
//            mm.addAttribute("errMsg", "删除演唱会节目异常");
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
//            FrsConcertProgram program = programService.getById(id);
//            program.setFcpIsValid(1);
//            programService.updateById(program);
//            return "redirect:list?concertId=" + program.getFcpFcId();
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-恢复演唱会节目异常。", e);
//            mm.addAttribute("errMsg", "恢复演唱会节目异常");
//            return "error/error";
//        }
//    }
//
//}
