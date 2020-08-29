package com.service.imp;

import com.dao.IMenuDishInfoMapper;
import com.entity.MenuDishInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.service.MenuDishService;
import com.utils.PageSqlUtil;
import com.utils.ResultEnum;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 16:15
 * @slogan Just do it continually
 */
public class MenuDishServiceImp implements MenuDishService {
    private static final MapperProxy<IMenuDishInfoMapper> iMenuDishInfoMapperMapperProxy = new MapperProxy<>();
    private static final IMenuDishInfoMapper mapper =
            iMenuDishInfoMapperMapperProxy.getMapper(IMenuDishInfoMapper.class);

    @Override
    public List<MenuDishInfo> getMenuContentById(PageSqlUtil pageSqlUtil) throws AppException {
        //根据menu_id查询出分页后的dish信息
        List<MenuDishInfo> menuContentById = mapper.getMenuContentById(pageSqlUtil);
        System.out.println(menuContentById);
        if (menuContentById.isEmpty()) {
            throw new AppException(ResultEnum.NONE_DISH);
        }
        return menuContentById;
    }

    @Override
    public List<MenuDishInfo> getMenuContentByIdTotalPage(PageSqlUtil pageSqlUtil) throws AppException {
        List<MenuDishInfo> menuContentByIdTotalPage = mapper.getMenuContentByIdTotalPage(pageSqlUtil);
        if(menuContentByIdTotalPage.isEmpty()){
            throw new AppException(ResultEnum.NONE_DISH);
        }
        return menuContentByIdTotalPage;
    }

    @Override
    public Boolean menuDishCollect(MenuDishInfo menuDishInfo) throws AppException {
        Integer integer = mapper.menuDishCollect(menuDishInfo);
        if (integer == 0) throw new AppException(ResultEnum.COLLECT_FAIL);
        return true;
    }

    /**
     * 根据收藏夹id和菜谱id删除 指定菜谱
     * @param menuDishInfo
     */
    @Override public void deleteMenuDish(MenuDishInfo menuDishInfo) throws AppException {
        Integer integer = mapper.deleteMenuDish(menuDishInfo);
        if(integer==0){
            throw new AppException(ResultEnum.SYS_MISTAKE);
        }
    }


    ////根据根据收藏夹id查询所有的菜谱id(成功)
    /*public static void main(String[] args) {
        MenuDishInfo menuDishInfo = new MenuDishInfo();
        menuDishInfo.setMenuId("1111111111");
        List<MenuDishInfo> menuDishInfos = mapper.queryAllDishId(menuDishInfo);
        for (MenuDishInfo dishInfo : menuDishInfos) {
            System.out.println(dishInfo);
        }
    }*/

    //增加菜谱id(成功)
    /*public static void main(String[] args) {
        MenuDishInfo menuDishInfo = new MenuDishInfo();
        menuDishInfo.setDishId("222222");
        menuDishInfo.setMenuId("1111111");
        Integer integer = mapper.increaseDishId(menuDishInfo);
        System.out.println(integer);
    }
*/


    //删除菜谱(成功)
    /*public static void main(String[] args) {
        MenuDishInfo menuDishInfo = new MenuDishInfo();
        menuDishInfo.setDishId("222222");
        Integer integer = mapper.deleteDishId(menuDishInfo);
        System.out.println(integer);
    }*/
}
