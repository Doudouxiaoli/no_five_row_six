package com.wx.no_five_row_six.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.no_five_row_six.common.security.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class IndexController {
    @ResponseBody
    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public JsonNode index() {
        if (UserUtil.hasLogin()) {
            return JacksonMapper.newSuccessInstance();
        } else {
            return JacksonMapper.newErrorInstance("用户未登录");
        }
    }
}
