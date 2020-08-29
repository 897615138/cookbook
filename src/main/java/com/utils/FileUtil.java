package com.utils;
/**
 * 用来处理字符串的工具类
 */

import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/17 6:00 下午
 */
public class FileUtil {
    //    public static final List<String> whiteList;
    public static final List<String> sqlList;
    public static final List<String> loginBlackList;
    public static final List<String> allHtmlList;
    public static final List<String> blackList;
    public static final HashMap<String, List<String>> stringListHashMap = new HashMap<>();
    public static final HashMap<String, String> stringStringHashMap = new HashMap<>();
    public static final String timeLimit;


    static {
        JsonParse(JsonTest("util.json"));
//        whiteList = getMapList("whiteList");
        sqlList = getMapList("sqlList");
        loginBlackList = getMapList("loginBlackList");
        timeLimit = getMapValue("timerArgs");
        blackList = getMapList("blackList");
        allHtmlList = getMapList("allHtmlList");
//        System.out.println("目前网站的白名单如下:" + whiteList);
        System.out.println("目前网站的黑名单如下:" + blackList);
        System.out.println("目前网站的登陆黑名单如下:" + loginBlackList);
        System.out.println("目前网站需要进行的sql语句如下:" + sqlList);
    }

    /**
     * 读取json文件并解析成String
     *
     * @param path
     */
    public static String JsonTest(String path) {
        String configPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("config/fileconfig/" + path)).getPath();
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(configPath), StandardCharsets.UTF_8);
            int ch;
            while ((ch = inputStreamReader.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 使用json解析json格式文件为map
     *
     * @param str
     * @return
     */
    public static void JsonParse(String str) {
        Map<String, Object> jsonObject = JSONObject.parseObject(str);
        //通过foreach 强转 拿到key-value
        jsonObject.forEach((k, v) -> {
            if (v.getClass() == String.class) {//如果是普通的String值
                stringStringHashMap.put(k, (String) v);
            } else {//如果是list
                stringListHashMap.put(k, (List<String>) v);
            }
        });
//        System.out.println(stringListHashMap);
//        System.out.println(stringStringHashMap);
    }


    public static List<String> getMapList(String key) {
        return stringListHashMap.get(key);

    }

    public static String getMapValue(String key) {
        return stringStringHashMap.get(key);

    }

    public static void main(String[] args) {
//

    }
}
