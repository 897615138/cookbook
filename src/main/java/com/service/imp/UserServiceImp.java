package com.service.imp;

import com.dao.IUserInfoMapper;
import com.entity.UserInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.jdbc.parse.Result;
import com.service.UserService;
import com.utils.ResultEnum;

import java.util.List;


public class UserServiceImp implements UserService {
    private static final MapperProxy<IUserInfoMapper> iUserInfoMapperMapperProxy = new MapperProxy<>();
    private static final IUserInfoMapper mapper = iUserInfoMapperMapperProxy.getMapper(IUserInfoMapper.class);



    /**
     * 用户注册
     *
     * @param userInfo 用户信息
     * @return 检测用户名是否重复
     */
    @Override
    public void checkRegisterName(UserInfo userInfo) throws AppException {
        List<UserInfo> userInfos = mapper.selectUserByName(userInfo);
       /* System.out.println(userInfos);*/
        if(!userInfos.isEmpty()){
            throw new AppException(ResultEnum.REPEAT_USER);
        }
    }

    /**
     * 检查用户邮箱是否重复
     * @param userInfo
     * @return
     * @throws AppException
     */
    @Override
    public void checkRegisterMail(UserInfo userInfo) throws AppException {
        List<UserInfo> userInfos = mapper.selectUserByMail(userInfo);
        if(!userInfos.isEmpty()){
            throw new AppException(ResultEnum.REPEAT_MAIL);
        }
    }


    /**
     * 检查是否登录成功
     * @param userInfo 用户信息
     * @return
     * @throws AppException
     */
    @Override
    public void register(UserInfo userInfo) throws AppException {
        Integer register = mapper.register(userInfo);
        if(register==0){
            throw new AppException(ResultEnum.SYS_ERROR);
        }
    }


    //登录
    @Override
    public UserInfo login(UserInfo userInfo) throws AppException {
        List<UserInfo> userInfos = mapper.loginByMany(userInfo);
        if(userInfos.isEmpty()){
            throw new AppException(ResultEnum.LOGIN_ERROR);
        }
        return userInfos.get(0);
    }


    //更新用户信息
    @Override
    public void updateUser(UserInfo userInfo) throws AppException {
        Integer integer = mapper.updateUser(userInfo);
        if(integer==0){
            throw new AppException(ResultEnum.SYS_ERROR);
        }
    }

    @Override
    public void deleteUser(UserInfo userInfo) throws AppException {
        Integer integer = mapper.deleteUser(userInfo);
        if(integer==0){
            throw new AppException(ResultEnum.SYS_ERROR);
        }
    }


//    /**
//     * 信息更新
//     *
//     * @param userInfo 更新的用户信息
//     */
//    @Override
//    public void updateUser(UserInfo userInfo) {
//        int i;
//        try {
//            i = mapper.updateUser(userInfo);
//        } catch (Exception e) {
//            i = 0;
//        }
//        return i == 1;
//    }
//
//    /**
//     * 单个用户搜索
//     *
//     * @param userInfo 查询的用户信息
//     * @return 根据id查询到的用户
//     */
    @Override
    public UserInfo searchUser(UserInfo userInfo) {
       List<UserInfo> userById = mapper.getUserById(userInfo);
       /*System.out.println(userById);*/
       return userById.get(0);
   }




    //注册测试(成功)
    /*public static void main(String[] args) {
        UserInfo user =new UserInfo();
        user.setUserId("21078315191");
        user.setUserName("wangji");
        user.setUserPassword("666666");
        UserServiceImp userServiceImp = new UserServiceImp();
        void register = userServiceImp.register(user);
        System.out.println(register);

    }*/


    /*//登录测试(成功)
      public static void main(String[] args) {
        UserInfo user =new UserInfo();
        user.setUserName("wangji");
        user.setUserPassword("666666");
        UserServiceImp userServiceImp = new UserServiceImp();
          UserInfo login = userServiceImp.login(user);
          System.out.println(login);


      }*/
   //根据所给的用户id查询用户信息(成功)
   /*public static void main(String[] args) {
       UserInfo userInfo = new UserInfo();
       userInfo.setUserId("21078315190");
       UserInfo userInfo1 = new UserServiceImp().searchUser(userInfo);
       System.out.println(userInfo1);
   }*/


   /*//更新(成功)
   public static void main(String[] args) {
       UserInfo userInfo = new UserInfo();
       userInfo.setUserId("21078315110");
       userInfo.setUserMail("2398209682@qq.com");
       userInfo.setPassword("666666)
       userInfo.setUserName("wwwwangji");
       userInfo.setUserNickname("吉吉国王");

       void avoid = new UserServiceImp().updateUser(userInfo);
       System.out.println(avoid);

   }*/


    /**
     * 判断用户名是否存在
     * @param args
     */
   /*public static void main(String[] args) {
       UserInfo userInfo = new UserInfo();
       userInfo.setUserName("wangji");
       List<UserInfo> userInfos = mapper.selectUserByName(userInfo);
       System.out.println(userInfos);
   }*/
}


