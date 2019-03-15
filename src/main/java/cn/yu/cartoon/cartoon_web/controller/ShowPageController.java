package cn.yu.cartoon.cartoon_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 9:57
 **/
@Controller
public class ShowPageController {

    @RequestMapping("/index")
    public String getIndex() {
        System.out.println("进来了");
        return "index";
    }
}
