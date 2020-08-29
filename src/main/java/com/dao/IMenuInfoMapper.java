package com.dao;

import com.entity.MenuInfo;

import java.util.List;

/**
 * @description: Menu bean
 * @author: JIll Wang
 * @create: 2020-07-31 08:15
 **/
public interface IMenuInfoMapper {
    /**
     * 查询所有收藏夹 wj
     *
     * @return List
     */
    List<MenuInfo> queryAllMenu();

    /**
     * 通过user_id查询标签
     *
     * @param menu 收藏夹
     * @return List
     */
    List<MenuInfo> queryMenuByUser(MenuInfo menu);

    //通过收藏夹id和用户Id删除收藏夹
    Integer deleteMenuById(MenuInfo menuInfo);


    /**
     * 创建收藏夹(通过用户id和菜谱名称)
     * @param menuInfo
     * @return
     */
    Integer createMenuByIdAndName(MenuInfo menuInfo);

    /**
     * 修改收藏夹名称
     * @param menuInfo
     * @return
     */
    Integer updateMenuTime(MenuInfo menuInfo);

    /**
     * 通过menu_id获取menu
     * @param menuInfo
     * @return
     */
    List<MenuInfo> getMenuById(MenuInfo menuInfo);
}
