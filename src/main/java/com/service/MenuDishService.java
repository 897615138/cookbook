package com.service;

import com.entity.MenuDishInfo;
import com.exception.AppException;
import com.utils.PageSqlUtil;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:13
 * @slogan Just do it continually
 */
public interface MenuDishService {


    List<MenuDishInfo> getMenuContentById(PageSqlUtil pageSqlUtil) throws AppException;
    List<MenuDishInfo> getMenuContentByIdTotalPage(PageSqlUtil pageSqlUtil) throws AppException;

    //进行插入的判断,没有就插入,有就无法插入
    Boolean menuDishCollect(MenuDishInfo menuDishInfo) throws AppException;

    //根据收藏夹id和菜谱id删除指定菜谱
    void deleteMenuDish(MenuDishInfo menuDishInfo) throws AppException;

}
