package com.etc;

import com.alibaba.fastjson.JSON;
import com.entity.UserInfo;
import com.exception.AppException;
import com.utils.ResultEnum;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/11 9:31 下午
 */
public class FollowSystemUp {
    private static final DB db = DBMaker.fileDB("temple.db")
            .fileMmapEnable()
            .transactionEnable()//事务问题
            .closeOnJvmShutdown()//JVM关闭时关闭db
            .make();
    //生成两个map
    private static final HTreeMap<String, String> followMap;
    private static final HTreeMap<String, String> fansMap;

    //绑定序列化机制
    static {
        followMap = db.hashMap("followMap", Serializer.STRING, Serializer.STRING).createOrOpen();
        fansMap = db.hashMap("fansMap", Serializer.STRING, Serializer.STRING).createOrOpen();
    }

    //设置两个Map的初始化
    public static void init() {
        System.out.println("followSystem 初始化完毕---------------");
        System.out.println("目前的用户关注列表为:");
        if (fansMap.isEmpty()) {
            System.out.println("为空");
        }
        if (followMap.isEmpty()) {
            System.out.println("为空");
        }
        followMap.forEach((k, v) -> {
            System.out.print("用户:" + k + "\n");
            JSON.parseArray(v, String.class).forEach(System.out::println);

        });
        System.out.println("目前的用户粉丝列表为:");
        fansMap.forEach((k, v) -> {
            System.out.print("用户:" + k + "\n");
            JSON.parseArray(v, String.class).forEach(System.out::println);

        });
    }

    /**
     * 初始化map 再每个人注册成功的时候都有自己的初始化
     */
    public static void initMap(UserInfo userInfo) {
        followMap.put(changeObject(userInfo), changeObject(new ArrayList<UserInfo>()));
        fansMap.put(changeObject(userInfo), changeObject(new ArrayList<UserInfo>()));
        db.commit();
        System.out.println("新添一名用户~");

    }


    /**
     * 同时拿到两个用户 follow别人的与粉丝列表分别添加
     *
     * @param user 用户
     * @paramfollowedUser
     */
    public static void follow(UserInfo user, UserInfo followedUser) {
        List<UserInfo> strings = JSON.parseArray(followMap.get(changeObject(user)), UserInfo.class);
        List<UserInfo> userInfos = judgeList(strings);
        userInfos.add(followedUser);
//        System.out.println(strings);
        followMap.put(changeObject(user), changeObject(userInfos));
        List<UserInfo> strings1 = JSON.parseArray(fansMap.get(changeObject(followedUser)), UserInfo.class);
        List<UserInfo> userInfos1 = judgeList(strings1);
        userInfos1.add(user);
        fansMap.put(changeObject(followedUser), changeObject(userInfos1));
        System.out.println("用户关注成功");
        db.commit();
    }

    /**
     * 同理 取消关注
     *
     * @param user         用户
     * @param followedUser 粉丝
     */
    public static void unFollow(UserInfo user, UserInfo followedUser) {
        List<UserInfo> strings = Objects.requireNonNull(JSON.parseArray(followMap.get(changeObject(user)), UserInfo.class));
        strings.remove(JSON.parseObject(changeObject(followedUser), UserInfo.class));
//        System.out.println(strings);
        followMap.put(changeObject(user), changeObject(strings));
        List<UserInfo> strings1 = Objects.requireNonNull(JSON.parseArray(fansMap.get(changeObject(followedUser)), UserInfo.class));
        strings1.remove(JSON.parseObject(changeObject(user), UserInfo.class));
        fansMap.put(changeObject(followedUser), changeObject(strings1));
        System.out.println("用户取消关注成功!");
        db.commit();
    }

    public static Boolean isExist(UserInfo user, UserInfo followedUser) {
        List<UserInfo> strings = JSON.parseArray(followMap.get(changeObject(user)), UserInfo.class);
        if (strings == null) return false;
        System.out.println("关注前列表:" + strings);
        return strings.contains(JSON.parseObject(changeObject(followedUser), UserInfo.class));//注意这里的写法
    }


    public static List<UserInfo> showFans(UserInfo user) throws AppException {
        List<UserInfo> userInfos = JSON.parseArray(fansMap.get(changeObject(user)), UserInfo.class);
        if (userInfos==null)throw new AppException(ResultEnum.SYS_ERROR);
        return userInfos;

    }

    public static List<UserInfo> showFollows(UserInfo user) throws AppException {
        List<UserInfo> userInfos = JSON.parseArray(followMap.get(changeObject(user)), UserInfo.class);
        if (userInfos==null)throw new AppException(ResultEnum.SYS_ERROR);
        return userInfos;
    }

    public static String changeObject(Object o) {
        return JSON.toJSONString(o);
    }

//    public static Boolean isEq(List<UserInfo> strings,User) {
//       Boolean flag;
//       strings.forEach(e->{
//           e.equals()
//       });
//    }


    public static List<UserInfo> judgeList(List<UserInfo> strings) {
        if (strings == null) {
            ArrayList<UserInfo> userInfos = new ArrayList<>();
            strings = userInfos;
        }
        return strings;
    }

    public static void main(String[] args) {


    }


}
