package com.service;

import com.entity.MenuInfo;
import com.exception.AppException;

import java.awt.*;
import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 15:47
 * @slogan Just do it continually
 */
public interface MenuService {


    //创建收藏夹
    void createMenu(MenuInfo menuInfo) throws AppException;


    //根据userId查询该用用户的所有收藏夹
    List<MenuInfo> showMenus(MenuInfo menuInfo);

    //根据传回来的menu_id和user_id来删除指定收藏夹
    void deleteMenu(MenuInfo menuInfo) throws AppException;

    void updateMenuName(MenuInfo menuInfo) throws AppException;

    MenuInfo getMenuById(MenuInfo menuInfo) throws AppException;
}
