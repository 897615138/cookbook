package com.controller;

import com.alibaba.fastjson.JSON;
import com.entity.*;
import com.etc.LikeSystem;
import com.exception.AppException;
import com.service.imp.*;
import com.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet({"/menus/getMenuContentById", "/dishes/delete", "/dishes/isLike", "/dishes/collect", "/dishes/like",
        "/dishes/sort"
        , "/dishes/dishesNumber", "/dishes/fuzzySearch", "/dishes/fuzzySearch/totalPage", "/dishes/insert",
        "/dishes/select/id", "/dishes/update"})
public class DishInfoServlet extends HttpServlet {

    private DishServiceImp dishServiceImp;
    private PicturesServiceImp picturesServiceImp;
    private MenuPopularServiceImp menuPopularServiceImp;
    private MenuDishServiceImp menuDishServiceImp;
    private UserServiceImp userServiceImp;


    @Override
    public void init() {
        dishServiceImp = new DishServiceImp();
        picturesServiceImp = new PicturesServiceImp();
        menuPopularServiceImp = new MenuPopularServiceImp();
        menuDishServiceImp = new MenuDishServiceImp();
        userServiceImp = new UserServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //自己测试
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String servletPath = req.getServletPath();
        System.out.println(servletPath);

        switch (servletPath) {
            case "/dishes/sort":
                dishesSort(req, resp);
                break;
            case "/dishes/insert":
                dishInsert(req, resp);
                break;
            case "/dishes/select/id":
                dishSelectId(req, resp);
                break;
            case "/dishes/fuzzySearch":
                dishesFuzzySearch(req, resp);
                break;
            case "/dishes/dishesNumber":
                dishesDishesNumber(req, resp);
                break;
            case "/dishes/like":
                dishesLike(req, resp);
                break;
            case "/dishes/isLike":
                dishesIsLike(req, resp);
                break;
            case "/dishes/collect":
                dishesCollect(req, resp);
                break;
            case "/dishes/delete":
                dishesDelete(req, resp);
                break;
            case "/menus/getMenuContentById":
                getMenuContentById(req, resp);
                break;
            case "/dishes/update":
                updateDish(req,resp);
                break;

        }
    }

    private void updateDish(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DishInfo dishInfo = PictureUtil.getPic2(req);
        ResultEntity resultEntity;
        try {
            dishInfo.setDishUpdateTime(new Date());
            dishServiceImp.dishUpdate(dishInfo);
            System.out.println("修改完毕");
            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS);
            //初始化化维护点赞与收藏的Map
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);
    }

    /**
     * 根据menu_id 获取对应的dish_info+图片
     *
     * @param req  请求
     * @param resp 响应
     */
    private void getMenuContentById(HttpServletRequest req, HttpServletResponse resp) {
        Page page = BeanUtil.parseRequest(req, Page.class);
        PageSqlUtil pageSql = PageSqlUtil.getInstance(page);
        ResultEntity resultEntity;
        try {
            List<MenuDishInfo> menuContentById =
                    menuDishServiceImp.getMenuContentById(pageSql);//根据menu_id+分页查询出dish_id信息 此时也是分页过的
            //需要出menu 下 的 dish_info
            List<MenuDishInfo> menuContentByIdTotalPage = menuDishServiceImp.getMenuContentByIdTotalPage(pageSql);
            page.setTotalSize(menuContentByIdTotalPage.size());
            page.setTotalPage(page.getTotalPage());
            try {
                dishesSelectMenu(resp, page, menuContentById);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
            try {
                JSONUtil.write(resultEntity, resp);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void dishesSelectMenu(HttpServletResponse resp, Page page,
                                  List<MenuDishInfo> menuContentById) throws IOException {
        //需求 根据menu_id 分页找出对应的菜谱
        //根据菜谱 找到图片
        //返回的是一个dishInfo
        ResultEntity resultEntity;
        //2.根据Id查询出DishInfo的list集合
        System.out.println(menuContentById);
        List<DishInfo> dishInfos = new ArrayList<>();
        menuContentById.forEach(e1 -> {
            page.setId(e1.getMenuId());
            DishInfo dishInfo = new DishInfo();
            DishInfo dishInfo1;
            dishInfo.setDishId(e1.getDishId());
            try {
                List<DishInfo> dishInfos1 = dishServiceImp.queryDishById(dishInfo);
                dishInfo1 = dishInfos1.get(0);
                dishInfos.add(dishInfo1);
            } catch (AppException e) {
                e.printStackTrace();
            }
        });
        //3.遍历list,查出每份dish的图片集合
        if (!dishInfos.isEmpty()) {
            selectLikeAndCollectNumber(dishInfos);
            resultEntity = getResultEntity(dishInfos);
            resultEntity.setPage(page);
            //4.返回结果
            JSONUtil.write(resultEntity, resp);
        }
    }

    private void dishesDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DishInfo dishInfo = BeanUtil.parseRequest(req, DishInfo.class);
        ResultEntity resultEntity;
        try {
            //文章的逻辑删除
            dishServiceImp.logicDeleteDish(dishInfo);
            //点赞map的删除
            LikeSystem.DishDelete(dishInfo.getDishId());
            //menu_popular 实际删除 没有逻辑删除,需要实际删除
            menuPopularServiceImp.deleteMenuPopular(dishInfo);

            resultEntity = ResultEntity.SUCCESS(ResultEnum.DELETE_SUCCESS);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
            e.printStackTrace();
        }
        JSONUtil.write(resultEntity, resp);
    }

    private void dishesIsLike(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String dishId = BeanUtil.parseRequest2(req, DishInfo.class).getDishId();
        String userId = BeanUtil.parseRequest2(req, UserInfo.class).getUserId();
        //点过赞的false
        Boolean islike = LikeSystem.isLike(dishId, userId);
        ResultEntity resultEntity;
        if (islike) {
            resultEntity = ResultEntity.SUCCESS(ResultEnum.DISH_UNLIKE);
        } else {
            resultEntity = ResultEntity.ERROR(ResultEnum.DISH_LIKE);
        }
        //已经点过赞 返回false  没有点过返回true
        JSONUtil.write(resultEntity, resp);

    }

    /**
     * 菜谱放到收藏夹
     *
     * @param req  请求
     * @param resp 响应
     */
    private void dishesCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String collectArr = req.getParameter("collectArr");
        //拿到meunDish数组
        List<MenuDishInfo> menuDishInfos = JSON.parseArray(collectArr, MenuDishInfo.class);
        System.out.println("需要的列表" + menuDishInfos);
//        //原子引用
//        AtomicReference<ResultEntity> resultEntity = new AtomicReference<>();
//         MenuDish
//        //如果插入成功了
//        menuDishInfos.forEach(e -> {
//        });
        ResultEntity resultEntity;
        MenuPopularInfo menuPopularInfo = new MenuPopularInfo();
        try {
            //循环添加菜谱入收藏夹
            for (MenuDishInfo menuDishInfo : menuDishInfos) {
                menuDishServiceImp.menuDishCollect(menuDishInfo);
                menuPopularInfo.setDishId(menuDishInfo.getDishId());
            }
            //收藏数+1
            menuPopularServiceImp.collect(menuPopularInfo);
            //查询收藏数
            List<MenuPopularInfo> menuPopularInfos = menuPopularServiceImp.selectLikeAndCollectNumber(menuPopularInfo);
            MenuPopularInfo searchResult = menuPopularInfos.get(0);
            Page page = new Page();
            page.setSortord(searchResult.getDishCollect());
            resultEntity = ResultEntity.SUCCESS(ResultEnum.COLLECT_SUCCESS, page);
        } catch (AppException appException) {
            resultEntity = ResultEntity.ERROR(appException);
        }
        JSONUtil.write(resultEntity, resp);
    }

    /**
     * 用户点赞
     *
     * @param req  请求
     * @param resp 响应
     */
    private void dishesLike(HttpServletRequest req, HttpServletResponse resp) {
        String dishId = BeanUtil.parseRequest2(req, DishInfo.class).getDishId();
        String userId = BeanUtil.parseRequest2(req, UserInfo.class).getUserId();
        Page page = new Page();
        MenuPopularInfo menuPopularInfo = new MenuPopularInfo();
        System.out.println(menuPopularInfo);
        menuPopularInfo.setDishId(dishId);
        //把userId 和dishId 放入到进行逻辑判断  是否这个人点赞了这个文章
        //false 是取消点赞  true是点赞
        Boolean islike = LikeSystem.LikeOpation(dishId, userId);
        //在数据库中根据需求进行  修改
        try {
            if (islike) {  //点赞  ,已经完成  但是需要点赞数+1
                menuPopularServiceImp.giveLike(menuPopularInfo);
                List<MenuPopularInfo> menuPopularInfos =
                        menuPopularServiceImp.selectLikeAndCollectNumber(menuPopularInfo);
                MenuPopularInfo menuPopularInfo1 = menuPopularInfos.get(0);
                page.setSortord(menuPopularInfo1.getDishLike());
            } else { //取消点赞   需要完成 点赞数-1
                menuPopularServiceImp.unLike(menuPopularInfo);
                List<MenuPopularInfo> menuPopularInfos =
                        menuPopularServiceImp.selectLikeAndCollectNumber(menuPopularInfo);
                MenuPopularInfo menuPopularInfo1 = menuPopularInfos.get(0);
                page.setSortord(menuPopularInfo1.getDishLike());
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        //返回成功给前端
        ResultEntity resultEntity = new ResultEntity();
        resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS, page);
        try {
            JSONUtil.write(resultEntity, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //主要是传回主页数
    private void dishesSortTotalPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Page page = BeanUtil.parseRequest(req, Page.class);
        PageSqlUtil instance = PageSqlUtil.getInstance(page);
        ResultEntity resultEntity;
        try {
            List<DishInfo> dishInfos = dishServiceImp.listTagDishInfoByPageSize(instance);
            page.setTotalSize(dishInfos.size());
            int totalPage = page.getTotalPage();
            page.setTotalPage(totalPage);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, page);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
            e.printStackTrace();
        }
        JSONUtil.write(resultEntity, resp);

    }

    private void dishesSort(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //不需要page的type 要sortord
        Page page = BeanUtil.parseRequest(req, Page.class);
        PageSqlUtil instance = PageSqlUtil.getInstance(page);
        ResultEntity resultEntity;
        try {
            if (page.getSortord() < 0) {
                List<DishInfo> dishInfos = dishServiceImp.listTagDishInfoByPage(instance);
                selectLikeAndCollectNumber(dishInfos);
                List<DishInfo> dishInfos1 = dishServiceImp.listTagDishInfoByPageSize(instance);
                resultEntity = getResultEntity(dishInfos);
                page.setTotalSize(dishInfos1.size());
                page.setTotalPage(page.getTotalPage());
            } else {
                List<DishInfo> dishInfos = dishServiceImp.listTagDishInfoByPageAsc(instance);
                selectLikeAndCollectNumber(dishInfos);
                List<DishInfo> dishInfos1 = dishServiceImp.listTagDishInfoByPageSizeAsc(instance);
                resultEntity = getResultEntity(dishInfos);
                page.setTotalSize(dishInfos1.size());
                page.setTotalPage(page.getTotalPage());
            }
            resultEntity.setPage(page);

        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
            e.printStackTrace();
        }
        JSONUtil.write(resultEntity, resp);
    }

    //查询每一个菜谱的点赞数
    private void selectLikeAndCollectNumber(List<DishInfo> dishInfos) {
        dishInfos.forEach(e1 -> {
            MenuPopularInfo menuPopularInfo = new MenuPopularInfo();
            menuPopularInfo.setDishId(e1.getDishId());
            try {
                List<MenuPopularInfo> menuPopularInfos =
                        menuPopularServiceImp.selectLikeAndCollectNumber(menuPopularInfo);
                e1.setLikeNumber(menuPopularInfos.get(0).getDishLike());
                e1.setCollectNumber(menuPopularInfos.get(0).getDishCollect());
            } catch (AppException e) {
                e.printStackTrace();
            }
        });
    }
//    private void dishesSelectMenu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        //需求 根据menu_id 分页找出对应的菜谱
//        //根据菜谱 找到图片
//        //返回的是一个dishInfo
//        System.out.println("进入转发");
//        ResultEntity resultEntity;
//        //2.根据Id查询出DishInfo的list集合
//        List<MenuDishInfo> menuContentById = (List<MenuDishInfo>) req.getAttribute(ConstEnum.FIND_DISHES.getMsg());
//        System.out.println(menuContentById);
//        List<DishInfo> dishInfos = new ArrayList<>();
//        menuContentById.forEach(e1 -> {
//            DishInfo dishInfo = new DishInfo();
//            dishInfo.setDishId(e1.getDishId());
//            try {
//                DishInfo dishInfo1 = dishServiceImp.queryDishById(dishInfo);
//                dishInfo.setDishName(dishInfo1.getDishName());
//            } catch (AppException e) {
//                e.printStackTrace();
//            }
//            dishInfos.add(dishInfo);
//        });
//        //3.遍历list,查出每份dish的图片集合
//        resultEntity = getResultEntity(dishInfos);
//        //4.返回结果
//        JSONUtil.write(resultEntity, resp);
//    }

    /**
     * 根据user_id查询信息
     * 主要信息为 有多少条菜谱 放入page对象的totalSize中
     *
     * @param req  请求
     * @param resp 响应
     */
    private void dishesDishesNumber(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Page page = BeanUtil.parseRequest(req, Page.class);
//        PageSqlUtil instance = PageSqlUtil.getInstance(page);
        ResultEntity resultEntity;
        UserInfo userInfo = new UserInfo();
        try {
            if (page.getId() == null) {
                HttpSession session = req.getSession();
                UserInfo attribute = (UserInfo) session.getAttribute(ConstEnum.LOGIN_USER.getMsg());
                userInfo.setUserId(attribute.getUserId());
            } else {
                userInfo.setUserId(String.valueOf(page.getId()));
            }
            page.setId(userInfo.getUserId());
            PageSqlUtil instance = PageSqlUtil.getInstance(page);
            List<DishInfo> dishInfos = dishServiceImp.queryDishByUserIdPage(instance);//查出 id 对应的文章
            List<DishInfo> dishInfos1 = dishServiceImp.queryDishByUserId(userInfo);
            selectLikeAndCollectNumber(dishInfos);
            page.setTotalSize(dishInfos1.size());
            page.setTotalPage(page.getTotalPage());
            resultEntity = getResultEntity(dishInfos);
            resultEntity.setPage(page);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);
    }

    /**
     * 根据输入的菜谱名称部分模糊查询
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException 异常
     */
    private void dishesFuzzySearch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Page page = BeanUtil.parseRequest(req, Page.class);
        System.out.println(page);
        PageSqlUtil instance = PageSqlUtil.getInstance(page);
        System.out.println(instance);
        ResultEntity resultEntity;
        try {
            List<DishInfo> dishInfos = dishServiceImp.dishesSearch(instance);
            System.out.println("查收" + dishInfos);
            List<DishInfo> dishInfos1 = dishServiceImp.dishesSearchTotalPage(instance);
            page.setTotalSize(dishInfos1.size());//总页数的写回
            page.setTotalPage(page.getTotalPage());
            System.out.println(page);
            resultEntity = getResultEntity(dishInfos);
            resultEntity.setPage(page);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);

    }
//    private void dishesFuzzySearchTotalPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Page page = BeanUtil.parseRequest(req, Page.class);
//        PageSqlUtil instance = PageSqlUtil.getInstance(page);
//        ResultEntity resultEntity;
//        try {
//            List<DishInfo> dishInfos = dishServiceImp.queryDishByUserIdPage(instance);
//            page.setTotalSize(dishInfos.size());
//            page.setTotalPage(page.getTotalPage());
//            resultEntity=ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS,page);
//        } catch (AppException e) {
//            resultEntity=ResultEntity.ERROR(e);
//            e.printStackTrace();
//        }
//        JSONUtil.write(resultEntity,resp);
//    }

    /**
     * 根据dish_id查询对应的dish_info信息
     *
     * @param req  请求
     * @param resp 响应
     */
    private void dishSelectId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //需要返回用户的所有信息
        DishInfo dish = BeanUtil.parseRequest(req, DishInfo.class);
        ResultEntity resultEntity;
        try {
            List<DishInfo> dishInfos = dishServiceImp.queryDishById(dish);
            selectLikeAndCollectNumber(dishInfos);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(dishInfos.get(0).getUserId());
            UserInfo userInfo1 = userServiceImp.searchUser(userInfo);
            dishInfos.get(0).setUserInfo(userInfo1);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, dishInfos.get(0));
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);

    }


    /**
     * 发布文章
     * 同时创建维护的Map
     *
     * @param req  请求
     * @param resp 响应
     */
    private void dishInsert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("插入菜谱");
        DishInfo dishInfo = PictureUtil.getPic(req);
        ResultEntity resultEntity;
        try {
            dishServiceImp.dishInsert(dishInfo);
            System.out.println("插入完毕");
            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS);
            //初始化化维护点赞与收藏的Map
            LikeSystem.initDish(dishInfo.getDishId());
//            LikeAndCollectSystem.initMap(dishInfo.getDishId());
            //初始化一个点赞收藏表
            MenuPopularInfo menuPopularInfo = new MenuPopularInfo();
            String dishId = dishInfo.getDishId();
            menuPopularInfo.setDishId(dishId);
//            menuPopularInfo.setDishId(dishInfo.getDishId());
            menuPopularServiceImp.insertDish(menuPopularInfo);
        } catch (AppException e) {
            resultEntity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(resultEntity, resp);
//        req.getRequestDispatcher("userPage.html").forward(req,resp);
    }


    /**
     * 查找dishInfos集合的dish的图片,并设置写回对象
     *
     * @param dishInfos 菜谱信息
     * @return 返回
     */
    private ResultEntity getResultEntity(List<DishInfo> dishInfos) {
        ResultEntity resultEntity;
        dishInfos.forEach(e1 -> {
            try {
                List<PicturesInfo> picturesInfos = picturesServiceImp.queryAllPictures(e1);
                e1.setPicturesInfoList(picturesInfos);
            } catch (AppException e) {
                e.printStackTrace();
            }
        });
        resultEntity = ResultEntity.SUCCESS(ResultEnum.FIND_SUCCESS, dishInfos);
        return resultEntity;
    }

//    /**
//     * 多表查询
//     * 分页显示 根据时间+tag查出来所有文章简介(图片+简介)
//     *
//     * @param req  请求
//     * @param resp 响应
//     */
//
//    private void queryByTagAndCreateTime(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String servletPath = req.getServletPath();
//        //传来的page
//        Page page = BeanUtil.parseRequest(req, Page.class);
//        //需要进行Page转换
//        PageSqlUtil pageSql = PageSqlUtil.getInstance(page);
//        ResultEntity resultEntity = null;
//        switch (pageSql.getType()) {
//            case 7:
//                allocation(req, pageSql, resultEntity);
//                break;
//            case 8:
//                allocation(req, pageSql, resultEntity);
//
//        }
//
//
//        JSONUtil.write(resultEntity, resp);
//
//    }


//    private void allocation(HttpServletRequest req, PageSqlUtil pageSql, ResultEntity resultEntity) {
//        String servletPath = req.getServletPath();
//        List<DishInfo> dishInfos;
//        if (!("0".equals(pageSql.getId()))) {
//            try {
//                if (servletPath.endsWith("desc")) {
//                    dishInfos = dishServiceImp.listTagDishInfoByTimePageDesc(pageSql);//dishInfos是查出来所有文章的集合
//                } else {
//                    dishInfos = dishServiceImp.listTagDishInfoByTimePageAsc(pageSql);
//                }
//                resultEntity = getResultEntity(dishInfos);
//            } catch (AppException e) {
//                resultEntity = ResultEntity.ERROR(e);
//            }
//        } else {
//            try {
//                if (servletPath.endsWith("desc")) {
//                    dishInfos = dishServiceImp.listAllDishInfoByTimePageDesc(pageSql);//dishInfos是查出来所有文章的集合
//                } else {
//                    dishInfos = dishServiceImp.listAllDishInfoByTimePageAsc(pageSql);
//                }
//                resultEntity = getResultEntity(dishInfos);
//            } catch (AppException e) {
//                resultEntity = ResultEntity.ERROR(e);
//            }
//        }
//    }
}
