package com.github.anhtom2000.mapper;

import com.github.anhtom2000.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 用户表(TbUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-05 22:49:28
 */
@Repository("userMapper")
public interface UserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Select("SELECT * FROM tb_user WHERE user_id = #{userId}")
    @ResultMap("userMap")
    User queryById(Integer userId);

    @Select("SELECT * FROM tb_user WHERE username = #{name}")
    @Results(id = "userMap", value = {
            @Result(id = true,column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "login_time", property = "loginTime")
    })
    User queryByName(String name);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @ResultMap("userMap")
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbUser 实例对象
     * @return 对象列表
     */
    @ResultMap("userMap")
    List<User> queryAll(User tbUser);

    /**
     * 新增数据
     *
     * @param tbUser 实例对象
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO tb_user(username,password,create_time,login_time) VALUES (#{user.username},#{user.password},#{user.createTime},#{user.loginTime})")
    int insert(@Param("user") User tbUser);

    /**
     * 修改数据
     *
     * @param userId 用户主键
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true)
    @Update("UPDATE tb_user SET login_time = #{loginTime} WHERE user_id = #{userId}")
    int updateLoginTime(@Param("userId") Integer userId, @Param("loginTime") LocalDateTime loginTime);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    @Options(useGeneratedKeys = true)
    @Delete("DELETE FROM tb_user WHERE user_id = #{userId}")
    int deleteById(Integer userId);

}