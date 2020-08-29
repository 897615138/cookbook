package com.dao;

import com.entity.UserInfo;

import java.util.List;

/**
 * @author zp
 * @create 2020-07-27-21:59
 */
public interface IUserInfoMapper {
    /**
     * 登录用户
     * 多条件登陆 用户名和邮箱
     *
     * @param userInfo 设置id+password
     * @return 根据返回的list 的来进行判断
     */
    List<UserInfo> loginByMany(UserInfo userInfo);

    /**
     * 注册新用户
     *
     * @param userInfo
     */
    Integer register(UserInfo userInfo);


    /**
     * 更新用户信息
     *
     * @param userInfo 用户
     */
    Integer updateUser(UserInfo userInfo);

    /**wj
     * 通过userId获得对应的User对象
     * @param userId userId
     * @return User对象
     */
    List<UserInfo> getUserById(UserInfo userId);


    /**
     * 通过用户名查找是否存在该用户
     */
    List<UserInfo> selectUserByName(UserInfo userInfo);


    /**
     * 根据用户邮箱判断是否存在该用户
     */
    List<UserInfo> selectUserByMail(UserInfo userInfo);


    /**
     * 逻辑删除用户
     * @param userInfo
     * @return
     */
    Integer deleteUser(UserInfo userInfo);
}
