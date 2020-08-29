package com.dao;

import com.entity.MenuDishInfo;
import com.utils.PageSqlUtil;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/6 23:57
 * @slogan Just do it continually
 */
public interface IMenuDishInfoMapper {

    //根据根据收藏夹id查询所有的菜谱id
    List<MenuDishInfo> queryAllDishId(MenuDishInfo menuDishInfo);


    //增加菜谱id
    Integer increaseDishId(MenuDishInfo menuDishInfo);

    //删除菜谱id
    Integer deleteDishId(MenuDishInfo menuDishInfo);


    //根据menu_id查出dish_id
    List<MenuDishInfo> getMenuContentById(PageSqlUtil pageSqlUtil);
    List<MenuDishInfo> getMenuContentByIdTotalPage(PageSqlUtil pageSqlUtil);


    //判断有没有收藏,没有收藏就插入
    Integer menuDishCollect(MenuDishInfo menuDishInfo);

    //根据收藏夹id和菜谱id删除指定菜谱
    Integer deleteMenuDish(MenuDishInfo menuDishInfo);

}
