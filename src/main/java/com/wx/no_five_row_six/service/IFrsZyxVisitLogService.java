package com.wx.no_five_row_six.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.common.excel.ExcelData;
import com.wx.no_five_row_six.entity.FrsZyxVisitLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 访问记录日志表 服务类
 * </p>
 *
 * @author dxl
 * @since 2020-09-27
 */
public interface IFrsZyxVisitLogService extends IService<FrsZyxVisitLog> {
    /**
     * 保存一条访问记录
     *
     * @param id          访问的具体内容id
     * @param request
     * @param type        访问的模块(电影/电视剧/综艺/代言/演唱会/歌曲/舞蹈)
     * @param contentName 访问的具体内容标题(namanana)
     * @return
     */

    Boolean saveVisit(Long id, HttpServletRequest request, String type, String contentName);

    /**
     * 导出记录
     *
     * @param visitLogList
     * @return
     */
    ExcelData exportLogExcel(List<FrsZyxVisitLog> visitLogList);
}
