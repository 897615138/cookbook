package com.service.imp;

import com.dao.IPicturesInfoMapper;
import com.entity.DishInfo;
import com.entity.PicturesInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.service.PicturesService;
import com.utils.ResultEnum;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:18
 * @slogan Just do it continually
 */
public class PicturesServiceImp implements PicturesService {
    private static final MapperProxy<IPicturesInfoMapper> iPicturesInfoMapperMapperProxy = new MapperProxy<>();
    private static final IPicturesInfoMapper mapper =
            iPicturesInfoMapperMapperProxy.getMapper(IPicturesInfoMapper.class);

    /**
     * 查dish_id下的所有图片
     *
     * @param dishInfo 菜谱信息
     * @return 返回
     */
    @Override
    public List<PicturesInfo> queryAllPictures(DishInfo dishInfo) throws AppException {
        List<PicturesInfo> picturesInfos = mapper.queryAllPictures(dishInfo);
        if (picturesInfos.isEmpty()) {
            throw new AppException(ResultEnum.FIND_ERROR);
        }
        return picturesInfos;
    }

    @Override
    public void increasePictures(PicturesInfo picturesInfo) {
        mapper.increasePictures(picturesInfo);
    }


    //根据菜谱id查询图片(成功)
    /*public static void main(String[] args) {
        PicturesInfo picturesInfo = new PicturesInfo();
        picturesInfo.setDishId("1234567890");
        List<PicturesInfo> picturesInfos = mapper.queryAllPictures(picturesInfo);
        for (PicturesInfo info : picturesInfos) {
            System.out.println(info);
        }
    }*/

    //插入图片id和图片地址(成功)
    /*public static void main(String[] args) {
        PicturesInfo picturesInfo = new PicturesInfo();
        picturesInfo.setDishId("01234567890");
        picturesInfo.setPicAddress("6666699999");
        Integer integer = mapper.increasePictures(picturesInfo);
        System.out.println(integer);
    }*/


    //删除图片(成功)
   /* public static void main(String[] args) {
        PicturesInfo picturesInfo = new PicturesInfo();
        picturesInfo.setDishId("01234567890");
        picturesInfo.setPicAddress("6666699999");
        Integer integer = mapper.deletePictures(picturesInfo);
        System.out.println(integer);
    }*/
}
