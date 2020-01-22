package com.wx.no_five_row_six.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = {"","index"})
    public String index(){
        return "index";
    }
}
