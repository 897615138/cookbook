package com.service;

import com.entity.DishInfo;
import com.entity.UserInfo;
import com.exception.AppException;
import com.utils.PageSqlUtil;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 9:27
 * @slogan Just do it continually
 */
public interface DishService {



    /**
     * 插入菜谱
     *
     * @param dishInfo 菜谱信息
     * @throws AppException 异常
     */
    void dishInsert(DishInfo dishInfo) throws AppException;

    List<DishInfo> dishesSearch(PageSqlUtil page) throws AppException;
    List<DishInfo> dishesSearchTotalPage(PageSqlUtil page) throws AppException;


    List<DishInfo> queryDishById(DishInfo dishInfo) throws AppException;

    List<DishInfo> queryDishByUserId(UserInfo userInfo) throws AppException;

    List<DishInfo> queryDishByUserIdPage(PageSqlUtil page) throws AppException;


    List<DishInfo> listTagDishInfoByPage(PageSqlUtil pageSqlUtil) throws AppException;
    List<DishInfo> listTagDishInfoByPageSize(PageSqlUtil pageSqlUtil) throws AppException;
    List<DishInfo> listTagDishInfoByPageAsc(PageSqlUtil pageSqlUtil) throws AppException;
    List<DishInfo> listTagDishInfoByPageSizeAsc(PageSqlUtil pageSqlUtil) throws AppException;

    void logicDeleteDish(DishInfo dishInfo) throws AppException;
    void dishUpdate(DishInfo dishInfo) throws AppException;

}
