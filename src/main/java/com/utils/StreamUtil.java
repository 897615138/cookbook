package com.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @description: 流式计算工具类
 * @author: JIll Wang
 * @create: 2020-07-31 18:05
 **/
public class StreamUtil {
    /**
     * 流式运算去重
     *
     * @param keyExtractor 比较的键值 （去重的根据）
     *                     使用举例：
     *                     获得拥有的收藏夹id 一个人可以有多个收藏夹
     *                     List<MenuInfo> collect = menus.stream()
     *                     .filter(distinctByKey(MenuInfo::getMenuId))
     *                     .collect(Collectors.toList());
     * @param <T>          泛型
     * @return 去重之后的流
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
