package com.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @description:
 * @author: JIll Wang
 * @create: 2020-07-14 11:33
 **/
public class BeanUtil {

    public static <T> T parseRequest(HttpServletRequest request, Class<T> tClass) {
        T t;
        //  所有 key  的集合
        Enumeration<String> names = request.getParameterNames();
        HashMap<String, String> map = new HashMap<>();
        while (names.hasMoreElements()) {
            // 获取单个key
            String s = names.nextElement();
            String value = request.getParameter(s);
            map.put(s, value);
        }
        t = JSONArray.parseObject(JSON.toJSONString(map), tClass);
        return t;
    }

    public static <T> T parseRequest2(HttpServletRequest request, Class<T> tClass) {
        T t;
        //  所有 key  的集合
        Enumeration<String> names = request.getParameterNames();
        HashMap<String, String> map = new HashMap<>();
        ArrayList<String> fields = new ArrayList<>();
        Field[] field = tClass.getDeclaredFields();
//        Field[] field = Class.class.getDeclaredFields();  //获取实体类的所有属性，返回Field数组
        for (Field item : field) {  //遍历所有属性
            String name = item.getName();    //获取属性的名字
            fields.add(name);
        }
        while (names.hasMoreElements()) {
            // 获取单个key
            String s = names.nextElement();
            if (fields.contains(s)) {
                String value = request.getParameter(s);
                map.put(s, value);
            }
        }
        t = JSONArray.parseObject(JSON.toJSONString(map), tClass);
        return t;
    }


    public static void main(String[] args) {

    }

}