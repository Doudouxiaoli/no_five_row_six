package com.wx.no_five_row_six.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.common.excel.ExcelData;
import com.wx.no_five_row_six.common.Const;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsZyxVisitLog;
import com.wx.no_five_row_six.mapper.FrsZyxVisitLogMapper;
import com.wx.no_five_row_six.service.IFrsZyxVisitLogService;
import com.wx.no_five_row_six.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 访问记录日志表 服务实现类
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
@Service
public class FrsZyxVisitLogServiceImpl extends ServiceImpl<FrsZyxVisitLogMapper, FrsZyxVisitLog> implements IFrsZyxVisitLogService {
    //@Autowired
    //private FrsLikeRecordServiceImpl likeRecordService;
    //@Autowired
    //private FrsBookmarkRecordServiceImpl bookmarkRecordService;
    @Autowired
    private IWechatUserService userService;

    @Override
    public Boolean saveVisit(Long id, HttpServletRequest request, String type, String contentName) {
        Integer like = 0;
        Long likeTime = null;
        Integer favorite = 0;
        Long favoriteTime = null;
        Long now = System.currentTimeMillis();
        Long userId = UserUtil.getUserId();
        String ip = getRemoteIpAddr(request);
        //QueryWrapper<FrsLikeRecord> likeRecordQueryWrapper = new QueryWrapper<>();
        //likeRecordQueryWrapper.lambda()
        //        .eq(FrsLikeRecord::getFlrUserId, userId)
        //        .eq(FrsLikeRecord::getFlrTypeId, type)
        //        .eq(FrsLikeRecord::getFlrContentId, id);
        //FrsLikeRecord likeRecord = likeRecordService.getOne(likeRecordQueryWrapper, false);
        //if (null != likeRecord) {
        //    like = likeRecord.getFlrState();
        //    likeTime = likeRecord.getFlrTime();
        //}
        //QueryWrapper<FrsBookmarkRecord> bookmarkRecordQueryWrapper = new QueryWrapper<>();
        //bookmarkRecordQueryWrapper.lambda()
        //        .eq(FrsBookmarkRecord::getFbrUserId, userId)
        //        .eq(FrsBookmarkRecord::getFbrTypeId, type)
        //        .eq(FrsBookmarkRecord::getFbrContentId, id);
        //FrsBookmarkRecord bookmarkRecord = bookmarkRecordService.getOne(bookmarkRecordQueryWrapper, false);
        //if (null != bookmarkRecord) {
        //    favorite = bookmarkRecord.getFbrState();
        //    favoriteTime = bookmarkRecord.getFbrTime();
        //}
        FrsZyxVisitLog visitLog = new FrsZyxVisitLog();
        visitLog.setZvlUserId(userId);
        visitLog.setZvlNewsId(id);
        visitLog.setZvlVisitModule(contentName);
        visitLog.setZvlNewsModule(type);
        visitLog.setZvlIp(ip);
        visitLog.setZvlStartTime(now);
        return save(visitLog);
    }

    /**
     * 导出日志
     *
     * @param visitLogList
     * @return
     */
    @Override
    public ExcelData exportLogExcel(List<FrsZyxVisitLog> visitLogList) {
        ExcelData data = new ExcelData();
        data.setName("访问日志");
        List<String> titles = Arrays.asList("用户名", "浏览时间", "观看板块", "资讯主键ID", "题目", "是否点赞（是/否/取消点赞）", "点赞时间", "是否收藏（是/否/取消收藏）",
                "收藏时间", "访问ip");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        if (visitLogList != null && visitLogList.size() > 0) {
            for (FrsZyxVisitLog log : visitLogList) {
                String userName = userService.getById(log.getZvlUserId()).getWuNickname();
                List<Object> row = new ArrayList<>();
                row.add(userName);
                row.add(log.getZvlDuration());
                row.add(log.getZvlNewsModule());
                row.add(log.getZvlNewsId());
                row.add(log.getZvlVisitModule());
                row.add(log.getZvlLike() == 1 ? "是" : "否");
                row.add(log.getZvlLikeTime());
                row.add(log.getZvlFavorite() == 1 ? "是" : "否");
                row.add(log.getZvlFavoriteTime());
                row.add(log.getZvlIp());
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
