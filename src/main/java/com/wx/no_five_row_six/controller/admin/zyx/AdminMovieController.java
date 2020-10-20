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
 * @version 2020/10/16 17:27
 * @desc 后台管理-影视作品-电影
 */
@Controller
@RequestMapping("/admin/zyx/movie")
public class AdminMovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminMovieController.class);
    @Autowired
    private IFrsZyxNewsService movieService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "admin/zyx/movie/list";
    }

    /**
     * 电影列表
     *
     * @param mm
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(ModelMap mm, String keyword, Integer current, Integer size) {
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
                    .eq(FrsZyxNews::getZnNcId, ZyxNewsConst.FILM)
                    .eq(FrsZyxNews::getZnTagIds, ZyxNewsConst.FILM_MOVIE_ID)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = movieService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-电影列表异常", e);
            mm.addAttribute("errMsg", "电影列表异常");
            return JacksonMapper.newErrorInstance("后台管理-电影列表异常");
        }
    }

    /**
     * 编辑电影界面
     *
     * @param id 电影id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            if (null == id) {
                mm.addAttribute("title", "电影添加");
            } else {
                mm.addAttribute("title", "电影编辑");
                FrsZyxNews film = movieService.getById(id);
                mm.addAttribute("film", film);
            }
            return "admin/zyx/movie/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取电影异常。", e);
            mm.addAttribute("errMsg", "获取电影异常");
            return "error/error";
        }
    }

    /**
     * 保存电影
     *
     * @param movie
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews movie) {
        try {
            if (movie.getZnId() == null) {
                movie.setZnNcId(ZyxNewsConst.FILM);
                movie.setZnTagIds(ZyxNewsConst.FILM_MOVIE_ID);
                movie.setZnCreateTime(TimeUtil.dateToLong());
                movie.setZnIsValid(ZyxNewsConst.VALID);
                movieService.save(movie);
            } else {
                movie.setZnUpdateTime(TimeUtil.dateTolong());
                movie.setZnUpdateUserId(AdminUserUtil.getUserId());
                movie.setZnUpdateUserName(AdminUserUtil.getShowName());
                movieService.updateById(movie);
            }
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改电影异常。", e);
            mm.addAttribute("errMsg", "保存或修改电影异常");
            return "error/error";
        }
    }

    /**
     * 删除电影
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("del")
    public JsonNode del(Long id) {
        try {
            FrsZyxNews movie = movieService.getById(id);
            movie.setZnIsValid(ZyxNewsConst.NOT_VALID);
            movieService.updateById(movie);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除电影异常。", e);
            return JacksonMapper.newErrorInstance("删除电影异常");
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
            FrsZyxNews movie = movieService.getById(id);
            movie.setZnIsValid(ZyxNewsConst.VALID);
            movieService.updateById(movie);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复电影异常。", e);
            return JacksonMapper.newErrorInstance("恢复电影异常");
        }
    }


}
