package com.github.anhtom2000.mapper;

import com.github.anhtom2000.entity.Led;
import com.github.anhtom2000.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Weleness
 * @date 2020/06/05
 * @description TODO
 */
@Repository("LedMapper")
public interface LedMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param ledId 主键
     * @return 实例对象
     */
    @Select("SELECT * FROM tb_led WHERE led_id = #{ledId}")
    Led queryById(Integer ledId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Results(id = "ledMap", value = {
            @Result(id = true, column = "led_id", property = "ledId"),
            @Result(column = "led_name", property = "ledName"),
            @Result(column = "opened", property = "opened"),
            @Result(column = "user_id", property = "user", javaType = User.class, one = @One(select = "com.github.anhtom2000.mapper.UserMapper.queryById")),
            @Result(column = "control_time",property = "controlTime",javaType = LocalDateTime.class)
    })
    @Select("SELECT * FROM tb_led LIMIT #{offset},#{limit}")
    List<Led> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    @Select("SELECT COUNT(*) FROM tb_led")
    public Integer getCount();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbLed 实例对象
     * @return 对象列表
     */
    List<Led> queryAll(Led tbLed);

    /**
     * 新增数据
     *
     * @param led 实例对象
     * @return 影响行数
     */
    @Insert("INSERT INTO tb_led(led_name,opened,user_id,control_time) VALUES (#{led.ledName},#{led.opened},#{led.user.userId},#{led.controlTime})")
    int insert(@Param("led") Led led);

    /**
     * 修改数据
     *
     * @param led 实例对象
     * @return 影响行数
     */
    int update(Led led);

    /**
     * 通过主键删除数据
     *
     * @param ledId 主键
     * @return 影响行数
     */
    @Delete("DELETE FORM  tb_led WHERE led_id = #{ledId}")
    int deleteById(Integer ledId);
}
