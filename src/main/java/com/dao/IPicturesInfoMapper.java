package com.dao;

import com.entity.DishInfo;
import com.entity.PicturesInfo;

import java.util.List;

/**
 * @author WonderFour
 * @date 2020/8/6 23:57
 */
public interface IPicturesInfoMapper {

    /**
     * 根据菜谱id查询图片
     *
     * @param dishInfo 菜谱信息
     * @return 返回
     */
    List<PicturesInfo> queryAllPictures(DishInfo dishInfo);


    /**
     * 插入菜谱id-图片地址
     *
     * @param picturesInfo 图片信息
     */
    Integer increasePictures(PicturesInfo picturesInfo);


    /**
     * 删除
     *
     * @param picturesInfo
     */
    Integer deletePictures(PicturesInfo picturesInfo);
}
