package com.service;

import com.entity.CommentInfo;
import com.exception.AppException;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:02
 * @slogan Just do it continually
 */
public interface CommentService {

    /**
     * 返回指定的评论
     *
     * @return
     */
    List<CommentInfo> getRootComments(CommentInfo commentInfo) throws AppException;

    List<CommentInfo> getChildComments(CommentInfo commentInfo) throws AppException;

    List<CommentInfo> getAllComments(CommentInfo commentInfo);


    /**
     * 发布评论
     *
     * @param commentInfo 评论信息
     * @throws AppException 异常
     */
    void insertComment(CommentInfo commentInfo) throws AppException;

}
