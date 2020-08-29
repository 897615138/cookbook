package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
wj bean封装
一对多关系
暂定
wj20200806
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDishInfo implements Serializable {
    //收藏夹id
    private String menuId;
    //菜谱id
    private String dishId;
    //收藏夹创建时间
    private Date collectionTime;
    //是否删除
    private Integer collectionDelete;

    private List<DishInfo> dishInfoList;
    //private static final long serialVersionUID = 3L;
}