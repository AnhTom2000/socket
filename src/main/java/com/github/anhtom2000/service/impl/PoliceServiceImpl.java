package com.github.anhtom2000.service.impl;

import com.github.anhtom2000.entity.AdminDTO;
import com.github.anhtom2000.entity.Police;
import com.github.anhtom2000.hello.GreetingClient;
import com.github.anhtom2000.mapper.PoliceMapper;
import com.github.anhtom2000.service.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * 报警表(Police)表服务实现类
 *
 * @author makejava
 * @since 2020-06-05 22:17:34
 */
@Service("PoliceService")
public class PoliceServiceImpl implements PoliceService {

    @Autowired
    private PoliceMapper policeMapper;

    @Autowired
    private GreetingClient greetingServer;


    @Override
    public AdminDTO<List<Police>> queryAllByLimit(int offset, int limit) {
        List<Police> police = policeMapper.queryAllByLimit(offset, limit);
        return new AdminDTO<>(200, null, police, policeMapper.getCount(), true);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public void insert(Police police) {
        policeMapper.insert(police);
        String protrol = police.getPoliceId() + "_";
        protrol += police.getOpened() ? "1" : "0";
        DataOutputStream out = greetingServer.getOut();
        try {
            out.write(protrol.getBytes());
            out.write("\r\n".getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public boolean deleteById(Integer policeId) {
        return policeMapper.deleteById(policeId) > 0;
    }
}