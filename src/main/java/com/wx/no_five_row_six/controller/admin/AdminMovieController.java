package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsMovie;
import com.wx.no_five_row_six.service.impl.FrsMolivideoServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsMovieServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/molivideo/movie")
public class AdminMovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminMovieController.class);

    @Autowired
    private FrsMovieServiceImpl movieService;

    @Autowired
    private FrsMolivideoServiceImpl molivideoService;

    /**
     * 电影列表
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
        QueryWrapper<FrsMovie> queryWrapper = null;
        IPage<FrsMovie> page = new Page<>(current, size);
        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsMovie::getFmName, keyword)
                    .between(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate), FrsMovie::getFmTime, TimeUtil.stringToLong(startDate, TimeUtil.FORMAT_DATE), TimeUtil.stringToLong(endDate, TimeUtil.FORMAT_DATE))
                    .eq(FrsMovie::getFmIsValid, state)
                    .eq(FrsMovie::getFmFmvId, workId)
                    .orderByDesc(FrsMovie::getFmTime, FrsMovie::getFmSort);
            page = movieService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-电影列表异常", e);
            mm.addAttribute("errMsg", "电影列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("workId", workId);
        mm.addAttribute("type", molivideoService.getById(workId).getFmvType());
        return "admin/molivideo/movie/detail/list";
    }

    /**
     * 添加电影界面
     *
     * @return
     */
    @RequestMapping("add")
    public String add(ModelMap mm, Long workId) {
        try {
            mm.addAttribute("workId", workId);
            String workName = molivideoService.getById(workId).getFmvName();
            mm.addAttribute("workName", workName);
            return "admin/molivideo/movie/detail/add";
        } catch (Exception e) {
            mm.addAttribute("errMsg", "后台管理-电影>添加:类型传值错误");
            e.printStackTrace();
        }
        mm.addAttribute("errMsg", "获取电影异常");
        return "error/error";
    }

    /**
     * 编辑电影界面
     *
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            FrsMovie movie = movieService.getById(id);
            if (movie != null) {
                Long workId = movie.getFmFmvId();
                String workName = movie.getFmName();
                mm.addAttribute("workId", workId);
                mm.addAttribute("workName", workName);
                mm.addAttribute("movie", movie);
                return "admin/molivideo/movie/detail/edit";
            } else {
                mm.addAttribute("errMsg", "后台管理-电影>编辑:类型传值错误");
                return "error/error";
            }
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
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsMovie movie) {
        try {
            if (movie.getFmId() == null) {
                movie.setFmTime(TimeUtil.stringToLong(movie.getFmTimeStr(), "yyyy-MM-dd"));
                movie.setFmCreateTime(TimeUtil.dateToLong());
                movie.setFmIsValid(1);
                movieService.save(movie);
            } else {
                movie.setFmTime(TimeUtil.stringToLong(movie.getFmTimeStr(), "yyyy-MM-dd"));
                movie.setFmUpdateTime(TimeUtil.dateTolong());
                movieService.updateById(movie);
            }
            return "redirect:list?workId=" + movie.getFmFmvId();
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
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsMovie movie = movieService.getById(id);
            movie.setFmIsValid(0);
            movieService.updateById(movie);
            return "redirect:list?workId=" + movie.getFmFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除电影异常。", e);
            mm.addAttribute("errMsg", "删除电影异常");
            return "error/error";
        }
    }

    /**
     * 销毁电影(不可逆)
     *
     * @param id
     * @return
     */
    @RequestMapping("destroy")
    public String destroy(ModelMap mm, Long id) {
        try {
            FrsMovie movie = movieService.getById(id);
            movieService.removeById(movie);
            return "redirect:list?workId=" + movie.getFmFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-销毁电影异常。", e);
            mm.addAttribute("errMsg", "销毁电影异常");
            return "error/error";
        }
    }

    /**
     * 恢复删除
     *
     * @param id
     * @return
     */
    @RequestMapping("reBack")
    public String reBack(ModelMap mm, Long id) {
        try {
            FrsMovie movie = movieService.getById(id);
            movie.setFmIsValid(1);
            movieService.updateById(movie);
            return "redirect:list?workId=" + movie.getFmFmvId();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复电影异常。", e);
            mm.addAttribute("errMsg", "恢复电影异常");
            return "error/error";
        }
    }

}
