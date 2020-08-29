package com.service.imp;

import com.dao.ICommentInfoMapper;
import com.entity.CommentInfo;
import com.entity.UserInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.service.CommentService;
import com.utils.ResultEnum;
import com.utils.StreamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:02
 * @slogan Just do it continually
 */
public class CommentServiceImp implements CommentService {
    private static final MapperProxy<ICommentInfoMapper> iCommentInfoMapperMapperProxy = new MapperProxy<>();
    private static final ICommentInfoMapper mapper = iCommentInfoMapperMapperProxy.getMapper(ICommentInfoMapper.class);
    private static final UserServiceImp userServiceImp = new UserServiceImp();


    {
        //根评论列表

        //父子评论列表
    }


    /**
     * 获得根评论
     *
     * @param commentInfo
     * @return
     * @throws AppException
     */
    @Override
    public List<CommentInfo> getRootComments(CommentInfo commentInfo) throws AppException {
        //拿到所有的comment_id
        List<CommentInfo> commentInfos = mapper.queryCommentByDishId(commentInfo);
//        //使用流式运算去重
//        List<CommentInfo> commentInfos =
//                rawCommentInfos.stream().filter(StreamUtil.distinctByKey(CommentInfo::getCommentId))
//                        .collect(Collectors.toList());

        if (commentInfos.isEmpty()) {
            throw new AppException(ResultEnum.COMMENT_ERROR);
        }
        //遍历根评论 加上用户信息
        commentInfos.forEach(e -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(e.getUserId());
            UserInfo userInfo1 = userServiceImp.searchUser(userInfo);
            e.setUserInfos(userInfo1);
        });
//        将list数组装入list数组再遍历循环整个list
//        commentInfos.forEach((e) -> {
//
//            List<CommentInfo> childComments = getChildComments(e); //拿到所有的子评论
////            System.out.println(commentInfos1);
//            lists.add(childComments);
//
//        });
//        System.out.println(commentIdMap);
//        System.out.println(commentInfos);

        return commentInfos;
    }

//    public List<CommentInfo> getRootComments(CommentInfo commentInfo) {
//        List<CommentInfo> dishComments = getDishComments(commentInfo);
//        commentInfos.forEach(e -> {
//            UserInfo userInfo = new UserInfo();
//            userInfo.setUserId(e.getUserId());
//            UserInfo userInfo1 = userServiceImp.searchUser(userInfo); //查询子用户的信息
//            e.setUserInfos(userInfo1); //放入
//            String parentUserId = e.getParentUserId();
//            if (!"0".equals(parentUserId)) {
//                userInfo.setUserId(parentUserId);
//                UserInfo userInfo2 = userServiceImp.searchUser(userInfo);
//                e.setParentInfos(userInfo2);//父用户的信息
//            }
//        });
//
//        return commentInfos;
//    }


    /**
     * 传入一个根评论信息,获得子评论的所有信息
     *
     * @param commentInfo
     * @return
     */
    @Override
    public List<CommentInfo> getChildComments(CommentInfo commentInfo) throws AppException {
        List<CommentInfo> commentInfos = mapper.queryCommentByCommentTime(commentInfo);
        if (commentInfos.isEmpty()) throw new AppException(ResultEnum.CHILD_COMMENT_ERROR);
        commentInfos.forEach(e -> {
            System.out.println("评论信息:" + e);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(e.getUserId());
            UserInfo userInfo1 = userServiceImp.searchUser(userInfo); //查询子用户的信息
            e.setUserInfos(userInfo1); //放入
            String parentUserId = e.getParentUserId();
            //如果某个评论的父id不为它的根评论,那就是子评论
            if (!parentUserId.equals(commentInfo.getUserId())) {
                userInfo.setUserId(parentUserId);
                UserInfo userInfo2 = userServiceImp.searchUser(userInfo);
                e.setParentInfos(userInfo2);//父用户的信息
            }

        });

        return commentInfos;
    }


    @Override
    public List<CommentInfo> getAllComments(CommentInfo commentInfo) {
        return mapper.queryCommentByDishIdAll(commentInfo);
    }


    /**
     * 插入评论
     *
     * @param commentInfo 评论信息
     * @throws AppException 异常
     */
    @Override
    public void insertComment(CommentInfo commentInfo) throws AppException {
        try {
            mapper.insertComment(commentInfo);//如果报错 报我自己的提示
        } catch (Exception e) {
            throw new AppException(ResultEnum.INSERT_COMMENT_ERROR);
        }
    }


}
