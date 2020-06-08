package com.github.anhtom2000.controller;


import com.github.anhtom2000.entity.Police;
import com.github.anhtom2000.entity.User;
import com.github.anhtom2000.hello.GreetingClient;
import com.github.anhtom2000.service.PoliceService;
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
 * 报警表(Police)表控制层
 *
 * @author makejava
 * @since 2020-06-05 22:17:34
 */
@Controller
@RequestMapping("/police")
@Slf4j
public class PoliceController {
    /**
    // * 服务对象
    // */
    @Autowired
    private PoliceService policeService;

    @Autowired
    private UserService userService;

    @Autowired
    private GreetingClient greetingServer;

    @RequestMapping("/doPolice")
    @ResponseBody
    public String doPolice(@AuthenticationPrincipal Principal principal, @RequestBody Police police){
        String name = principal.getName();
        User user = userService.queryByName(name);
        police.setUser(user);
        police.setPoliceTime(LocalDateTime.now(Clock.systemDefaultZone()));
        String logInfo  = "警报器";
        logInfo+=police.getOpened() ? ",报警":",“关闭";
        log.info("警报器的状态:{}",logInfo);
        policeService.insert(police);
        return null;
    }

    @ResponseBody
    @RequestMapping("/getPolice")
    public Police getPolice(){
        Police police = null;
        try {
            greetingServer.getOut().write("3_2".getBytes());
            police = new Police();
            while(greetingServer.getReceiveData()==null){}
            String receiveData = greetingServer.getReceiveData();
            police.setOpened("3_1".equals(receiveData));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return police;
    }

}