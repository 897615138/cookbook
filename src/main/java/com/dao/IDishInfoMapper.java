package com.dao;

import com.entity.DishInfo;
import com.entity.UserInfo;
import com.utils.PageSqlUtil;

import java.util.List;

/**
 * @author zp
 * @create 2020-07-28-21:57
 */
public interface IDishInfoMapper {

    /**
     * 菜谱所需要的信息
     * 增加菜谱
     *
     * @param dish 菜谱信息
     */
    Integer insertDish(DishInfo dish);

    /**
     * 根据dish_id和user_id删除菜谱
     *
     * @param dish 菜谱信息
     */
    void deleteDish(DishInfo dish);

    /**
     * 根据dishId和userId更新菜谱
     *
     * @param dish 作为更新菜谱的依据
     * @return
     */
    Integer updateDish(DishInfo dish);


    /**
     * 根据dishId
     *
     * @param dish 菜谱信息
     * @return 菜谱List
     */
    List<DishInfo> queryDishById(DishInfo dish);


    /**
     * 获得全部菜谱
     */
    List<DishInfo> queryAllDishInfo();


//    /**
//     * 根据信息排序查询出菜谱
//     */
//    List<DishInfo> queryDishSortByInfo();


    /**
     * 模糊查询出对应的菜谱
     *
     * @param page 分页
     * @return 返回
     */
    List<DishInfo> dishesSearch(PageSqlUtil page);
    List<DishInfo> dishesSearchTotalPage(PageSqlUtil page);

    List<DishInfo> queryDishByUserId(UserInfo userInfo);

    List<DishInfo> queryDishByUserIdPage(PageSqlUtil pageSqlUtil);


    List<DishInfo> listTagDishInfoByPage(PageSqlUtil pageSqlUtil);
    List<DishInfo> listTagDishInfoByPageSize(PageSqlUtil pageSqlUtil);
    List<DishInfo> listTagDishInfoByPageAsc(PageSqlUtil pageSqlUtil);
    List<DishInfo>listTagDishInfoByPageSizeAsc(PageSqlUtil pageSqlUtil);
    Integer logicDeleteDish(DishInfo dishInfo);
}
