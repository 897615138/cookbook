package com.service.imp;

import com.dao.IMenuInfoMapper;
import com.entity.MenuInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.jdbc.parse.Result;
import com.service.MenuService;
import com.utils.ResultEnum;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 15:48
 * @slogan Just do it continually
 */
public class MenuServiceImp implements MenuService {
    private static final MapperProxy<IMenuInfoMapper> iMenuInfoMapperMapperProxy = new MapperProxy<>();
    private static final IMenuInfoMapper mapper = iMenuInfoMapperMapperProxy.getMapper(IMenuInfoMapper.class);



    /**
     *创建收藏夹
     * @param menuInfo
     */
    @Override
    public void createMenu(MenuInfo menuInfo) throws AppException {
        Integer menuByIdAndName = mapper.createMenuByIdAndName(menuInfo);
        if(menuByIdAndName!=1){
            throw new AppException(ResultEnum.FULL_MENU_ERROR);
        }
    }

    /**
     * 展示所有的收藏夹
     * @param menuInfo
     * @return
     */
    @Override
    public List<MenuInfo> showMenus(MenuInfo menuInfo) {
       return mapper.queryMenuByUser(menuInfo);

    }

    /**
     * 删除指定的收藏夹
     * @param menuInfo
     */
    @Override
    public void deleteMenu(MenuInfo menuInfo) throws AppException {
        Integer integer = mapper.deleteMenuById(menuInfo);
        if(integer!=1){
            throw new AppException(ResultEnum.SYS_MISTAKE);
        }
    }

    @Override
    public void updateMenuName(MenuInfo menuInfo) throws AppException {
        Integer integer = mapper.updateMenuTime(menuInfo);
        if(integer!=1){
            throw new AppException(ResultEnum.SYS_MISTAKE);
        }
    }

    @Override
    public MenuInfo getMenuById(MenuInfo menuInfo) throws AppException {
        List<MenuInfo> menuById = mapper.getMenuById(menuInfo);
        if(menuById.isEmpty()){
            throw  new AppException(ResultEnum.SYS_MISTAKE);
        }
        return menuById.get(0);
    }














    /*//查询所有收藏夹(成功)
    public static void main(String[] args) {
        List<MenuInfo> menuInfos = mapper.queryAllMenu();
        for (MenuInfo menuInfo : menuInfos) {
            System.out.println(menuInfo);
        }
    }*/


    //根据用户id查询对应的收藏夹(成功)
   /* public static void main(String[] args) {
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setUserId("222222222");
        List<MenuInfo> menuInfos = mapper.queryMenuByUser(menuInfo);
        System.out.println(menuInfos);
    }*/


   //删除收藏夹(逻辑删除)
   /*public static void main(String[] args) {
       MenuInfo menuInfo = new MenuInfo();
       menuInfo.setMenuId("111111");
       Integer integer = mapper.deleteMenuById(menuInfo);
       System.out.println(integer);
   }*/

}
