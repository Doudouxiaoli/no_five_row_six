package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.*;
import com.wx.no_five_row_six.service.impl.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private FrsConcertServiceImpl concertService;
    @Autowired
    private FrsDanceServiceImpl danceService;
    @Autowired
    private FrsSongServiceImpl songService;
    @Autowired
    private FrsEndorsementServiceImpl endorsementService;
    @Autowired
    private FrsMolivideoServiceImpl molivideoService;
    @Autowired
    private FrsMovieServiceImpl movieService;
    @Autowired
    private FrsTvServiceImpl tvService;
    @Autowired
    private FrsVarietyServiceImpl varietyService;
    @Autowired
    private FrsLikeRecordServiceImpl likeRecordService;
    @Autowired
    private FrsBookmarkRecordServiceImpl bookmarkRecordService;

    /**
     * 修改点赞状态
     */
    @ResponseBody
    @RequestMapping("changeLike")
    public JsonNode changeLike(@Param("type") Integer type, Integer id) {
        String contentName = null;
        try {
            Long userId = UserUtil.getUserId();
            QueryWrapper<FrsLikeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FrsLikeRecord::getFlrContentId, id).eq(FrsLikeRecord::getFlrUserId, userId).eq(FrsLikeRecord::getFlrTypeId, type);
            FrsLikeRecord record = likeRecordService.getOne(queryWrapper, false);
//       更改各个表的点赞数
            if (Const.MODEL_TYPE_CONCERT == type) {
                FrsConcert concert = concertService.getById(id);
                if (null != record) {
                    concert.setFcLikedNum(concert.getFcLikedNum() == 0 ? concert.getFcLikedNum() - 1 : 0);
                } else {
                    concert.setFcLikedNum(concert.getFcLikedNum() + 1);
                    contentName = concert.getFcTitle();
                }
                concertService.save(concert);
            } else if (Const.MODEL_TYPE_DANCE == type) {
                FrsDance dance = danceService.getById(id);
                if (null != record) {
                    dance.setFdLikedNum(dance.getFdLikedNum() == 0 ? dance.getFdLikedNum() - 1 : 0);
                } else {
                    dance.setFdLikedNum(dance.getFdLikedNum() + 1);
                    contentName = dance.getFdTvName();
                }
                danceService.save(dance);
            } else if (Const.MODEL_TYPE_SONG == type) {
                FrsSong song = songService.getById(id);
                if (null != record) {
                    song.setFsLikeNum(song.getFsLikeNum() == 0 ? song.getFsLikeNum() - 1 : 0);
                } else {
                    song.setFsLikeNum(song.getFsLikeNum() + 1);
                    contentName = song.getFsName();
                }
                songService.save(song);
            } else if (Const.MODEL_TYPE_ENDORSEMENT == type) {
                FrsEndorsement endorsement = endorsementService.getById(id);
                if (null != record) {
                    endorsement.setFeLikedNum(endorsement.getFeLikedNum() == 0 ? endorsement.getFeLikedNum() - 1 : 0);
                } else {
                    endorsement.setFeLikedNum(endorsement.getFeLikedNum() + 1);
                    contentName = endorsement.getFeName();
                }
                endorsementService.save(endorsement);
            } else if (Const.MODEL_TYPE_MOVIE == type) {
                FrsMovie movie = movieService.getById(id);
                if (null != record) {
                    movie.setFmLikedNum(movie.getFmLikedNum() == 0 ? movie.getFmLikedNum() + 1 : 0);
                } else {
                    movie.setFmLikedNum(movie.getFmLikedNum() + 1);
                    contentName = movie.getFmName();
                }
                movieService.save(movie);
            } else if (Const.MODEL_TYPE_TV == type) {
                FrsTv tv = tvService.getById(id);
                if (null != record) {
                    tv.setFtLikedNum(tv.getFtLikedNum() == 0 ? tv.getFtLikedNum() + 1 : 0);
                } else {
                    tv.setFtLikedNum(tv.getFtLikedNum() + 1);
                    contentName = tv.getFtName();
                }
                tvService.save(tv);
            } else if (Const.MODEL_TYPE_VARIETY == type) {
                FrsVariety variety = varietyService.getById(id);
                if (null != record) {
                    variety.setFvLikedNum(variety.getFvLikedNum() == 0 ? variety.getFvLikedNum() + 1 : 0);
                } else {
                    variety.setFvLikedNum(variety.getFvLikedNum() + 1);
                    contentName = variety.getFvName();
                }
            }
//存点赞记录表
            if (null != record) {
                record.setFlrState(record.getFlrState() == 0 ? 1 : 0);
                record.setFlrTime(TimeUtil.dateToLong());
                likeRecordService.updateById(record);
            } else {
                FrsLikeRecord addRecord = new FrsLikeRecord();
                addRecord.setFlrUserId(userId);
                addRecord.setFlrTypeId(type);
                addRecord.setFlrContentId(id);
                addRecord.setFlrTypeName(Const.getModelType(type));
                addRecord.setFlrContentName(contentName);
                likeRecordService.save(addRecord);
            }
            LOGGER.info(Const.getModelType(type) + contentName + "====点赞状态改变");
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
    }

    /**
     * 改变收藏状态
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("changeBookMark")
    public JsonNode changeBookMark(@Param("type") Integer type, Integer id) {
        String contentName = null;
        try {
            Long userId = UserUtil.getUserId();
            QueryWrapper<FrsBookmarkRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FrsBookmarkRecord::getFbrContentId, id).eq(FrsBookmarkRecord::getFbrUserId, userId).eq(FrsBookmarkRecord::getFbrTypeId, type);
            FrsBookmarkRecord record = bookmarkRecordService.getOne(queryWrapper, false);
//       更改各个表的收藏数
            if (Const.MODEL_TYPE_CONCERT == type) {
                FrsConcert concert = concertService.getById(id);
                if (null != record) {
                    concert.setFcCollectionNum(concert.getFcCollectionNum() == 0 ? concert.getFcLikedNum() - 1 : 0);
                } else {
                    concert.setFcCollectionNum(concert.getFcCollectionNum() + 1);
                    contentName = concert.getFcTitle();
                }
                concertService.save(concert);
            } else if (Const.MODEL_TYPE_DANCE == type) {
                FrsDance dance = danceService.getById(id);
                if (null != record) {
                    dance.setFdCollectionNum(dance.getFdCollectionNum() == 0 ? dance.getFdLikedNum() - 1 : 0);
                } else {
                    dance.setFdCollectionNum(dance.getFdCollectionNum() + 1);
                    contentName = dance.getFdTvName();
                }
                danceService.save(dance);
            } else if (Const.MODEL_TYPE_SONG == type) {
                FrsSong song = songService.getById(id);
                if (null != record) {
                    song.setFsCollectionNum(song.getFsCollectionNum() == 0 ? song.getFsLikeNum() - 1 : 0);
                } else {
                    song.setFsCollectionNum(song.getFsCollectionNum() + 1);
                    contentName = song.getFsName();
                }
                songService.save(song);
            } else if (Const.MODEL_TYPE_ENDORSEMENT == type) {
                FrsEndorsement endorsement = endorsementService.getById(id);
                if (null != record) {
                    endorsement.setFeCollectionNum(endorsement.getFeCollectionNum() == 0 ? endorsement.getFeLikedNum() - 1 : 0);
                } else {
                    endorsement.setFeCollectionNum(endorsement.getFeCollectionNum() + 1);
                    contentName = endorsement.getFeName();
                }
                endorsementService.save(endorsement);
            } else if (Const.MODEL_TYPE_MOVIE == type) {
                FrsMovie movie = movieService.getById(id);
                if (null != record) {
                    movie.setFmCollectionNum(movie.getFmCollectionNum() == 0 ? movie.getFmLikedNum() + 1 : 0);
                } else {
                    movie.setFmCollectionNum(movie.getFmCollectionNum() + 1);
                    contentName = movie.getFmName();
                }
                movieService.save(movie);
            } else if (Const.MODEL_TYPE_TV == type) {
                FrsTv tv = tvService.getById(id);
                if (null != record) {
                    tv.setFtCollectionNum(tv.getFtCollectionNum() == 0 ? tv.getFtLikedNum() + 1 : 0);
                } else {
                    tv.setFtCollectionNum(tv.getFtCollectionNum() + 1);
                    contentName = tv.getFtName();
                }
                tvService.save(tv);
            } else if (Const.MODEL_TYPE_VARIETY == type) {
                FrsVariety variety = varietyService.getById(id);
                if (null != record) {
                    variety.setFvCollectionNum(variety.getFvCollectionNum() == 0 ? variety.getFvLikedNum() + 1 : 0);
                } else {
                    variety.setFvCollectionNum(variety.getFvCollectionNum() + 1);
                    contentName = variety.getFvName();
                }
            }
//存点赞记录表
            if (null != record) {
                record.setFbrState(record.getFbrState() == 0 ? 1 : 0);
                record.setFbrTime(TimeUtil.dateToLong());
                bookmarkRecordService.updateById(record);
            } else {
                FrsBookmarkRecord addRecord = new FrsBookmarkRecord();
                addRecord.setFbrUserId(userId);
                addRecord.setFbrTypeId(type);
                addRecord.setFbrContentId(id);
                addRecord.setFbrTypeName(Const.getModelType(type));
                addRecord.setFbrContentName(contentName);
                bookmarkRecordService.save(addRecord);
            }
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("参数传递异常");
        }
    }
}
