package com.github.anhtom2000.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2020-06-05 22:12:30
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
public class User implements Serializable {
    private static final long serialVersionUID = 637462335874268811L;
    /**
    * 用户主键
    */
    private Integer userId;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 账户创建时间
    */
    private LocalDateTime createTime;
    /**
    * 登陆时间
    */
    private LocalDateTime loginTime;




}