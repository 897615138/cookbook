package com.etc;

import com.alibaba.fastjson.JSON;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LikeSystem {

    private static final DB db = DBMaker.fileDB("like.db")
            .fileMmapEnable()
            .transactionEnable()//事务问题
            .closeOnJvmShutdown()//JVM关闭时关闭db
            .make();
//
//            DBMaker.fileDB("like.db")
//            .fileMmapEnable()
//            .transactionEnable()//事务问题
//            .closeOnJvmShutdown()//JVM关闭时关闭db
//            .make();

    //生成map
    private static final HTreeMap<String, String> likeMap;

    //绑定序列化机制
    static {
        likeMap = db.hashMap("likeMap", Serializer.STRING, Serializer.STRING).createOrOpen();
    }

    //设置两个Map的初始化
    public static void init() {
        System.out.println("likeSystem初始化完毕---------------");
        System.out.println("目前的文章的点赞情况为:");
        if (likeMap.isEmpty()) {
            System.out.println("为空");
        }
        likeMap.forEach((k, v) -> {
            System.out.print("文章号:" + k);
            System.out.println("点赞人：" + v);
        });
    }

    public static void initDish(String dishId) {
        System.out.println("新增一篇文章");
        likeMap.put(dishId, JSON.toJSONString(new ArrayList<String>()));
        db.commit();
    }

    /**
     * 文章的点赞与取消点赞
     *
     * @param dishId 菜谱id
     * @param userId 用户id
     * @return true 为点赞  false为取消点赞
     */
    public static Boolean LikeOpation(String dishId, String userId) {
        //把存入map的取出来 文章喜欢的人
        //Objects.requireNonNull(JSON.parseArray(followMap.get(user), String.class));
        List<String> likeUser = Objects.requireNonNull(JSON.parseArray(likeMap.get(dishId), String.class));
        System.out.println(likeUser);
        if (likeUser.contains(userId)) {
            //取消点赞
            likeUser.remove(userId);
            likeMap.put(dishId, JSON.toJSONString(likeUser));
            db.commit();
            return false;
        } else {
            //点赞
            likeUser.add(userId);
            likeMap.put(dishId, JSON.toJSONString(likeUser));
            db.commit();
            return true;
        }
    }

    /**
     * 点过赞 返回false
     * @param dishId 菜谱id
     * @param userId 用户id
     * @return 返回
     */
    public static Boolean isLike(String dishId,String userId){
        List<String> likeUser = Objects.requireNonNull(JSON.parseArray(likeMap.get(dishId), String.class));
        if(likeUser.contains(userId)) {
            return false;
        }
        return true;
    }

    public static void DishDelete(String dishId){
        likeMap.remove(dishId);
        db.commit();

    }

}
