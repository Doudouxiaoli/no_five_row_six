package com.wx.no_five_row_six.controller.api;

import com.wx.no_five_row_six.common.security.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class IndexController {
    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public String index() {
        if (UserUtil.hasLogin()) {
            return "pc/include/index";
        } else {
            return "pc/include/login";
        }
    }
}
