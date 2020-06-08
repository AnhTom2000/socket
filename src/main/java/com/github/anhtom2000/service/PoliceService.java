package com.github.anhtom2000.service;

import com.github.anhtom2000.entity.AdminDTO;
import com.github.anhtom2000.entity.Police;

import java.util.List;

/**
 * 报警表(Police)表服务接口
 *
 * @author makejava
 * @since 2020-06-05 22:17:34
 */
public interface PoliceService {



    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    AdminDTO<List<Police>> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tbPolice 实例对象
     * @return 实例对象
     */
    void insert(Police tbPolice);

    /**
     * 通过主键删除数据
     *
     * @param policeId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer policeId);

}