package com.github.anhtom2000.service;

import com.github.anhtom2000.entity.AdminDTO;
import com.github.anhtom2000.entity.Led;

import java.util.List;

/**
 * led表(Led)表服务接口
 *
 * @author makejava
 * @since 2020-06-05 22:17:13
 */
public interface LedService {

    /**
     * 通过ID查询单条数据
     *
     * @param ledId 主键
     * @return 实例对象
     */
    Led queryById(Integer ledId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    AdminDTO<List<Led>> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param led 实例对象
     * @return 实例对象
     */
    void insert(Led led);


    /**
     * 通过主键删除数据
     *
     * @param ledId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer ledId);

}