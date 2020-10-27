package com.wx.no_five_row_six.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.no_five_row_six.common.ZyxNewsConst;
import com.wx.no_five_row_six.common.security.AdminUserUtil;
import com.wx.no_five_row_six.common.security.UserUtil;
import com.wx.no_five_row_six.entity.FrsZyxLikeRecord;
import com.wx.no_five_row_six.entity.FrsZyxNews;
import com.wx.no_five_row_six.mapper.FrsZyxNewsMapper;
import com.wx.no_five_row_six.service.IFrsZyxLikeRecordService;
import com.wx.no_five_row_six.service.IFrsZyxNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 通用表 服务实现类
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
@Service
public class FrsZyxNewsServiceImpl extends ServiceImpl<FrsZyxNewsMapper, FrsZyxNews> implements IFrsZyxNewsService {
    @Autowired
    IFrsZyxNewsService newsService;
    @Autowired
    IFrsZyxLikeRecordService likeRecordService;

    @Override
    public List<FrsZyxNews> getChildList(Long moduleId, Integer tagId) {
        QueryWrapper<FrsZyxNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FrsZyxNews::getZnNcId, moduleId);
        if (null != tagId) {
            queryWrapper.lambda().eq(FrsZyxNews::getZnTagIds, tagId);
        }
        List<FrsZyxNews> fatherList = newsService.list(queryWrapper);
        List idList = new ArrayList();
        for (FrsZyxNews father : fatherList) {
            idList.add(father.getZnId());
        }
        QueryWrapper<FrsZyxNews> queryWrapper1 = new QueryWrapper<>();
        if (null != idList) {
            queryWrapper1.lambda().in(FrsZyxNews::getZnFromId, idList);
            List<FrsZyxNews> childList = newsService.list(queryWrapper1);
            return childList;
        } else {
            return fatherList;
        }
    }
    @Override
    public void changeValid(Long id) {
        FrsZyxNews news = this.getById(id);
        if (news.getZnIsValid() == null || ZyxNewsConst.NOT_VALID == news.getZnIsValid()) {
            news.setZnIsValid(ZyxNewsConst.VALID);
        } else {
            news.setZnIsValid(ZyxNewsConst.NOT_VALID);
        }
        news.setZnUpdateTime(System.currentTimeMillis());
        news.setZnUpdateUserId(AdminUserUtil.getUserId());
        news.setZnUpdateUserName(AdminUserUtil.getShowName());
        updateById(news);
    }

    @Override
    public void addNewsLike(Long newsId) {
        QueryWrapper<FrsZyxLikeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FrsZyxLikeRecord::getZlrNewsId, newsId)
                .eq(FrsZyxLikeRecord::getZlrType, 0)
                .eq(FrsZyxLikeRecord::getZlrUserId, UserUtil.getUserId());
        FrsZyxLikeRecord record = likeRecordService.getOne(queryWrapper);
        if (null == record) {
            record = new FrsZyxLikeRecord();
            record.setZlrNewsId(newsId);
            record.setZlrUserId(UserUtil.getUserId());
            record.setZlrType(ZyxNewsConst.NOT_VALID);
            record.setZlrTime(System.currentTimeMillis());
            FrsZyxNews news = getById(newsId);
            news.setZnLikeNum(news.getZnLikeNum() == null ? 1 : news.getZnLikeNum() + 1);
            updateById(news);
        }

    }
}
