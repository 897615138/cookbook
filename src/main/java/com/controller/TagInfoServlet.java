package com.controller;

import com.entity.TagInfo;
import com.exception.AppException;
import com.service.imp.TagServiceImp;
import com.utils.BeanUtil;
import com.utils.JSONUtil;
import com.utils.ResultEntity;
import com.utils.ResultEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/tags/list", "/tags/list/byid"})
public class TagInfoServlet extends HttpServlet {
    private TagServiceImp tagServiceImp;


    @Override
    public void init() {
        tagServiceImp = new TagServiceImp();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String servletPath = req.getServletPath();
        System.out.println(servletPath);
        switch (servletPath) {
            case "/tags/list":
                tagsList(resp);
                break;
            case "/tags/list/byid":
                tagsListById(req, resp);
                break;

        }


    }

    private void tagsListById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TagInfo tag = BeanUtil.parseRequest(req, TagInfo.class);
        System.out.println(tag);
        ResultEntity resultEntity;
        TagInfo tagInfo;
        try {
            tagInfo = tagServiceImp.queryTag(tag);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, tagInfo);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
            e.printStackTrace();
        }
        System.out.println(resultEntity);
        JSONUtil.write(resultEntity, resp);

    }

    /**
     * 查询出所有的tag信息
     *
     * @param resp 响应
     */
    private void tagsList(HttpServletResponse resp) throws IOException {
        System.out.println("进入tagList");
        List<TagInfo> tagInfos;
        ResultEntity resultEntity;
        try {
            tagInfos = tagServiceImp.queryAllTag();
            System.out.println(tagInfos);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, tagInfos);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
            e.printStackTrace();
        }
        JSONUtil.write(resultEntity, resp);
    }


}
