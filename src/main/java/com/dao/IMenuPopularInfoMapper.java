package com.dao;

import com.entity.DishInfo;
import com.entity.MenuPopularInfo;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/6 23:58
 * @slogan Just do it continually
 */
public interface IMenuPopularInfoMapper {


    //根据菜谱id查询
    List<MenuPopularInfo> queryAllDish(MenuPopularInfo menuPopularInfo);

    //点赞
    void giveLike(MenuPopularInfo menuPopularInfo);

    //取消点赞
    void unLike(MenuPopularInfo menuPopularInfo);

    void collect(MenuPopularInfo menuPopularInfo);

    void uncollected(MenuPopularInfo menuPopularInfo);

    void insertDish(MenuPopularInfo menuPopularInfo);

    List<MenuPopularInfo> selectLikeAndCollectNumber(MenuPopularInfo menuPopularInfo);

    void deleteMenuPopular(DishInfo dishInfo);

}
