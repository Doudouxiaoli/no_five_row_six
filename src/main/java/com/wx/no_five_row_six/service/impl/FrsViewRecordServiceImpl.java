package com.wx.no_five_row_six.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.common.excel.ExcelData;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsBookmarkRecord;
import com.wx.no_five_row_six.entity.FrsLikeRecord;
import com.wx.no_five_row_six.entity.FrsViewRecord;
import com.wx.no_five_row_six.mapper.FrsViewRecordMapper;
import com.wx.no_five_row_six.service.IFrsViewRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 浏览记录表 服务实现类
 * </p>
 *
 * @author dxl
 * @since 2020-04-09
 */
@Service
public class FrsViewRecordServiceImpl extends ServiceImpl<FrsViewRecordMapper, FrsViewRecord> implements IFrsViewRecordService {
    @Autowired
    private FrsLikeRecordServiceImpl likeRecordService;
    @Autowired
    private FrsBookmarkRecordServiceImpl bookmarkRecordService;
    @Autowired
    private FrsUserServiceImpl userService;

    /**
     * 保存一条访问记录
     *
     * @param id          访问的具体内容id
     * @param request
     * @param type        访问的模块id(电影/电视剧/综艺/代言/演唱会/歌曲/舞蹈)
     * @param contentName 访问的具体内容标题(namanana)
     * @return
     */
    public boolean saveVisit(Long id, HttpServletRequest request, Integer type, String contentName) {
        Integer like = 0;
        Long likeTime = null;
        Integer favorite = 0;
        Long favoriteTime = null;
        Long now = System.currentTimeMillis();
        Long userId = UserUtil.getUserId();
        String ip = getRemoteIpAddr(request);
        QueryWrapper<FrsLikeRecord> likeRecordQueryWrapper = new QueryWrapper<>();
        likeRecordQueryWrapper.lambda()
                .eq(FrsLikeRecord::getFlrUserId, userId)
                .eq(FrsLikeRecord::getFlrTypeId, type)
                .eq(FrsLikeRecord::getFlrContentId, id);
        FrsLikeRecord likeRecord = likeRecordService.getOne(likeRecordQueryWrapper, false);
        if (null != likeRecord) {
            like = likeRecord.getFlrState();
            likeTime = likeRecord.getFlrTime();
        }
        QueryWrapper<FrsBookmarkRecord> bookmarkRecordQueryWrapper = new QueryWrapper<>();
        bookmarkRecordQueryWrapper.lambda()
                .eq(FrsBookmarkRecord::getFbrUserId, userId)
                .eq(FrsBookmarkRecord::getFbrTypeId, type)
                .eq(FrsBookmarkRecord::getFbrContentId, id);
        FrsBookmarkRecord bookmarkRecord = bookmarkRecordService.getOne(bookmarkRecordQueryWrapper, false);
        if (null != bookmarkRecord) {
            favorite = bookmarkRecord.getFbrState();
            favoriteTime = bookmarkRecord.getFbrTime();
        }
        FrsViewRecord visitLog = new FrsViewRecord();
        visitLog.setFvrUserId(userId);
        visitLog.setFvrContentId(id.intValue());
        visitLog.setFvrContentName(contentName);
        visitLog.setFvrTypeId(type);
        visitLog.setFvrTypeName(Const.getModelType(type));
        visitLog.setFvrLike(like);
        visitLog.setFvrLikeTime(likeTime);
        visitLog.setFvrFavorite(favorite);
        visitLog.setFvrFavoriteTime(favoriteTime);
        visitLog.setFvrIp(ip);
        visitLog.setFvrTime(now);
        return save(visitLog);
    }

    /**
     * 导出日志
     *
     * @param visitLogList
     * @return
     */
    public ExcelData exportLogExcel(List<FrsViewRecord> visitLogList) {
        ExcelData data = new ExcelData();
        data.setName("访问日志");
        List<String> titles = Arrays.asList("用户名", "浏览时间", "观看板块", "资讯主键ID", "题目", "是否点赞（是/否/取消点赞）", "点赞时间", "是否收藏（是/否/取消收藏）",
                "收藏时间", "访问ip");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        if (visitLogList != null && visitLogList.size() > 0) {
            for (FrsViewRecord log : visitLogList) {
                String userName = userService.getById(log.getFvrUserId()).getFuName();
                List<Object> row = new ArrayList<>();
                row.add(userName);
                row.add(log.getFvrTime());
                row.add(log.getFvrTypeName());
                row.add(log.getFvrContentId());
                row.add(log.getFvrContentName());
                row.add(log.getFvrLike() == 1 ? "是" : "否");
                row.add(log.getFvrLikeTime());
                row.add(log.getFvrFavorite() == 1 ? "是" : "否");
                row.add(log.getFvrFavoriteTime());
                row.add(log.getFvrIp());
                rows.add(row);
            }
        }
        data.setRows(rows);
        return data;
    }

    /**
     * 判断IP地址是否有效
     *
     * @param ip
     * @return
     */
    private static boolean isValidIpAddr(String ip) {
        return ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown");
    }

    /**
     * 取得请求的IP地址
     *
     * @param request
     * @return
     */
    public static String getRemoteIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isValidIpAddr(ip)) {
            return ip.split(",")[0];
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (isValidIpAddr(ip)) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (isValidIpAddr(ip)) {
            return ip;
        }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if (isValidIpAddr(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }

}
