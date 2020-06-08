package com.github.anhtom2000.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * led表(Led)实体类
 *
 * @author makejava
 * @since 2020-06-05 22:17:12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler"})
@Builder
public class Led implements Serializable {
    private static final long serialVersionUID = -71944045525183538L;
    /**
    * led灯主键
    */
    private Integer ledId;
    /**
    * led灯名
    */
    private String ledName;
    /**
    * led状态
    */
    private Boolean opened;
    /**
    * 操作的用户
    */
    private User user;

    /**
     * 操作时间
     */
    private LocalDateTime controlTime;




}