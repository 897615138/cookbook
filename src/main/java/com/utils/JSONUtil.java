package com.utils;

import com.alibaba.fastjson.JSON;
import com.baidu.ueditor.define.BaseState;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONUtil {


    /**
     * 把数据以json字符串的形式写到前端
     *
     * @param o o
     * @param response 响应
     * @throws IOException 异常
     */
    public static void write(Object o, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(o));
        writer.flush();
    }
    public static void writeBase(String url,HttpServletResponse resp) throws IOException {
        BaseState baseState = new BaseState();
        baseState.setState(true);
        baseState.putInfo("url", url);
        resp.getWriter().print(baseState.toJSONString());

    }

//    public static void writeBase(String url, HttpServletResponse resp) throws IOException {
//        BaseState baseState = new BaseState();
//        baseState.setState(true);
//        baseState.putInfo("url", url);
//        resp.getWriter().print(baseState.toJSONString());

//    }


}
