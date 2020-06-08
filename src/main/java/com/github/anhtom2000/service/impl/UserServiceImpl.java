package com.github.anhtom2000.service.impl;

import com.github.anhtom2000.entity.User;
import com.github.anhtom2000.mapper.UserMapper;
import com.github.anhtom2000.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2020-06-05 22:12:34
 */
@Service("UserService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByName(String name) {
        return userMapper.queryByName(name);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public void insert(User user) {
        if (!ObjectUtils.isEmpty(user)) {
            User user1 = queryByName(user.getUsername());
            if(user1==null)userMapper.insert(user);
            else throw new RuntimeException("该用户名已注册");
        } else {
            log.warn("注册的user对象不能为空");
            throw new NullPointerException("不能为空");
        }
    }

}