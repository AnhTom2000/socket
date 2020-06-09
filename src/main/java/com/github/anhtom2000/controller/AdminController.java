package com.github.anhtom2000.controller;

import com.github.anhtom2000.entity.AdminDTO;
import com.github.anhtom2000.entity.Led;
import com.github.anhtom2000.entity.Police;
import com.github.anhtom2000.service.LedService;
import com.github.anhtom2000.service.PoliceService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/06/06
 */
@RestController
@RequestMapping("/admin")
public class AdminController {


    private LedService ledService;

    @Autowired
    private PoliceService policeService;

    public AdminController (LedService ledService){
        this.ledService = ledService;
    }

    @RequestMapping("/getLed")
    public AdminDTO<List<Led>> getLed(@RequestParam("offset") int offset, @RequestParam("pageSize") int pageSize){
        return ledService.queryAllByLimit(offset, pageSize);
    }

    @RequestMapping("/getPolice")
    public AdminDTO<List<Police>> getPolice(@RequestParam("offset") int offset, @RequestParam("pageSize") int pageSize){
        return policeService.queryAllByLimit(offset, pageSize);
    }

    @RequestMapping("/delLed/{id}")
    public AdminDTO<String> delLed(@PathVariable("id") Integer id){
        ledService.deleteById(id);
        return new AdminDTO<>(200,null,"ok",null,true);
    }

    @RequestMapping("/delPolice/{id}")
    public AdminDTO<String> delPolice(@PathVariable("id")Integer id){
        policeService.deleteById(id);
        return new AdminDTO<>(200,null,"ok",null,true);
    }

}
