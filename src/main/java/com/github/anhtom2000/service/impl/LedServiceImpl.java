package com.github.anhtom2000.service.impl;


import com.github.anhtom2000.entity.AdminDTO;
import com.github.anhtom2000.entity.Led;
import com.github.anhtom2000.hello.GreetingClient;
import com.github.anhtom2000.mapper.LedMapper;
import com.github.anhtom2000.service.LedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * led表(Led)表服务实现类
 *
 * @author makejava
 * @since 2020-06-05 22:17:13
 */
@Service("LedService")
public class LedServiceImpl implements LedService {

    @Autowired
    private LedMapper ledMapper;


    private GreetingClient GreetingClient;

    public LedServiceImpl(GreetingClient GreetingClient) {
        this.GreetingClient = GreetingClient;
    }

    @Override
    public Led queryById(Integer ledId) {
        return ledMapper.queryById(ledId);
    }

    @Override
    public AdminDTO<List<Led>> queryAllByLimit(int offset, int limit) {
        List<Led> leds = ledMapper.queryAllByLimit(offset, limit);
        Integer count = ledMapper.getCount();
        return new AdminDTO<>(200, null, leds, count, true);
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public void insert(Led led) {
        ledMapper.insert(led);
        String protocol;
        try {
            protocol = led.getLedId() + "_";
            protocol += led.getOpened() ? "1" : "0";
            DataOutputStream out = GreetingClient.getOut();
            out.write(protocol.getBytes());
            out.write("\r\n".getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public boolean deleteById(Integer ledId) {
        return ledMapper.deleteById(ledId) > 0;
    }
}