package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
wj bean封装
wj20200806
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfo implements Serializable {
    //收藏夹Id
    private String menuId;
    //收藏夹创建者id 一个人可创建多个收藏夹 应有上限！
    private String userId;
    //收藏夹自定义名称
    private String menuName;
    //收藏夹是否被逻辑删除
    private Integer menuDelete;

   // private static final long serialVersionUID = 4L;
}