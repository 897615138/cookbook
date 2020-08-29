package com.controller;


import com.entity.DishInfo;
import com.entity.UserInfo;
import com.etc.FollowSystemUp;
import com.etc.LikeSystem;
import com.exception.AppException;
import com.listener.SessionLogs;
import com.service.imp.DishServiceImp;
import com.service.imp.MenuPopularServiceImp;
import com.service.imp.UserServiceImp;
import com.utils.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;


@WebServlet({"/users/repeat/name", "/users/getFans", "/users/getFollows", "/users/search", "/users/repeat/mail",
        "/users/register", "/users/login", "/users/getLoginUser", "/users/update", "/users/delete", "/users/logout",
        "/users/follow", "/users/isFollow"})
public class UserInfoServlet extends HttpServlet {
    private UserServiceImp userController;
    private DishServiceImp dishServiceImp;
    private MenuPopularServiceImp menuPopularServiceImp;

    @Override
    public void init() {
        //因为初始化只有一次
        userController = new UserServiceImp();
        dishServiceImp = new DishServiceImp();
        menuPopularServiceImp=new MenuPopularServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //初始化各个参数 然后 用switch case 进行跳转
        String servletPath = req.getServletPath();
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json"); //与前面相对应
        //拿到到底是什么样的请求 然后在进行分发
        switch (servletPath) {
            case "/users/repeat/name":
                checkRegisterName(req, resp);
                break;
            case "/users/repeat/mail":
                checkRegisterMail(req, resp);
                break;
            case "/users/register":
                try {
                    register(req, resp);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "/users/login":
                try {
                    login(req, resp);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                break;
            case "/users/getLoginUser":
                getLoginUser(req, resp);
                break;
            case "/users/update":
                updateUser(req, resp);
                break;
            case "/users/delete":
                deleteUser(req, resp);
                break;
            case "/users/search":
                searchUser(req, resp);
                break;
            case "/users/logout":
                logout(req, resp);
                break;
            case "/users/follow":
                follow(req, resp);
                break;
            case "/users/isFollow":
                isFollow(req, resp);
                break;
            case "/users/getFans":
                getFans(req, resp);
                break;
            case "/users/getFollows":
                getFollows(req, resp);
                break;

        }
    }

    private void getFollows(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserInfo userInfo;
        if (req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg()) == null) {
            UserInfo userInfo1 = BeanUtil.parseRequest(req, UserInfo.class);
            userInfo = userController.searchUser(userInfo1);
            //否则
        } else {
            userInfo = (UserInfo) req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg());
        }
        System.out.println(userInfo);
        System.out.println("正在请求关注");
        ResultEntity resultEntity;
        List<UserInfo> strings;
        try {
            strings = FollowSystemUp.showFollows(userInfo);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS, strings);
        } catch (AppException e) {
            resultEntity = ResultEntity.FAIL(ResultEnum.FOLLOW_ERROR);
        }
//        if (strings.isEmpty()) {
//            resultEntity = ResultEntity.FAIL(ResultEnum.FAN_ERROR);
//        } else {
//            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS, strings);
//        }
        JSONUtil.write(resultEntity, resp);
    }

    private void getFans(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //如果没有
        UserInfo userInfo;
        if (req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg()) == null) {
            UserInfo userInfo1 = BeanUtil.parseRequest(req, UserInfo.class);
            userInfo = userController.searchUser(userInfo1);
            //否则
        } else {
            userInfo = (UserInfo) req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg());
        }
        System.out.println(userInfo);
        System.out.println("正在请求粉丝");
        ResultEntity resultEntity;
        List<UserInfo> strings;
        try {
            strings = FollowSystemUp.showFans(userInfo);
            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS, strings);
        } catch (AppException e) {
            resultEntity = ResultEntity.FAIL(ResultEnum.FAN_ERROR);
        }
//        if (strings.isEmpty()) {
//
//        } else {
//
//        }
        JSONUtil.write(resultEntity, resp);
    }
    /**
     * 根据id 搜索用户
     *
     * @param req  请求
     * @param resp 响应
     */
    private void searchUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserInfo userInfo = BeanUtil.parseRequest(req, UserInfo.class);
        System.out.println(userInfo);
        //用指定的id搜索用户对象
        UserInfo userInfo1 = userController.searchUser(userInfo);
        ResultEntity resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS, userInfo1);
        JSONUtil.write(resultEntity, resp);
    }


    //登出
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(ConstEnum.LOGIN_USER.getMsg());
        ResultEntity resultEntity;
        //如果成功登录
        resultEntity = ResultEntity.SUCCESS(ResultEnum.LOGOUT_SUCCESS);
        JSONUtil.write(resultEntity, resp);
    }


    //检查用户名是否已经存在
    private void checkRegisterName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserInfo object = BeanUtil.parseRequest(req, UserInfo.class);
        ResultEntity<?> entity;
        try {
            userController.checkRegisterName(object);
            entity = ResultEntity.SUCCESS(ResultEnum.USABLE_USERNAME);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }


    //检查用户邮箱是否重复
    private void checkRegisterMail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserInfo user = BeanUtil.parseRequest(req, UserInfo.class);
        System.out.println(user);
        ResultEntity<?> entity;
        try {
            userController.checkRegisterMail(user);
            entity = ResultEntity.SUCCESS(ResultEnum.USABLE_MAIL);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }


    //注册
    private void register(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, NoSuchAlgorithmException {
        UserInfo user = BeanUtil.parseRequest(req, UserInfo.class);
        System.out.println(user);
        ResultEntity<?> entity;
        user.setUserId(EncryptionUtil.uuid());
        user.setUserPassword(EncryptionUtil.encryption(user.getUserPassword()).substring(0, 12));
        try {
            userController.register(user);
            entity = ResultEntity.SUCCESS(ResultEnum.SUCCESS_REGISTER);


            //++++++++++
            //如果注册成功给用户的两个map增加元素
            FollowSystemUp.initMap(user);

            //++++++++++

        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }


    //登录
    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, NoSuchAlgorithmException {
        UserInfo userInfo = BeanUtil.parseRequest(req, UserInfo.class);
        userInfo.setUserMail(userInfo.getUserName());
        userInfo.setUserPassword(EncryptionUtil.encryption(userInfo.getUserPassword()).substring(0, 12));
        System.out.println(userInfo);
        ResultEntity entity;
        try {
            UserInfo login = userController.login(userInfo);
            System.out.println(login);
            isRepeatLogin(req, login);
            HttpSession session = req.getSession();
            session.setAttribute(ConstEnum.LOGIN_USER.getMsg(), login);
            entity = ResultEntity.SUCCESS(ResultEnum.LOGIN_SUCCESS);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }


    /**
     * 账号重复登录验证
     *
     * @param request 回应
     */
    public void isRepeatLogin(HttpServletRequest request, UserInfo userInfo) {
        String userAccount = userInfo.getUserName();
        //设备号
        String id = request.getRemoteAddr() + "_" + userAccount;
        HttpSession session = request.getSession();
        session.setAttribute(ConstEnum.LOGIN_USER.getMsg(), userInfo);
        if (SessionLogs.accountMap.containsKey(userAccount)) {// 该账号已经登录
            String loginId = SessionLogs.accountMap.get(userAccount);//已经登录的设备号
            if (loginId.equals(id)) {//同一台设备
                // 更新session Map
                SessionLogs.sessionMap.put(id, session);
            } else {
                HttpSession s = SessionLogs.sessionMap.get(loginId);//上一个登录的会话
                s.invalidate();
                //移除登录记录
                SessionLogs.sessionMap.remove(loginId);
                //放入新纪录
                SessionLogs.sessionMap.put(id, session);
                //更新accountMap
                SessionLogs.accountMap.put(userAccount, id);
            }
        } else {
            SessionLogs.accountMap.put(userAccount, id);
            SessionLogs.sessionMap.put(id, session);
        }

    }

    /**
     * 获得登录的用户信息
     * wj
     *
     * @param req  请求
     * @param resp 响应
     */
    private void getLoginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session0 = req.getSession();
        //获得登录的用户
        UserInfo user = (UserInfo) session0.getAttribute(ConstEnum.LOGIN_USER.getMsg());
        resp.setContentType("application/json");
        ResultEntity resultEntity;
        //如果成功登录
        if (user != null) {
            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS, user);
        } else {
            resultEntity = ResultEntity.FAIL(ResultEnum.LOGIN_WARNING);
        }
        JSONUtil.write(resultEntity, resp);
    }


    //更新
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserInfo userInfo = BeanUtil.parseRequest(req, UserInfo.class);
        System.out.println(userInfo);
        ResultEntity entity;
        //设置修改时间
        userInfo.setUserUpdateTime(new Date());
        //设置user_delete为0
        userInfo.setUserDelete(0);
        try {
            userController.updateUser(userInfo);
            UserInfo userInfo1 = userController.searchUser(userInfo);
            HttpSession session = req.getSession();
            session.setAttribute(ConstEnum.LOGIN_USER.getMsg(), userInfo1);
            entity = ResultEntity.SUCCESS(ResultEnum.UPDATE_SUCCESS);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }


    //注销用户(逻辑删除)
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserInfo userInfo = BeanUtil.parseRequest(req, UserInfo.class);
        System.out.println(userInfo);
        ResultEntity entity;
        try {
            List<DishInfo> dishInfos = dishServiceImp.queryDishByUserId(userInfo);
            //用户有文章 就把文章逻辑删除
            if (dishInfos.size() != 0) {
                dishInfos.forEach(e1 -> {
                    try {
                        //文章的逻辑删除 和点赞map的移除
                        //menu_popular也要删除
                        dishServiceImp.logicDeleteDish(e1);
                        LikeSystem.DishDelete(e1.getDishId());
                        menuPopularServiceImp.deleteMenuPopular(e1);
                    } catch (AppException e) {
                        e.printStackTrace();
                    }
                });
            }
            userController.deleteUser(userInfo);
            entity = ResultEntity.SUCCESS(ResultEnum.SUCCESS);
        } catch (AppException e) {
            entity = ResultEntity.ERROR(e);
        }
        JSONUtil.write(entity, resp);
    }

    /**
     * 关注
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException 异常
     */
    private void follow(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String followedUserId = req.getParameter("followedUserId");
        String userId = req.getParameter("userId");
        System.out.println("被关注的人" + followedUserId);
        System.out.println("关注者" + userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        UserInfo user = userController.searchUser(userInfo);
        userInfo.setUserId(followedUserId);
        UserInfo follow = userController.searchUser(userInfo);
        ResultEntity entity;
        //如果有 则报告已经添加过 如果没有 则 添加
        if (FollowSystemUp.isExist(user, follow)) {
            System.out.println("已经关注");
            FollowSystemUp.unFollow(user, follow);
            System.out.println(11111);
            entity = ResultEntity.FAIL(ResultEnum.UNFOLLOW_SUCCESS);
        } else {
            System.out.println("现在开始关注");
            FollowSystemUp.follow(user, follow);
            entity = ResultEntity.SUCCESS(ResultEnum.FOLLOW_SUCCESS);
        }
        JSONUtil.write(entity, resp);
    }

    /**
     * 取关
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException 异常
     */
    private void isFollow(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String followedUserId = req.getParameter("followedUserId");
        String userId = req.getParameter("userId");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        UserInfo user = userController.searchUser(userInfo);
        userInfo.setUserId(followedUserId);
        UserInfo follow = userController.searchUser(user);
        ResultEntity entity;
        //如果有 则报告已经添加过 如果没有 则 添加
        if (FollowSystemUp.isExist(user, follow)) {
            System.out.println("已经关注");
            entity = ResultEntity.FAIL(ResultEnum.SUCCESS);
        } else {
            entity = ResultEntity.SUCCESS(ResultEnum.SUCCESS);
        }
        JSONUtil.write(entity, resp);
    }


}
