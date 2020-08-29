package com.service.imp;


import com.dao.IDishInfoMapper;
import com.entity.DishInfo;
import com.entity.UserInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.service.DishService;


import com.utils.PageSqlUtil;
import com.utils.ResultEnum;


import java.util.List;


public class DishServiceImp implements DishService {
    private static final MapperProxy<IDishInfoMapper> iDishMapperMapperProxy = new MapperProxy<>();
    private static final IDishInfoMapper mapper = iDishMapperMapperProxy.getMapper(IDishInfoMapper.class);

    @Override
    public List<DishInfo> listTagDishInfoByPage(PageSqlUtil pageSqlUtil)throws AppException {
        List<DishInfo> dishInfos = mapper.listTagDishInfoByPage(pageSqlUtil);
        if (dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return dishInfos;
    }

    @Override
    public List<DishInfo> listTagDishInfoByPageSize(PageSqlUtil pageSqlUtil) throws AppException {
        List<DishInfo> dishInfos = mapper.listTagDishInfoByPageSize(pageSqlUtil);
        if (dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return dishInfos;
    }

    @Override
    public List<DishInfo> listTagDishInfoByPageAsc(PageSqlUtil pageSqlUtil) throws AppException {
        List<DishInfo> dishInfos = mapper.listTagDishInfoByPageAsc(pageSqlUtil);
        if (dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return dishInfos;
    }

    @Override
    public List<DishInfo> listTagDishInfoByPageSizeAsc(PageSqlUtil pageSqlUtil) throws AppException {
        List<DishInfo> dishInfos = mapper.listTagDishInfoByPageSizeAsc(pageSqlUtil);
        if (dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return dishInfos;
    }

    @Override
    public void logicDeleteDish(DishInfo dishInfo) throws AppException {
        Integer integer = mapper.logicDeleteDish(dishInfo);
        if(0==integer) {
            throw new AppException(ResultEnum.DELETE_FAIL);
        }

    }

    /**
     * 插入菜谱
     * @param dishInfo 菜谱信息
     * @throws AppException 异常
     */
    @Override
    public void dishInsert(DishInfo dishInfo) throws AppException {
        //System.out.println(dishInfo);
        Integer integer = mapper.insertDish(dishInfo);
        if(integer<1) {
            throw new AppException(ResultEnum.DISH_INSERT_FAIL);
        }
    }
    @Override
    public List<DishInfo> dishesSearch(PageSqlUtil page) throws AppException {
        List<DishInfo> dishInfos = mapper.dishesSearch(page);
        if(dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return  dishInfos;
    }

    @Override
    public List<DishInfo> dishesSearchTotalPage(PageSqlUtil page) throws AppException {
        List<DishInfo> dishInfos = mapper.dishesSearchTotalPage(page);
        if(dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return  dishInfos;
    }

    /**
     * 根据dish——id查询
     * @param dishInfo 菜谱信息
     * @return 返回
     * @throws AppException 异常
     */
    @Override
    public List<DishInfo> queryDishById(DishInfo dishInfo) throws AppException {
        List<DishInfo> dishInfos = mapper.queryDishById(dishInfo);
        if(dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return dishInfos;
    }




    @Override
    public List<DishInfo> queryDishByUserId(UserInfo userInfo) throws AppException {
        List<DishInfo> dishInfos = mapper.queryDishByUserId(userInfo);
        if(dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return  dishInfos;
    }

    @Override
    public List<DishInfo> queryDishByUserIdPage(PageSqlUtil page) throws AppException {
        List<DishInfo> dishInfos = mapper.queryDishByUserIdPage(page);
        if(dishInfos.isEmpty()) {
            throw  new AppException(ResultEnum.NONE_DISH);
        }
        return  dishInfos;
    }


    @Override public void dishUpdate(DishInfo dishInfo) throws AppException {
        Integer integer = mapper.updateDish(dishInfo);
        if(integer<1) {
            throw new AppException(ResultEnum.DISH_INSERT_FAIL);
        }

    }


//    //测试按照创建时间分页
//    public static void main(String[] args) {
//        DishServiceImp dishServiceImp = new DishServiceImp();
//        PageSqlUtil pageSqlUtil=new PageSqlUtil();
//        List<DishInfo> dishInfos = dishServiceImp.listDishInfoByTimePage(pageSqlUtil);
//        System.out.println(dishInfos);
//    }

    /*//测试Dao层的insert方法(成功)
    public static void main(String[] args) {
        DishInfo dishInfo = new DishInfo();
        dishInfo.setDishId("102222221");
        dishInfo.setUserId("21078315190");
        dishInfo.setTagId("2000");
        dishInfo.setDishName("西红柿炒蛋");
        dishInfo.setDishIntro("好吃的一米");
        dishInfo.setDishText("独家秘方,绝对香嗷");
        Integer integer = mapper.insertDish(dishInfo);
        System.out.println(integer);


    }*/


    //测试Dao层的update方法(成功)
//    public static void main(String[] args) {
//        DishInfo dishInfo = new DishInfo();
//        dishInfo.setDishId("102222221");
//        dishInfo.setUserId("21078315190");
//        dishInfo.setTagId("2000");
//        dishInfo.setDishName("西红柿炒蛋");
//        dishInfo.setDishIntro("好吃的二米");
//        dishInfo.setDishText("偷来的独家秘方!");
//        dishInfo.setDishCreateTime(new Date());
//        dishInfo.setDishUpdateTime(new Date());
//        dishInfo.setDishDelete(0);
//        Integer integer = mapper.updateDish(dishInfo);
//        System.out.println(integer);
//    }



    /*//根据dishId和userId查询指定菜谱菜谱(成功)
    public static void main(String[] args) {
        DishInfo dishInfo = new DishInfo();
        dishInfo.setUserId("21078315190");
        dishInfo.setDishId("102222222");
        List<DishInfo> dishInfos = mapper.queryDishById(dishInfo);
        System.out.println(dishInfos);
    }*/

    /*//查询所有的菜谱(成功)
    public static void main(String[] args) {
        List<DishInfo> dishInfos = mapper.queryAllDishInfo();
        for (DishInfo dishInfo : dishInfos) {
            System.out.println(dishInfo);
        }
    }*/

    //根据菜谱id和用户id删除菜谱(成功)
    /*public static void main(String[] args) {
        DishInfo dishInfo = new DishInfo();
        dishInfo.setDishId("102222222");
        dishInfo.setUserId("21078315190");
        Integer integer = mapper.deleteDish(dishInfo);
        System.out.println(integer);
    }*/
}


