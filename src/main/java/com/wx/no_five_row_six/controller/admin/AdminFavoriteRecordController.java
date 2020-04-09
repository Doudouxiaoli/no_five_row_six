package com.wx.no_five_row_six.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.entity.FrsBookmarkRecord;
import com.wx.no_five_row_six.service.impl.FrsBookmarkRecordServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("admin/favorite")
public class AdminFavoriteRecordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminFavoriteRecordController.class);
    @Autowired
    private FrsBookmarkRecordServiceImpl bookmarkRecordService;

    /**
     * list
     *
     * @param limit
     * @param keyword
     * @return
     */
    @RequestMapping("/list")
    public String list(ModelMap mm, Integer current, Integer limit, String keyword, String startDate, String endDate) {
        if (current == null) {
            current = 1;
        }
        if (limit == null) {
            limit = Const.ADMIN_ROWSPERPAGE_MORE;
        }
        IPage<FrsBookmarkRecord> page = new Page<>(current, limit);
        QueryWrapper<FrsBookmarkRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate), FrsBookmarkRecord::getFbrTime, TimeUtil.stringToLong(startDate), TimeUtil.stringToLong(endDate))
                .like(StringUtils.isNotEmpty(keyword), FrsBookmarkRecord::getFbrTime, keyword);
        page = bookmarkRecordService.page(page, queryWrapper);
        mm.addAttribute("page", page);
        mm.addAttribute("keyword", keyword);
        mm.addAttribute("startDate", startDate);
        mm.addAttribute("endDate", endDate);
        return "admin/other/listFavorite";
    }
}
