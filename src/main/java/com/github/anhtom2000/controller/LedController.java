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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private boolean status1 = false;

    private boolean status2 = false;

    @RequestMapping("/ledToggle")
    @ResponseBody
    public String ledToggle(@AuthenticationPrincipal Principal principal, @RequestBody Led led) {
        String name = principal.getName();
        User user = userService.queryByName(name);
        led.setUser(user);
        led.setControlTime(LocalDateTime.now(Clock.systemDefaultZone()));
        String logInfo = led.getLedName();
        logInfo += led.getOpened() ? ",开灯" : ",“关灯";
        log.info("led的状态:{}", logInfo);
        ledService.insert(led);
        return null;
    }

    @ResponseBody
    @RequestMapping("/getLed1")
    public Boolean getLed1() {
        greetingClient.requestToSerialPortServer("1_2");
        while (ObjectUtils.isEmpty(greetingClient.getReceiveData())) {}
        String receiveData = greetingClient.getReceiveData();
        if (receiveData.startsWith("1_")) {
            status1 = receiveData.trim().equals("1_1");
        }
        return status1;
    }

    @ResponseBody
    @RequestMapping("/getLed2")
    public Boolean getLed2() {
        greetingClient.requestToSerialPortServer("2_2");
        while (ObjectUtils.isEmpty(greetingClient.getReceiveData())) {}
        String receiveData = greetingClient.getReceiveData();
        if (receiveData.startsWith("2_")) {
            status2 = receiveData.trim().equals("2_1");
        }
        return status2;
    }


}