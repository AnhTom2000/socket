package com.github.anhtom2000.service;


import com.github.anhtom2000.entity.User;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2020-06-05 22:12:33
 */
public interface UserService {


    /**
     * 通过ID查询单条数据
     *
     * @param name 主键
     * @return 实例对象
     */
    User queryByName(String name);


    /**
     * 新增数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    void insert(User tbUser);



}