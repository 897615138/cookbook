package com.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zp
 * @create 2020-07-28-21:54
 * wj20200806
 */
@Data
@ToString
public class DishInfo implements Serializable {
    //菜谱id
    private String dishId;
    //创建该菜谱的用户id
    private String userId;
    //该菜谱的标签
    private String tagId;
    //菜谱名称
    private String dishName;
    //菜谱简介
    private String dishIntro;
    //菜谱内容
    private String dishText;
    //菜谱上传/创建时间
    private Date dishCreateTime;


    //菜谱最后一次更新时间
    private Date dishUpdateTime;
    //菜谱是否逻辑删除 1为已删除0为未删除
    private Integer dishDelete;
    // private static final long serialVersionUID = 2L;

    //存picture的地址集合
    private List<PicturesInfo> picturesInfoList;

    //存点赞人的user_id
    private List<String> likeUsers;


    private Integer likeNumber;

    private Integer collectNumber;
    private UserInfo userInfo;

}
