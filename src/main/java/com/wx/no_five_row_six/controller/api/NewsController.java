package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.ZyxNewsConst;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsZyxLikeRecord;
import com.wx.no_five_row_six.entity.FrsZyxNews;
import com.wx.no_five_row_six.service.IFrsZyxLikeRecordService;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import com.wx.no_five_row_six.service.IFrsZyxVisitLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dxl
 * @version 2020/10/21 10:43
 * @desc zyx资讯api
 */
@RequestMapping("/api/news")
@Controller
public class NewsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private IFrsZyxNewsService fatherService;
    @Autowired
    private IFrsZyxNewsService childService;
    @Autowired
    private IFrsZyxNewsService newsService;
    @Autowired
    private IFrsZyxVisitLogService visitLogService;
    @Autowired
    private IFrsZyxLikeRecordService likeRecordService;

    /**
     * 父集列表
     *
     * @param current
     * @param size
     * @param keyword
     * @param moduleId 0专辑 1舞蹈 2演唱会 3代言 4影视
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public JsonNode fatherList(Integer current, Integer size, String keyword, Long moduleId) {
        if (current == null) {
            current = 1;
        }
        if (size == null) {
            size = Const.WEB_PC_ROWSPERPAGE;
        }
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<FrsZyxNews>();
        IPage<FrsZyxNews> page = new Page<>(current, size);
        try {
            queryWrapper.lambda().like(StringUtils.isNotEmpty(keyword), FrsZyxNews::getZnTitle, keyword)
                    .eq(FrsZyxNews::getZnNcId, moduleId)
                    .eq(FrsZyxNews::getZnIsValid, ZyxNewsConst.VALID)
                    .orderByDesc(FrsZyxNews::getZnDate);
            page = fatherService.page(page, queryWrapper);
            return JacksonMapper.newDataInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            String err = "父集列表查询异常";
            LOGGER.error(err, e);
            return JacksonMapper.newErrorInstance(err);
        }
    }

    /**
     * 子集列表
     *
     * @param moduleId 父id： 0专辑 1舞蹈 2演唱会 3代言 4影视
     * @param tagId    代言 0 食物 1美妆 2服装 3轻奢 4游戏
     *                 影视类型 0电视剧 1电影 2综艺
     * @param runId    主要展示的一个子集，主要是用于单页面切换播放但不跳转页面
     * @return
     */
    @RequestMapping("childList")
    @ResponseBody
    public JsonNode childList(Long moduleId, Integer tagId, Long runId) {
        Map<Integer, Object> map = new HashMap<>();
        try {
            FrsZyxNews father = fatherService.getById(moduleId);
            List<FrsZyxNews> childList = childService.getChildList(moduleId, tagId);
            FrsZyxNews runningNews = null;
            if (null != runId) {
                runningNews = childService.getById(runId);
                childList.remove(runningNews);
            }
            map.put(0, father);
            map.put(1, childList);
            map.put(3, runningNews);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情跳转出错");
        }
        return JacksonMapper.newDataInstance(map);
    }

    /**
     * 详情展示
     *
     * @param pk 资讯id
     * @return
     */
    @ResponseBody
    @RequestMapping("detail")
    public JsonNode detail(Long pk) {
        try {
            FrsZyxNews news = newsService.getById(pk);
            return JacksonMapper.newDataInstance(news);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情列表展示出错");
        }
    }

    /**
     * 保存一条访问记录
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("saveVisit")
    public JsonNode saveVisit(Long id, HttpServletRequest request) {
        FrsZyxNews news = newsService.getById(id);
        visitLogService.saveVisit(id, request, news.getZnFromId().toString(), news.getZnTitle());
        return JacksonMapper.newSuccessInstance();
    }
    /**
     * 修改点赞状态
     *
     * @param type 访问的模块(电影/电视剧/综艺/代言/演唱会/歌曲/舞蹈)
     * @param id   资讯主键
     * @return
     */
    @ResponseBody
    @RequestMapping("changeLike")
    public JsonNode changeLike(@Param("type") Integer type, Integer id) {
        String contentName = null;
        try {
            Long userId = UserUtil.getUserId();
            QueryWrapper<FrsZyxLikeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FrsZyxLikeRecord::getZlrCommentId, id).eq(FrsZyxLikeRecord::getZlrUserId, userId).eq(FrsZyxLikeRecord::getZlrType, type);
            FrsZyxLikeRecord record = likeRecordService.getOne(queryWrapper, false);
            //更改各个表的点赞数
            if (null != type) {
                FrsZyxNews news = newsService.getById(id);
                if (null != record) {
                    news.setZnLikeNum(news.getZnLikeNum() == 0 ? news.getZnLikeNum() - 1 : 0);
                } else {
                    news.setZnLikeNum(news.getZnLikeNum() + 1);
                    contentName = news.getZnTitle();
                }
                newsService.save(news);
            }
            //存点赞记录表
            if (null != record) {
                record.setZlrTime(TimeUtil.dateToLong());
                likeRecordService.updateById(record);
            } else {
                FrsZyxLikeRecord addRecord = new FrsZyxLikeRecord();
                addRecord.setZlrUserId(userId);
                addRecord.setZlrType(type);
                addRecord.setZlrCommentId(id.longValue());
                likeRecordService.save(addRecord);
            }
            LOGGER.info(ZyxNewsConst.getModelName(id.longValue()) + contentName + "====点赞状态改变");
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
    }

}
