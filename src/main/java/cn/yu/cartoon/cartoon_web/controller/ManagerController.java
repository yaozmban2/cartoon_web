package cn.yu.cartoon.cartoon_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/15 13:45
 **/
@Controller
public class ManagerController {

    @GetMapping("/manager")
    public String getManager() {
        return "dashboard";
    }
}
