package cn.yu.cartoon.cartoon_web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Yu
 * @version 1.0
 * @date 2019/3/14 21:59
 **/
@Controller
public class LoginController {


    @GetMapping("/user/login")
    public String forwardLogin() {
        System.out.println("forwardLogin");
        return "login";
    }

    @PostMapping("/user/login")
    public String login(String userName, String password) {

        System.out.println("login");

        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        //3.执行登录方法
        try {
            subject.login(token);
            return "redirect:/test.html";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "login";
        }

    }
}
