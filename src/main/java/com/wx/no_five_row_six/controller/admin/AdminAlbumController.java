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
//@RequestMapping("/admin/album")
//public class AdminAlbumController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAlbumController.class);
//
//    @Autowired
//    private FrsAlbumServiceImpl frsAlbumService;
//
//    /**
//     * 专辑列表
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
//        QueryWrapper<FrsAlbum> queryWrapper = null;
//        IPage<FrsAlbum> page = new Page<>(current, size);
//        try {
//            // 查询条件
//            queryWrapper = new QueryWrapper<FrsAlbum>().like(StringUtils.isNotEmpty(keyword), "fa_name", keyword)
//                    .or().like(StringUtils.isNotEmpty(keyword), "fa_id", keyword)
//                    .ge(StringUtils.isNotEmpty(startDate), "fa_time", TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE))
//                    .le(StringUtils.isNotEmpty(endDate), "fa_time", TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
//                    .eq("fa_is_valid", state)
//                    .orderByDesc("fa_sort");
//            page = frsAlbumService.page(page, queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-专辑列表异常", e);
//            mm.addAttribute("errMsg", "专辑列表异常");
//            return "error/error";
//        }
//        mm.addAttribute("page", page);
//        mm.addAttribute("keyword", keyword);
//        mm.addAttribute("startDate", startDate);
//        mm.addAttribute("endDate", endDate);
//        mm.addAttribute("state", state);
//        return "admin/album/list";
//    }
//
//    /**
//     * 添加专辑界面
//     *
//     * @return
//     */
//    @RequestMapping("addUI")
//    public String newsAddUI() {
//        return "admin/album/add";
//    }
//
//    /**
//     * 编辑专辑界面
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("editUI")
//    public String editUI(ModelMap mm, Long id) {
//        try {
//            FrsAlbum album = frsAlbumService.getById(id);
//            if (StringUtils.isNotEmpty(album.getFaContent())) {
//                String content = album.getFaContent().replace("\"", "\'");
//                album.setFaContent(content);
//            }
//            mm.addAttribute("album", album);
//            return "admin/album/edit";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-获取专辑异常。", e);
//            mm.addAttribute("errMsg", "获取专辑异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 保存专辑
//     *
//     * @param album
//     * @return
//     */
//    @RequestMapping("saveOrUpdate")
//    public String saveOrUpdate(ModelMap mm, FrsAlbum album) {
//        try {
//            if (album.getFaId() == null) {
//                album.setFaTime(TimeUtil.stringToLong(album.getFaTimeStr(), "yyyy-MM-dd"));
//                album.setFaCreateTime(TimeUtil.dateToLong());
//                album.setFaIsValid(1);
//                frsAlbumService.save(album);
//            } else {
//                album.setFaTime(TimeUtil.stringToLong(album.getFaTimeStr(), "yyyy-MM-dd"));
//                album.setFaUpdateTime(TimeUtil.dateTolong());
//                frsAlbumService.updateById(album);
//            }
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-保存或修改专辑异常。", e);
//            mm.addAttribute("errMsg", "保存或修改专辑异常");
//            return "error/error";
//        }
//    }
//
//    /**
//     * 删除专辑
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("del")
//    public String del(ModelMap mm, Long id) {
//        try {
//            FrsAlbum album = frsAlbumService.getById(id);
//            album.setFaIsValid(0);
//            frsAlbumService.updateById(album);
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-删除专辑异常。", e);
//            mm.addAttribute("errMsg", "删除专辑异常");
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
//            FrsAlbum album = frsAlbumService.getById(id);
//            album.setFaIsValid(1);
//            frsAlbumService.updateById(album);
//            return "redirect:list";
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("后台管理-恢复专辑异常。", e);
//            mm.addAttribute("errMsg", "恢复专辑异常");
//            return "error/error";
//        }
//    }
//}
