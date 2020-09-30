//package com.wx.no_five_row_six.controller.api;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.wx.common.util.TimeUtil;
//import com.wx.no_five_row_six.common.Const;
//import com.wx.no_five_row_six.common.ServletUtil;
//import com.wx.common.jackson.JacksonMapper;
//import com.wx.no_five_row_six.common.security.UserUtil;
//import com.wx.no_five_row_six.entity.*;
//import com.wx.no_five_row_six.service.impl.*;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.annotations.Param;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author dxl
// * @date 2020/4/9
// * @desc 公共
// */
//@Controller
//@RequestMapping("/api/common")
//public class CommonController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
//    @Autowired
//    private FrsUserServiceImpl userService;
//    @Autowired
//    private SmsCodeServiceImpl smsCodeService;
//    @Autowired
//    private FrsConcertServiceImpl concertService;
//    @Autowired
//    private FrsDanceServiceImpl danceService;
//    @Autowired
//    private FrsSongServiceImpl songService;
//    @Autowired
//    private FrsEndorsementServiceImpl endorsementService;
//    @Autowired
//    private FrsMovieServiceImpl movieService;
//    @Autowired
//    private FrsTvServiceImpl tvService;
//    @Autowired
//    private FrsVarietyServiceImpl varietyService;
//    @Autowired
//    private FrsLikeRecordServiceImpl likeRecordService;
//    @Autowired
//    private FrsBookmarkRecordServiceImpl bookmarkRecordService;
//
//    /**
//     * 修改点赞状态
//     */
//    @ResponseBody
//    @RequestMapping("changeLike")
//    public JsonNode changeLike(@Param("type") Integer type, Integer id) {
//        String contentName = null;
//        try {
//            Long userId = UserUtil.getUserId();
//            QueryWrapper<FrsLikeRecord> queryWrapper = new QueryWrapper<>();
//            queryWrapper.lambda().eq(FrsLikeRecord::getFlrContentId, id).eq(FrsLikeRecord::getFlrUserId, userId).eq(FrsLikeRecord::getFlrTypeId, type);
//            FrsLikeRecord record = likeRecordService.getOne(queryWrapper, false);
//            //       更改各个表的点赞数
//            if (Const.MODEL_TYPE_CONCERT == type) {
//                FrsConcert concert = concertService.getById(id);
//                if (null != record) {
//                    concert.setFcLikedNum(concert.getFcLikedNum() == 0 ? concert.getFcLikedNum() - 1 : 0);
//                } else {
//                    concert.setFcLikedNum(concert.getFcLikedNum() + 1);
//                    contentName = concert.getFcTitle();
//                }
//                concertService.save(concert);
//            } else if (Const.MODEL_TYPE_DANCE == type) {
//                FrsDance dance = danceService.getById(id);
//                if (null != record) {
//                    dance.setFdLikedNum(dance.getFdLikedNum() == 0 ? dance.getFdLikedNum() - 1 : 0);
//                } else {
//                    dance.setFdLikedNum(dance.getFdLikedNum() + 1);
//                    contentName = dance.getFdTvName();
//                }
//                danceService.save(dance);
//            } else if (Const.MODEL_TYPE_SONG == type) {
//                FrsSong song = songService.getById(id);
//                if (null != record) {
//                    song.setFsLikeNum(song.getFsLikeNum() == 0 ? song.getFsLikeNum() - 1 : 0);
//                } else {
//                    song.setFsLikeNum(song.getFsLikeNum() + 1);
//                    contentName = song.getFsName();
//                }
//                songService.save(song);
//            } else if (Const.MODEL_TYPE_ENDORSEMENT == type) {
//                FrsEndorsement endorsement = endorsementService.getById(id);
//                if (null != record) {
//                    endorsement.setFeLikedNum(endorsement.getFeLikedNum() == 0 ? endorsement.getFeLikedNum() - 1 : 0);
//                } else {
//                    endorsement.setFeLikedNum(endorsement.getFeLikedNum() + 1);
//                    contentName = endorsement.getFeName();
//                }
//                endorsementService.save(endorsement);
//            } else if (Const.MODEL_TYPE_MOVIE == type) {
//                FrsMovie movie = movieService.getById(id);
//                if (null != record) {
//                    movie.setFmLikedNum(movie.getFmLikedNum() == 0 ? movie.getFmLikedNum() + 1 : 0);
//                } else {
//                    movie.setFmLikedNum(movie.getFmLikedNum() + 1);
//                    contentName = movie.getFmName();
//                }
//                movieService.save(movie);
//            } else if (Const.MODEL_TYPE_TV == type) {
//                FrsTv tv = tvService.getById(id);
//                if (null != record) {
//                    tv.setFtLikedNum(tv.getFtLikedNum() == 0 ? tv.getFtLikedNum() + 1 : 0);
//                } else {
//                    tv.setFtLikedNum(tv.getFtLikedNum() + 1);
//                    contentName = tv.getFtName();
//                }
//                tvService.save(tv);
//            } else if (Const.MODEL_TYPE_VARIETY == type) {
//                FrsVariety variety = varietyService.getById(id);
//                if (null != record) {
//                    variety.setFvLikedNum(variety.getFvLikedNum() == 0 ? variety.getFvLikedNum() + 1 : 0);
//                } else {
//                    variety.setFvLikedNum(variety.getFvLikedNum() + 1);
//                    contentName = variety.getFvName();
//                }
//            }
//            //存点赞记录表
//            if (null != record) {
//                record.setFlrState(record.getFlrState() == 0 ? 1 : 0);
//                record.setFlrTime(TimeUtil.dateToLong());
//                likeRecordService.updateById(record);
//            } else {
//                FrsLikeRecord addRecord = new FrsLikeRecord();
//                addRecord.setFlrUserId(userId);
//                addRecord.setFlrTypeId(type);
//                addRecord.setFlrContentId(id);
//                addRecord.setFlrTypeName(Const.getModelType(type));
//                addRecord.setFlrContentName(contentName);
//                likeRecordService.save(addRecord);
//            }
//            LOGGER.info(Const.getModelType(type) + contentName + "====点赞状态改变");
//            return JacksonMapper.newSuccessInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return JacksonMapper.newErrorInstance("参数传递异常");
//        }
//    }
//
//    /**
//     * 改变收藏状态
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("changeBookMark")
//    public JsonNode changeBookMark(@Param("type") Integer type, Integer id) {
//        String contentName = null;
//        try {
//            Long userId = UserUtil.getUserId();
//            QueryWrapper<FrsBookmarkRecord> queryWrapper = new QueryWrapper<>();
//            queryWrapper.lambda().eq(FrsBookmarkRecord::getFbrContentId, id).eq(FrsBookmarkRecord::getFbrUserId, userId).eq(FrsBookmarkRecord::getFbrTypeId, type);
//            FrsBookmarkRecord record = bookmarkRecordService.getOne(queryWrapper, false);
//            //       更改各个表的收藏数
//            if (Const.MODEL_TYPE_CONCERT == type) {
//                FrsConcert concert = concertService.getById(id);
//                if (null != record) {
//                    concert.setFcCollectionNum(concert.getFcCollectionNum() == 0 ? concert.getFcLikedNum() - 1 : 0);
//                } else {
//                    concert.setFcCollectionNum(concert.getFcCollectionNum() + 1);
//                    contentName = concert.getFcTitle();
//                }
//                concertService.save(concert);
//            } else if (Const.MODEL_TYPE_DANCE == type) {
//                FrsDance dance = danceService.getById(id);
//                if (null != record) {
//                    dance.setFdCollectionNum(dance.getFdCollectionNum() == 0 ? dance.getFdLikedNum() - 1 : 0);
//                } else {
//                    dance.setFdCollectionNum(dance.getFdCollectionNum() + 1);
//                    contentName = dance.getFdTvName();
//                }
//                danceService.save(dance);
//            } else if (Const.MODEL_TYPE_SONG == type) {
//                FrsSong song = songService.getById(id);
//                if (null != record) {
//                    song.setFsCollectionNum(song.getFsCollectionNum() == 0 ? song.getFsLikeNum() - 1 : 0);
//                } else {
//                    song.setFsCollectionNum(song.getFsCollectionNum() + 1);
//                    contentName = song.getFsName();
//                }
//                songService.save(song);
//            } else if (Const.MODEL_TYPE_ENDORSEMENT == type) {
//                FrsEndorsement endorsement = endorsementService.getById(id);
//                if (null != record) {
//                    endorsement.setFeCollectionNum(endorsement.getFeCollectionNum() == 0 ? endorsement.getFeLikedNum() - 1 : 0);
//                } else {
//                    endorsement.setFeCollectionNum(endorsement.getFeCollectionNum() + 1);
//                    contentName = endorsement.getFeName();
//                }
//                endorsementService.save(endorsement);
//            } else if (Const.MODEL_TYPE_MOVIE == type) {
//                FrsMovie movie = movieService.getById(id);
//                if (null != record) {
//                    movie.setFmCollectionNum(movie.getFmCollectionNum() == 0 ? movie.getFmLikedNum() + 1 : 0);
//                } else {
//                    movie.setFmCollectionNum(movie.getFmCollectionNum() + 1);
//                    contentName = movie.getFmName();
//                }
//                movieService.save(movie);
//            } else if (Const.MODEL_TYPE_TV == type) {
//                FrsTv tv = tvService.getById(id);
//                if (null != record) {
//                    tv.setFtCollectionNum(tv.getFtCollectionNum() == 0 ? tv.getFtLikedNum() + 1 : 0);
//                } else {
//                    tv.setFtCollectionNum(tv.getFtCollectionNum() + 1);
//                    contentName = tv.getFtName();
//                }
//                tvService.save(tv);
//            } else if (Const.MODEL_TYPE_VARIETY == type) {
//                FrsVariety variety = varietyService.getById(id);
//                if (null != record) {
//                    variety.setFvCollectionNum(variety.getFvCollectionNum() == 0 ? variety.getFvLikedNum() + 1 : 0);
//                } else {
//                    variety.setFvCollectionNum(variety.getFvCollectionNum() + 1);
//                    contentName = variety.getFvName();
//                }
//            }
//            //存点赞记录表
//            if (null != record) {
//                record.setFbrState(record.getFbrState() == 0 ? 1 : 0);
//                record.setFbrTime(TimeUtil.dateToLong());
//                bookmarkRecordService.updateById(record);
//            } else {
//                FrsBookmarkRecord addRecord = new FrsBookmarkRecord();
//                addRecord.setFbrUserId(userId);
//                addRecord.setFbrTypeId(type);
//                addRecord.setFbrContentId(id);
//                addRecord.setFbrTypeName(Const.getModelType(type));
//                addRecord.setFbrContentName(contentName);
//                bookmarkRecordService.save(addRecord);
//            }
//            return JacksonMapper.newSuccessInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return JacksonMapper.newErrorInstance("参数传递异常");
//        }
//    }
//
//    /**
//     * 发送验证码
//     *
//     * @param mobile 手机号
//     * @param type   ：smsCodeService.MOBILE_REGISTER 注册验证码
//     *               ： smsCodeService.MOBILE_LOGIN 登录验证码
//     *               :smsCodeService.MOBILE_MODIFY 修改验证码
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("sendCode")
//    public JsonNode sendCode(String mobile, String type, HttpServletRequest request) {
//        try {
//            if (StringUtils.isEmpty(mobile)) {
//                return JacksonMapper.newErrorInstance("手机号不能为空，请重试！");
//            }
//            if (StringUtils.isEmpty(type)) {
//                return JacksonMapper.newErrorInstance("type参数为空，请重试！");
//            }
//            if (smsCodeService.MOBILE_REGISTER.equals(type)) {
//                //注册类型时 验证手机号是否已经使用过
//                QueryWrapper<FrsUser> registerWrapper = new QueryWrapper<>();
//                registerWrapper.lambda().eq(FrsUser::getFuPhone, mobile);
//                FrsUser user = userService.getOne(registerWrapper, false);
//                if (user != null) {
//                    return JacksonMapper.newErrorInstance("此手机号已被注册过，请重试！");
//                }
//            } else if (smsCodeService.MOBILE_LOGIN.equals(type)) {
//                //登录时 验证手机号是否存在
//                QueryWrapper<FrsUser> loginWrapper = new QueryWrapper<>();
//                loginWrapper.lambda().eq(FrsUser::getFuPhone, mobile);
//                FrsUser user = userService.getOne(loginWrapper, false);
//                if (user == null) {
//                    return JacksonMapper.newErrorInstance("此手机号不存在或未注册，请重试！");
//                }
//            } else if (smsCodeService.MOBILE_MODIFY.equals(type)) {
//                //修改手机号时 验证手机号是否存在
//                QueryWrapper<FrsUser> modifyWrapper = new QueryWrapper<>();
//                modifyWrapper.lambda().eq(FrsUser::getFuPhone, mobile);
//                FrsUser user = userService.getOne(modifyWrapper, false);
//                if (user != null) {
//                    return JacksonMapper.newErrorInstance("此手机号已被注册过，请重试！");
//                }
//            } else {
//                //除微信绑定外的其他类型不能发送验证码
//                return JacksonMapper.newErrorInstance("type参数类型不存在，请重试！");
//            }
//            return smsCodeService.saveCode(ServletUtil.getIpAddress(request), mobile, type, null);
//        } catch (Exception e) {
//            String errMsg = "手机号登录验证码发送异常";
//            LOGGER.error(e.getMessage(), e);
//            return JacksonMapper.newErrorInstance(errMsg);
//        }
//    }
//
//    /**
//     * 检验验证码是否正确
//     *
//     * @param code
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("checkCode")
//    public JsonNode checkCode(String code, String type) {
//        QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(SmsCode::getScCode, code)
//                .eq(SmsCode::getScType, type)
//                .eq(SmsCode::getScIsUsed, 0)
//                .le(SmsCode::getScCreateDate, TimeUtil.dateTolong())
//                .ge(SmsCode::getScInvalidDate, TimeUtil.dateTolong());
//        SmsCode dbCode = smsCodeService.getOne(queryWrapper, false);
//        if (dbCode == null) {
//            return JacksonMapper.newErrorInstance("验证码失效或不存在");
//        }
//        return JacksonMapper.newSuccessInstance();
//    }
//
//}
