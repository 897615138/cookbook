package com.service;

import com.entity.UserInfo;
import com.exception.AppException;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 9:26
 * @slogan Just do it continually
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param userInfo 用户信息
     * @return 是否注册成功
     */
    void register(UserInfo userInfo) throws AppException;


    /**
     *检查用户名是否存在
     * @param userInfo
     * @return
     */
    void checkRegisterName(UserInfo userInfo) throws AppException;


    /**
     * 判断用户的邮箱是否重复
     * @param userInfo
     * @return
     */

    void checkRegisterMail(UserInfo userInfo) throws AppException;
    /**
     * 用户登录
     *
     * @param userInfo 用户信息 多方式登录
     */
    UserInfo login(UserInfo userInfo) throws AppException;



    /**
    * 信息更新
     *
     * @param userInfo 更新的用户信息
     */
    void updateUser(UserInfo userInfo) throws AppException;



    void deleteUser(UserInfo userInfo) throws AppException;
//
//
//
//    /**
//     * 单个用户搜索
//     *
//     * @param userInfo 查询的用户信息
//     * @return 根据id查询到的用户
//     */
    UserInfo searchUser(UserInfo userInfo);
}
