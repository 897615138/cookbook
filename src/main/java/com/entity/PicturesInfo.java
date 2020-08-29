package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
封面图！
wj bean封装
wj20200806
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PicturesInfo implements Serializable {
    //菜谱id
    private String dishId;
    //菜谱封面图 1对1关系
    private String picAddress;

    // private static final long serialVersionUID = 5L;
}