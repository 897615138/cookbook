package com.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * @author zp
 * @create 2020-07-27-21:35
 * <p>
 * wj20200806
 */
@Data
@ToString
public class UserInfo implements Serializable {
    //用户id UUID
    private String userId;
    //用户名 唯一判断
    private String userName;
    //用户昵称
    private String userNickname;
    //用户密码
    private String userPassword;
    //用户邮箱 唯一判断
    private String userMail;
    //用户头像
    private String userPicture;
    //用户创建时间
    private Date userCreateTime;
    //用户信息最后一次更新时间
    private Date userUpdateTime;
    //用户是否逻辑删除
    private Integer userDelete;

    // private static final long serialVersionUID = 7L;



}
