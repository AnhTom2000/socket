package com.github.anhtom2000.entity;

import lombok.*;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/06/07
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO<T> {

    Integer code;

    String message;

    T rows;

    Integer total;

    Boolean status;
}