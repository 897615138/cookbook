package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/*
wj bean封装
wj20200806
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo implements Serializable {
    //评论id
    private String commentId;
    //菜谱id
    private String dishId;
    //用户id
    private String userId;
    //评论内容
    private String commentText;
    //评论时间
    private Date commentTime;
    //父评论
    private String parentUserId;
    //评论是否删除 逻辑删除
    private Integer commentDelete;


    //limit 参数1
    private Integer modPage;
    //limit 参数2
    private Integer pageSize;

    //评论当前页
    private Integer currPage;


    //评论的用户列表
    private UserInfo userInfos;
    private UserInfo parentInfos;


    // private static final long serialVersionUID = 1L;
}