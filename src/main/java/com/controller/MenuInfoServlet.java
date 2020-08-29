package com.controller;

import com.entity.MenuDishInfo;
import com.entity.MenuInfo;
import com.entity.UserInfo;
import com.exception.AppException;
import com.service.imp.MenuDishServiceImp;
import com.service.imp.MenuServiceImp;
import com.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author WonderFour
 * @Date 2020/8/10 11:10
 * @slogan Just do it continually
 */
@WebServlet({ "/menus/insert", "/menus/getLoginUserMenus", "/menus/delete",
        "/menus/update/name", "/menus/id","/menus/deleteMenuDish"})
public class MenuInfoServlet extends HttpServlet {

    private MenuServiceImp menuController;
    private MenuDishServiceImp menuDishServiceImp;

    @Override
    public void init() {
        menuController = new MenuServiceImp();
        menuDishServiceImp = new MenuDishServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String servletPath = req.getServletPath();
        System.out.println(servletPath);
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        switch (servletPath) {
            case "/menus/insert":
                createMenu(req, resp);
                break;
            case "/menus/getLoginUserMenus":
                getLoginUserMenus(req, resp);
                break;
            case "/menus/delete":
                deleteMenu(req, resp);
                break;

            case "/menus/update/name":
                updateMenuName(req, resp);
                break;
            case "/menus/id":
                findMenu(req, resp);
                break;
            case "/menus/deleteMenuDish":
                deleteMenuDish(req,resp);
                break;
        }
    }

    //删除指定收藏夹里面的指定菜谱
    private void deleteMenuDish(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MenuDishInfo menuDishInfo = BeanUtil.parseRequest(req, MenuDishInfo.class);
        ResultEntity resultEntity;
        try{
            menuDishServiceImp.deleteMenuDish(menuDishInfo);
            resultEntity=ResultEntity.SUCCESS(ResultEnum.DELETE_SUCCESS);
        }catch (AppException e){
            resultEntity=ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity,resp);
    }

    private void getMenuContentByIdPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Page page = BeanUtil.parseRequest(req, Page.class);
        PageSqlUtil instance = PageSqlUtil.getInstance(page);
        ResultEntity resultEntity = null;
        try {
            List<MenuDishInfo> menuContentByIdTotalPage = menuDishServiceImp.getMenuContentByIdTotalPage(instance);
            page.setTotalSize(menuContentByIdTotalPage.size());
            page.setTotalPage(page.getTotalPage());
            resultEntity=ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS,page);
        } catch (AppException e) {
            resultEntity=ResultEntity.ERROR(e);
            e.printStackTrace();
        }
        JSONUtil.write(resultEntity,resp);
    }





    //创建收藏夹(限定不能大于六个)
    private void createMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MenuInfo menuInfo = BeanUtil.parseRequest(req, MenuInfo.class);
        UserInfo user = (UserInfo) req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg());
        //设置userId为登录用户的id
        menuInfo.setUserId(user.getUserId());
        //设置UUID(等待修改)
        menuInfo.setMenuId(UUID.randomUUID().toString().replaceAll("-", ""));
        /*System.out.println(menuInfo);*/
        ResultEntity resultEntity;
        try {
            menuController.createMenu(menuInfo);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.INSERT_SUCCESS);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);
    }

    /**
     * 根据用户id查询所有的收藏夹
     *
     * @param req  请求
     * @param resp 响应
     */
    private void getLoginUserMenus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MenuInfo menuInfo = BeanUtil.parseRequest(req, MenuInfo.class);
        UserInfo user = (UserInfo) req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg());
        menuInfo.setUserId(user.getUserId());
        List<MenuInfo> menuInfos = menuController.showMenus(menuInfo);
        ResultEntity<MenuInfo> success = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, menuInfos);
        JSONUtil.write(success, resp);

    }

    /**
     * 删除收藏夹
     *
     * @param req  请求
     * @param resp 响应
     */
    private void deleteMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MenuInfo menuInfo = BeanUtil.parseRequest(req, MenuInfo.class);
        HttpSession session = req.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(ConstEnum.LOGIN_USER.getMsg());
        menuInfo.setUserId(userInfo.getUserId());
        ResultEntity entity;
        try {
            menuController.deleteMenu(menuInfo);
            entity = ResultEntity.SUCCESS(ResultEnum.DELETE_SUCCESS);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }

    private void findMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MenuInfo menuInfo = BeanUtil.parseRequest(req, MenuInfo.class);
        ResultEntity resultEntity;
        try {
            MenuInfo menuById = menuController.getMenuById(menuInfo);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, menuById);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);
    }

    /**
     * 根据收藏夹id和待修改的收藏夹名称修改
     *
     * @param req  请求
     * @param resp 响应
     */
    private void updateMenuName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MenuInfo menuInfo = BeanUtil.parseRequest(req, MenuInfo.class);
        ResultEntity entity;
        try {
            menuController.updateMenuName(menuInfo);
            entity = ResultEntity.SUCCESS(ResultEnum.UPDATEMENUNAME_SUCCESS);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }
}
