package com.wx.no_five_row_six.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.TimeUtil;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsBookmarkRecord;
import com.wx.no_five_row_six.entity.FrsLikeRecord;
import com.wx.no_five_row_six.entity.FrsUser;
import com.wx.no_five_row_six.entity.FrsViewRecord;
import com.wx.no_five_row_six.service.impl.FrsBookmarkRecordServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsLikeRecordServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsUserServiceImpl;
import com.wx.no_five_row_six.service.impl.FrsViewRecordServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dxl
 * @date 2020/4/28
 * @desc 个人中心
 */
@Controller
@RequestMapping("/api/userCenter")
public class UserCenterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterController.class);
    @Autowired
    private FrsUserServiceImpl userService;
    @Autowired
    private FrsViewRecordServiceImpl viewRecordService;
    @Autowired
    private FrsLikeRecordServiceImpl likeRecordService;
    @Autowired
    private FrsBookmarkRecordServiceImpl bookmarkRecordService;

    /**
     * 个人中心首页
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("index")
    public JsonNode index() {
        if (UserUtil.hasLogin()) {
            String headImg = "1111";
            //String headImg = UserUtil.getHeadImg();
            Long userId = UserUtil.getUserId();
            List<FrsViewRecord> viewRecordList = viewRecordService.list(new QueryWrapper<FrsViewRecord>().eq("fvr_user_id", userId));
            List<FrsBookmarkRecord> bookmarkRecordList = bookmarkRecordService.list(new QueryWrapper<FrsBookmarkRecord>().eq("fbr_user_id", userId));
            List<FrsLikeRecord> likeRecordList = likeRecordService.list(new QueryWrapper<FrsLikeRecord>().eq("flr_user_id", userId));
            Map<Integer, Object> map = new HashMap<>();
            map.put(0, headImg);
            map.put(1, viewRecordList);
            map.put(2, bookmarkRecordList);
            map.put(3, likeRecordList);
            return JacksonMapper.newDataInstance(map);
        } else {
            return JacksonMapper.newErrorInstance("用户信息获取失败");
        }

    }

    /**
     * 个人中心详情
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("detail")
    public JsonNode detail() {
        try {
            FrsUser user = UserUtil.getUserModel().getUser();
            return JacksonMapper.newDataInstance(user);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "用户信息查询异常";
            LOGGER.error(errMsg);
            return JacksonMapper.newErrorInstance(errMsg);
        }
    }

    /**
     * 用户信息修改
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public JsonNode update(FrsUser user) {
        try {
            user.setFuUpdateTime(TimeUtil.dateTolong());
            userService.updateById(user);
            return JacksonMapper.newSuccessInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonMapper.newErrorInstance("注册用户编辑出错");
        }
    }
}
