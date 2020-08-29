package com.etc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LikeAndCollectSystem {
    //用户点赞Map   文章id   用户id
    private static final ConcurrentHashMap<String, List<String>> likeMap=new ConcurrentHashMap<>();

    //用户收藏Map   用户   用户id
    private static final ConcurrentHashMap<String,List<String>> collectMap=new ConcurrentHashMap<>();

    //当文章创建出来的时候 就应该初始化这两个Map
    public static void initMap(String dishId){
        likeMap.put(dishId,new ArrayList<>());
        collectMap.put(dishId,new ArrayList<>());
    }

    /** 进行用户的对于文章的点赞查询
     * @param dishId
     * @param userId
     * @return  true 为用户可以点赞 并把用户的id放入list中  false 用户已经点赞 ,进行取消点赞
     */
    public static Boolean isLike(String dishId,String userId){
        List<String> likeuser = likeMap.get(dishId);
        if(likeuser.contains(userId)) {
            likeuser.remove(userId);
            return false;
        }
        likeuser.add(userId);
        return  true;
    }

}
