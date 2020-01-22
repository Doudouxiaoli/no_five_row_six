package com.wx.no_five_row_six.controller;

import com.wx.common.jackson.JacksonMapper;
import com.wx.common.util.EncryptUtil;
import com.wx.no_five_row_six.WinxinConfig;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片
 *
 * @author fanyongqian
 * 2016年10月31日
 */
@Controller
@RequestMapping(value = "/wx")
public class WinxinController {
    private final static Logger logger = LoggerFactory.getLogger(WinxinController.class);

    @Autowired
    private WxMpService wxService;
    @Autowired
    private WinxinConfig winxinConfig;

    /**
     * 获取jssdk中的必要信息
     *
     * @param url code
     */
    @RequestMapping(value = "getJsSdk")
    @ResponseBody
    public String getJsSdk(String url) {
        try {
            String jsapiTicket = wxService.getJsapiTicket(false);

            long timestamp = System.currentTimeMillis() / 1000;
            String noncestr = EncryptUtil.randomString(16);
            String result = SHA1.genWithAmple(
                    "jsapi_ticket=" + jsapiTicket,
                    "noncestr=" + noncestr,
                    "timestamp=" + timestamp,
                    "url=" + url
            );

            Map<String, String> map = new HashMap<>();
            map.put("appId", winxinConfig.getAppId());
            map.put("timestamp", String.valueOf(timestamp));
            map.put("nonceStr", noncestr);
            map.put("signature", result);

            return JacksonMapper.newDataInstance(map).toString();
        } catch (WxErrorException e) {
            logger.error("取得SDK有错误发生。", e);
            return JacksonMapper.newErrorInstance("取得SDK有错误发生。！").toString();
        }
    }
}