package com.github.anhtom2000.controller;

import com.github.anhtom2000.entity.User;
import com.github.anhtom2000.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/06/06
 */
@Controller
public class BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String doRegister(@Param("username") String username, @Param("password") String password) {
        User user = userService.queryByName(username);
        System.out.println(username);
        if (user != null) {
            return "redirect:register?error";
        }
        user = User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .createTime(LocalDateTime.now(Clock.systemDefaultZone()))
                .loginTime(LocalDateTime.now(Clock.systemDefaultZone())).build();
        userService.insert(user);
        return "redirect:register?success";
    }

    @RequestMapping("/control")
    public String control() {
        return "control";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
