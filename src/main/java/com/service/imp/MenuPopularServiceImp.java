package com.service.imp;

import com.dao.IMenuPopularInfoMapper;
import com.entity.DishInfo;
import com.entity.MenuPopularInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.service.MenuPopularService;
import com.utils.ResultEnum;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:18
 * @slogan Just do it continually
 */
public class MenuPopularServiceImp implements MenuPopularService {
    private static final MapperProxy<IMenuPopularInfoMapper> iMenuPopularInfoMapperMapperProxy = new MapperProxy<>();
    private static final IMenuPopularInfoMapper mapper =
            iMenuPopularInfoMapperMapperProxy.getMapper(IMenuPopularInfoMapper.class);

    @Override
    public void giveLike(MenuPopularInfo menuPopularInfo) {
        mapper.giveLike(menuPopularInfo);
    }

    @Override
    public void unLike(MenuPopularInfo menuPopularInfo) {
        mapper.unLike(menuPopularInfo);
    }

    @Override
    public void collect(MenuPopularInfo menuPopularInfo) {
        mapper.collect(menuPopularInfo);
    }

    @Override
    public void unCollect(MenuPopularInfo menuPopularInfo) {
        mapper.collect(menuPopularInfo);
    }

    @Override
    public void insertDish(MenuPopularInfo menuPopularInfo) {
        mapper.insertDish(menuPopularInfo);
    }

    @Override
    public List<MenuPopularInfo>  selectLikeAndCollectNumber(MenuPopularInfo menuPopularInfo) throws AppException {
        List<MenuPopularInfo> menuPopularInfos = mapper.selectLikeAndCollectNumber(menuPopularInfo);
        if(menuPopularInfos.isEmpty()){
            throw new AppException(ResultEnum.FIND_ERROR);
        }
        return menuPopularInfos;

    }
    @Override
    public void deleteMenuPopular(DishInfo dishInfo) {
        mapper.deleteMenuPopular(dishInfo);
    }


//
    /*public static void main(String[] args) {
        MenuPopularInfo menuPopularInfo = new MenuPopularInfo();
        menuPopularInfo.setDishId("11111111");
        menuPopularInfo
    }*/


}
