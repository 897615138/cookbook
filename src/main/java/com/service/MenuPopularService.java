package com.service;

import com.entity.DishInfo;
import com.entity.MenuPopularInfo;
import com.exception.AppException;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:16
 * @slogan Just do it continually
 */
public interface MenuPopularService {

    void giveLike(MenuPopularInfo menuPopularInfo);

    void unLike(MenuPopularInfo menuPopularInfo);

    void collect(MenuPopularInfo menuPopularInfo);

    void unCollect(MenuPopularInfo menuPopularInfo);

    void insertDish(MenuPopularInfo menuPopularInfo);

    List<MenuPopularInfo> selectLikeAndCollectNumber(MenuPopularInfo menuPopularInfo) throws AppException;

    void deleteMenuPopular(DishInfo dishInfo);
}
