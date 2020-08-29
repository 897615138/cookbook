package com.dao;

import com.entity.CommentInfo;

import java.util.List;

public interface ICommentInfoMapper {
    /*
    limit 查询
     */
    List<CommentInfo> queryCommentByDishId(CommentInfo commentInfo);

    /*
    返回所有的

     */
    List<CommentInfo> queryCommentByDishIdAll(CommentInfo commentInfo);

    /*
     每个评论id为基础按照创建的时间进行排序
     */
    List<CommentInfo> queryCommentByCommentTime(CommentInfo commentInfo);

    /*
   发表评论
     */
    void insertComment(CommentInfo commentInfo);

}
