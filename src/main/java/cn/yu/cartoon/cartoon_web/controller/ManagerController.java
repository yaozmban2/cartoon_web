package cn.yu.cartoon.cartoon_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/manager/cartoonManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String cartoonManager(@PathVariable Integer pageCurrent,
                                 @PathVariable Integer pageSize,
                                 @PathVariable Integer pageCount) {

        return "/item/cartoonManage";

    }
}
