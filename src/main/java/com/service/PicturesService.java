package com.service;

import com.entity.DishInfo;
import com.entity.PicturesInfo;
import com.exception.AppException;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:17
 * @slogan Just do it continually
 */
public interface PicturesService {
    List<PicturesInfo> queryAllPictures(DishInfo dishInfo) throws AppException;

    void increasePictures(PicturesInfo picturesInfo);
}
