package com.github.anhtom2000.mapper;

import com.github.anhtom2000.entity.Police;
import com.github.anhtom2000.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Weleness
 * @date 2020/06/05
 * @description TODO
 */
@Repository("policeMapper")
public interface PoliceMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param policeId 主键
     * @return 实例对象
     */
   @ResultMap("policeMap")
    Police queryById(Integer policeId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Results(id = "policeMap", value = {
            @Result(id = true, column = "police_id", property = "policeId"),
            @Result(column = "opened", property = "opened"),
            @Result(column = "police_time", property = "policeTime"),
            @Result(column = "user_id", property = "user", javaType = User.class, one = @One(select = "com.github.anhtom2000.mapper.UserMapper.queryById"))
    })
    @Select("SELECT * FROM tb_police LIMIT #{offset},#{limit}")
    List<Police> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbPolice 实例对象
     * @return 对象列表
     */
    List<Police> queryAll(Police tbPolice);

    @Select("SELECT COUNT(*) FROM tb_police")
    Integer getCount();
    /**
     * 新增数据
     *
     * @param police 实例对象
     * @return 影响行数
     */
    @Insert("INSERT INTO tb_police(user_id,opened,police_time) VALUES(#{police.user.userId},#{police.opened},#{police.policeTime})")
    int insert(@Param("police") Police police);

    /**
     * 修改数据
     *
     * @param tbPolice 实例对象
     * @return 影响行数
     */
    int update(Police tbPolice);

    /**
     * 通过主键删除数据
     *
     * @param policeId 主键
     * @return 影响行数
     */
    @Delete("DELETE FROM tb_police WHERE police_id = #{policeId}")
    int deleteById(Integer policeId);

}
