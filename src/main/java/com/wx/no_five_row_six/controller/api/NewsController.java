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
    @RequestMapping("index")
    public JsonNode index(Integer current, Integer size, String keyword, Long moduleId, Integer tagId, Integer limit) {
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
                    .eq(null != tagId, FrsZyxNews::getZnTagIds, tagId)
                    .orderByDesc(FrsZyxNews::getZnDate)
                    .last(null != limit, "limit " + limit);
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
     * @param id
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public JsonNode list(Long id) {
        Map<Integer, List> map = new HashMap<>();
        try {
            List<FrsZyxNews> fatherList = fatherService.list(new QueryWrapper<FrsZyxNews>().eq("zn_id", id));
            FrsZyxNews father = fatherList.get(0);
            QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FrsZyxNews::getZnFromId, id);
            List<FrsZyxNews> childList = childService.list(queryWrapper);
            if (ZyxNewsConst.ENDORSEMENT == father.getZnNcId() || ZyxNewsConst.FILM == father.getZnNcId()) {
                Long moduleId = father.getZnNcId();
                Integer tagId = father.getZnTagIds();
                childList = childService.getChildList(moduleId, tagId);
            }
            map.put(0, fatherList);
            map.put(1, childList);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("子集获取出错");
        }
        return JacksonMapper.newDataInstance(map);
    }

    /**
     * 详情展示
     *
     * @param pk     资讯id
     * @param fromId 来源id
     * @return
     */
    @ResponseBody
    @RequestMapping("detail")
    public JsonNode detail(Long pk, Long fromId, HttpServletRequest request) {
        try {
            Map<Integer, Object> map = new HashMap<>();
            Integer tagId = null;
            FrsZyxNews running = null;
            FrsZyxNews father = null;
            Long moduleId = null;
            List<FrsZyxNews> childList = null;
            if (null != pk) {
                running = newsService.getById(pk);
                fromId = running.getZnFromId();
                father = newsService.getById(fromId);
                moduleId = father.getZnNcId();
                if (null != father.getZnTagIds()) {
                    tagId = father.getZnTagIds();
                }
                childList = childService.getChildList(moduleId, tagId);
            } else if (null != fromId) {
                //默认选择子节点的第一个展示
                father = newsService.getById(fromId);
                moduleId = father.getZnNcId();
                if (null != father.getZnTagIds()) {
                    tagId = father.getZnTagIds();
                }
                childList = childService.getChildList(moduleId, tagId);
                if (childList.size() > 0) {
                    running = childList.get(0);
                } else {
                    running = father;
                }
                pk = running.getZnId();
            }
            visitLogService.saveVisit(pk, request,father.getZnTitle(), running.getZnTitle());
            childList.remove(running);
            map.put(0, father);
            map.put(1, running);
            map.put(2, childList);
            return JacksonMapper.newDataInstance(map);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("详情列表展示出错");
        }
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
