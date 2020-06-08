package com.github.anhtom2000.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报警表(Police)实体类
 *
 * @author makejava
 * @since 2020-06-05 22:17:34
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"handler"})
public class Police implements Serializable {
    private static final long serialVersionUID = -61847735987045869L;
    /**
    * 报警主键
    */
    private Integer policeId;
    /**
    * 报警的用户主键
    */
    private User user;
    /**
     * 是否正在报警
     */
    private Boolean opened;
    /**
    * 报警时间
    */
    private LocalDateTime policeTime;

}