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
 * @desc 后台管理-演唱会节目
 */
@Controller
@RequestMapping("/admin/zyx/program")
public class AdminConcertProgramController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConcertProgramController.class);

    @Autowired
    private IFrsZyxNewsService programService;

    @RequestMapping("list")
    public String list(Long fromId, ModelMap mm) {
        mm.addAttribute("fromId", fromId);
        return "admin/zyx/concert/program/list";
    }

    /**
     * 演唱会表演曲目表演曲目列表
     *
     * @param keyword
     * @param current
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping("listAjax")
    public JsonNode listAjax(String keyword, Integer current, Integer size, Long fromId) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        try {
            // 查询条件 (曲目名称)
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword).eq(FrsZyxNews::getZnFromId,
                    fromId);
            page = programService.page(page, queryWrapper);
            return JacksonMapper.newCountInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-演唱会表演曲目列表异常", e);
            return JacksonMapper.newErrorInstance("后台管理-演唱会表演曲目列表异常");
        }
    }

    /**
     * 编辑表演曲目界面
     *
     * @param id     节目id
     * @param fromId 演唱会id
     * @return
     */
    @RequestMapping("edit")
    public String edit(ModelMap mm, Long id, Long fromId) {
        try {
            if (null == id) {
                mm.addAttribute("title", "演唱会表演曲目添加");
            } else {
                mm.addAttribute("title", "演唱会表演曲目编辑");
                FrsZyxNews program = programService.getById(id);
                fromId = program.getZnFromId();
                mm.addAttribute("program", program);
            }
            mm.addAttribute("fromId", fromId);
            return "admin/zyx/concert/program/edit";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-获取演唱会表演曲目异常。", e);
            mm.addAttribute("errMsg", "获取演唱会表演曲目异常");
            return "error/error";
        }
    }

    /**
     * 保存演唱会表演曲目
     *
     * @param program
     * @return
     */
    @RequestMapping("save")
    public String save(ModelMap mm, FrsZyxNews program) {
        try {
            if (null == program.getZnFromId()) {
                return "error/error";
            } else {
                if (null == program.getZnId()) {
                    FrsZyxNews concert = programService.getById(program.getZnFromId());
                    if (null != concert) {
                        //获取演唱会主题+场次
                        String from = concert.getZnTitle() + concert.getZnAddress();
                        program.setZnFrom(from);
                    }
                    program.setZnCreateUserId(AdminUserUtil.getUserId());
                    program.setZnCreateUserName(AdminUserUtil.getShowName());
                    program.setZnCreateTime(TimeUtil.dateToLong());
                    program.setZnIsValid(ZyxNewsConst.VALID);
                    programService.save(program);
                } else {
                    program.setZnUpdateTime(TimeUtil.dateTolong());
                    program.setZnUpdateUserId(AdminUserUtil.getUserId());
                    program.setZnUpdateUserName(AdminUserUtil.getShowName());
                    programService.updateById(program);
                }
                return "redirect:list?fromId="+program.getZnFromId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("后台管理-保存或修改演唱会表演曲目异常。", e);
            mm.addAttribute("errMsg", "保存或修改演唱会表演曲目异常");
            return "error/error";
        }
    }
}
