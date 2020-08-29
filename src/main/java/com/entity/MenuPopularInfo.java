package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
wj bean封装
暂定  伴随菜谱的建立而建立
wj20200806
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuPopularInfo implements Serializable {
    //菜谱id
    private String dishId;

    private Integer dishLike;

    private Integer dishCollect;

    private Integer dishClick;

    // private static final long serialVersionUID = 5L;
}