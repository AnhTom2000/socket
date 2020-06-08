package com.github.anhtom2000.controller;


import com.github.anhtom2000.entity.Led;
import com.github.anhtom2000.entity.User;
import com.github.anhtom2000.hello.GreetingClient;
import com.github.anhtom2000.service.LedService;
import com.github.anhtom2000.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.Principal;
import java.time.Clock;
import java.time.LocalDateTime;


/**
 * led表(Led)表控制层
 *
 * @author makejava
 * @since 2020-06-05 22:17:13
 */
@Controller
@RequestMapping("/led")
@Slf4j
public class LedController {
    /**
     * 服务对象
     */
    @Autowired
    private LedService ledService;

    @Autowired
    private UserService userService;

    @Autowired
    private GreetingClient greetingClient;

    @RequestMapping("/ledToggle")
    @ResponseBody
    public String ledToggle(@AuthenticationPrincipal Principal principal, @RequestBody Led led) {
        String name = principal.getName();
        User user = userService.queryByName(name);
        led.setUser(user);
        led.setControlTime(LocalDateTime.now(Clock.systemDefaultZone()));
        String logInfo  = led.getLedName();
        logInfo+=led.getOpened() ? ",开灯":",“关灯";
        log.info("led的状态:{}",logInfo);
        ledService.insert(led);
        return null;
    }

    @RequestMapping("/getLed1")
    public Led getLed1(){
        try {
            greetingClient.getOut().write("1_2".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getLed2")
    public Led getLed2(){
        try {
            greetingClient.getOut().write("2_2".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


}