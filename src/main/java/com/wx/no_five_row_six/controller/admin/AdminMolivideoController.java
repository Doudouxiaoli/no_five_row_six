package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsMolivideo;
import com.wx.no_five_row_six.entity.FrsMovie;
import com.wx.no_five_row_six.entity.FrsTv;
import com.wx.no_five_row_six.entity.FrsVariety;
import com.wx.no_five_row_six.service.impl.FrsMolivideoServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsMovieServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsTvServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsVarietyServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/molivideo")
public class AdminMolivideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminMolivideoController.class);

    @Autowired
    private FrsMolivideoServiceImpl molivideoService;
    @Autowired
    private FrsMovieServiceImpl movieService;
    @Autowired
    private FrsTvServiceImpl tvService;
    @Autowired
    private FrsVarietyServiceImpl varietyService;

    /**
     * 影视作品列表
     *
     * @param mm
     * @param keyword
     * @param state
     * @param current
     * @param size
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    @RequestMapping(value = {"/list"})
    public String list(ModelMap mm, String keyword, Integer state, Integer current, Integer size, String startDate, String endDate, Integer type) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        if (state == null) {
            state = 1;
        }
        QueryWrapper<FrsMolivideo> queryWrapper = null;
        IPage<FrsMolivideo> page = new Page<>(current, size);
        try {
            // 查询条件
            queryWrapper = new QueryWrapper<FrsMolivideo>().like(StringUtils.isNotEmpty(keyword), "fmv_name", keyword)
                    .or().like(StringUtils.isNotEmpty(keyword), "fmv_actors", keyword)
                    .eq("fmv_is_valid", state)
                    .eq("fmv_type", type)
                    .orderByDesc("fmv_sort");
            page = molivideoService.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-影视作品列表异常", e);
            mm.addAttribute("errMsg", "影视作品列表异常");
            return "error/error";
        }
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        mm.addAttribute("state", state);
        mm.addAttribute("type", type);
        if (Const.MOLIVIDEO_MOVIE_ID == type) {
            return "admin/molivideo/movie/list";
        } else if (Const.MOLIVIDEO_TV_ID == type) {
            return "admin/molivideo/tv/list";
        } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
            return "admin/molivideo/variety/list";
        } else {
            return "error/error";
        }
    }

    /**
     * 添加影视作品界面
     *
     * @return
     */
    @RequestMapping("add")
    public String add(ModelMap mm, Integer type) {
        mm.addAttribute("typeName", Const.getType(type));
        if (Const.MOLIVIDEO_MOVIE_ID == type) {
            mm.addAttribute("typeName", Const.getType(type));
            return "admin/molivideo/movie/add";
        } else if (Const.MOLIVIDEO_TV_ID == type) {
            mm.addAttribute("typeName", Const.getType(type));
            return "admin/molivideo/tv/add";
        } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
            mm.addAttribute("typeName", Const.getType(type));
            return "admin/molivideo/variety/add";
        } else {
            mm.addAttribute("errMsg", "后台管理-影视作品>添加:类型传值错误");
            return "error/error";
        }
    }

    /**
     * 编辑影视作品界面
     *
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id) {
        try {
            FrsMolivideo molivideo = molivideoService.getById(id);
            Integer type = molivideo.getFmvType();
            String typeName = molivideo.getFmvTypeName();
            mm.addAttribute("molivideo", molivideo);
            mm.addAttribute("type", type);
            mm.addAttribute("typeName", typeName);
            if (Const.MOLIVIDEO_MOVIE_ID == type) {
                return "admin/molivideo/movie/edit";
            } else if (Const.MOLIVIDEO_TV_ID == type) {
                return "admin/molivideo/tv/edit";
            } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
                return "admin/molivideo/variety/edit";
            } else {
                mm.addAttribute("errMsg", "后台管理-影视作品>编辑:类型传值错误");
                return "error/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取影视作品异常。", e);
            mm.addAttribute("errMsg", "获取影视作品异常");
            return "error/error";
        }
    }

    /**
     * 保存影视作品
     *
     * @param molivideo
     * @return
     */
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap mm, FrsMolivideo molivideo) {
        try {
            Long now = TimeUtil.dateToLong();
            if (molivideo.getFmvId() == null) {
                molivideo.setFmvCreateTime(now);
                molivideo.setFmvIsValid(1);
                molivideoService.save(molivideo);
                Integer type = molivideo.getFmvType();
                if (Const.MOLIVIDEO_MOVIE_ID == type) {
                    FrsMovie movie = new FrsMovie();
                    movie.setFmFmvId(molivideo.getFmvId());
                    movie.setFmName(molivideo.getFmvName());
                    movie.setFmImg(molivideo.getFmvImg());
                    movie.setFmCreateTime(now);
                    movie.setFmIsValid(1);
                    movie.setFmHitsNum(0);
                    movieService.save(movie);
                } else if (Const.MOLIVIDEO_TV_ID == type) {
                    FrsTv tv = new FrsTv();
                    tv.setFtFmvId(molivideo.getFmvId());
                    tv.setFtName(molivideo.getFmvName());
                    tv.setFtCreateTime(now);
                    tv.setFtIsValid(1);
                    tv.setFtHitsNum(0);
                    tvService.save(tv);
                } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
                    FrsVariety variety = new FrsVariety();
                    variety.setFvFmvId(molivideo.getFmvId());
                    variety.setFvName(molivideo.getFmvName());
                    variety.setFvImg(molivideo.getFmvImg());
                    variety.setFvCreateTime(now);
                    variety.setFvIsValid(1);
                    variety.setFvHitsNum(0);
                    varietyService.save(variety);
                } else {
                    return "error/error";
                }
            } else {
                Long fmvId = molivideo.getFmvId();
                molivideo.setFmvUpdateTime(now);
                molivideoService.updateById(molivideo);
                Integer type = molivideo.getFmvType();
                if (Const.MOLIVIDEO_MOVIE_ID == type) {
                    QueryWrapper<FrsMovie> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(FrsMovie::getFmFmvId, fmvId);
                    FrsMovie movie = movieService.getOne(queryWrapper, false);
                    movie.setFmName(molivideo.getFmvName());
                    movie.setFmUpdateTime(now);
                    movieService.updateById(movie);
                } else if (Const.MOLIVIDEO_TV_ID == type) {
                    QueryWrapper<FrsTv> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(FrsTv::getFtFmvId, fmvId);
                    FrsTv tv = tvService.getOne(queryWrapper, false);
                    tv.setFtName(molivideo.getFmvName());
                    tv.setFtUpdateTime(now);
                    tvService.updateById(tv);
                } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
                    QueryWrapper<FrsVariety> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(FrsVariety::getFvFmvId, fmvId);
                    FrsVariety variety = varietyService.getOne(queryWrapper, false);
                    variety.setFvName(molivideo.getFmvName());
                    variety.setFvUpdateTime(now);
                    varietyService.updateById(variety);
                } else {
                    return "error/error";
                }
            }
            return "redirect:list?type=" + molivideo.getFmvType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改影视作品异常。", e);
            mm.addAttribute("errMsg", "保存或修改影视作品异常");
            return "error/error";
        }
    }

    /**
     * 删除影视作品
     * 假删
     *
     * @param id
     * @return
     */
    @RequestMapping("del")
    public String del(ModelMap mm, Long id) {
        try {
            FrsMolivideo molivideo = molivideoService.getById(id);
            molivideo.setFmvIsValid(0);
            molivideoService.updateById(molivideo);
            return "redirect:list?type=" + molivideo.getFmvType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-删除影视作品异常。", e);
            mm.addAttribute("errMsg", "删除影视作品异常");
            return "error/error";
        }
    }

    /**
     * 彻底销毁影视作品
     * (不可逆)
     *
     * @param id
     * @return
     */
    @RequestMapping("destroy")
    public String destroy(ModelMap mm, Long id) {
        try {
            FrsMolivideo molivideo = molivideoService.getById(id);
            molivideoService.removeById(molivideo);
            Integer type = molivideo.getFmvType();
            if (Const.MOLIVIDEO_MOVIE_ID == type) {
                QueryWrapper<FrsMovie> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(FrsMovie::getFmFmvId, id);
                movieService.remove(queryWrapper);
            } else if (Const.MOLIVIDEO_TV_ID == type) {
                QueryWrapper<FrsTv> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(FrsTv::getFtFmvId, id);
                tvService.remove(queryWrapper);
            } else if (Const.MOLIVIDEO_VARIETY_ID == type) {
                QueryWrapper<FrsVariety> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(FrsVariety::getFvFmvId, id);
                varietyService.remove(queryWrapper);
            } else {
                return "error/error";
            }
            return "redirect:list?type=" + molivideo.getFmvType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-销毁影视作品异常。", e);
            mm.addAttribute("errMsg", "销毁影视作品异常");
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
            FrsMolivideo molivideo = molivideoService.getById(id);
            molivideo.setFmvIsValid(1);
            molivideoService.updateById(molivideo);
            return "redirect:list?type=" + molivideo.getFmvType();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-恢复影视作品异常。", e);
            mm.addAttribute("errMsg", "恢复影视作品异常");
            return "error/error";
        }
    }

}
