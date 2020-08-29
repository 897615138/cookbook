package com.controller;


import com.entity.CommentInfo;
import com.exception.AppException;
import com.service.CommentService;
import com.service.imp.CommentServiceImp;
import com.utils.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/3 6:26 下午
 */
@WebServlet({"/comments/get/rootComments", "/comments/insert", "/comments/get/childComments"})
public class CommentInfoServlet extends HttpServlet {
    private final CommentService commentController = new CommentServiceImp();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //初始化各个参数 然后 用switch case 进行跳转
        String servletPath = req.getServletPath();
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json"); //与前面相对应
        //拿到到底是什么样的请求 然后在进行分发
        switch (servletPath) {
            case "/comments/get/rootComments":
                getRootComments(req, resp);
                break;
            case "/comments/insert":
                insertComments(req, resp);
                break;
            case "/comments/get/childComments":
                getChildComments(req, resp);
                break;

        }
    }

    private void getChildComments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CommentInfo object = BeanUtil.parseRequest(req, CommentInfo.class);
        System.out.println(object);
        try {
            //拿到页面大小
            Integer pageSize = object.getPageSize();
            //拿到当前页
            Integer currPage = object.getCurrPage();
            //拿到分页后的子评论list
            List<CommentInfo> childComments = commentController.getChildComments(object);
            //总的根评论数
            object.setModPage(0);
            object.setPageSize(Integer.MAX_VALUE);
            List<CommentInfo> childCommentsCount = commentController.getChildComments(object);
            Integer totalSize = childCommentsCount.size();
            Page page = new Page();
            page.setCurrPage(currPage);
            page.setPageSize(pageSize);
            page.setTotalSize(totalSize);
            int totalPage = page.getTotalPage();
            System.out.println("总页数" + totalPage);
            page.setTotalPage(totalPage);
            System.out.println("目前的页面是:" + page);
            //设置总页数
            JSONUtil.write(ResultEntity.SUCCESS(ResultEnum.COMMENT_SUCCESS, childComments, page), resp);
        } catch (AppException appException) {
            JSONUtil.write(ResultEntity.ERROR(appException), resp);
        }
    }


    /**
     * 发布评论
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException 异常
     */
    private void insertComments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CommentInfo object = BeanUtil.parseRequest(req, CommentInfo.class);
        System.out.println("要插入的评论为:" + object);
        try {
            commentController.insertComment(object);
            JSONUtil.write(ResultEntity.SUCCESS(ResultEnum.INSERT_COMMENT_SUCCESS), resp);
        } catch (AppException appException) {
            JSONUtil.write(ResultEntity.ERROR(appException), resp);
        }
    }

    /**
     * 拿到评论
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException 异常
     */
    private void getRootComments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CommentInfo object = BeanUtil.parseRequest(req, CommentInfo.class);
        System.out.println(object);
        try {
            //拿到页面大小
            Integer pageSize = object.getPageSize();
            //拿到分页后的根评论list
            List<CommentInfo> rootComments = commentController.getRootComments(object);
            //总的根评论数
            object.setModPage(0);
            object.setPageSize(Integer.MAX_VALUE);
            List<CommentInfo> rootCommentsCount = commentController.getRootComments(object);
            Integer totalSize = rootCommentsCount.size();
            Page page = new Page();
            page.setPageSize(pageSize);
            page.setTotalSize(totalSize);
            int totalPage = page.getTotalPage();
            System.out.println("总页数" + totalPage);
            page.setTotalPage(totalPage);
            System.out.println("目前的页面是:" + page);
            //设置总页数
            JSONUtil.write(ResultEntity.SUCCESS(ResultEnum.COMMENT_SUCCESS, rootComments, page), resp);
        } catch (AppException appException) {
            JSONUtil.write(ResultEntity.ERROR(appException), resp);
        }
    }
}
