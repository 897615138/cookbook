package com.utils;


import com.exception.AppException;
import lombok.Data;

import java.util.List;

/**
 * 前后端数据交互的对象
 *
 * @param <T>
 */
@Data
public class ResultEntity<T> {
    private static final Object ob = new Object();
    private static final List<Object> LIST = null;
    private boolean result;
    private String msg;
    private T t;
    private List<T> data;
    private Page page;

    public static ResultEntity<Object> SUCCESS(ResultEnum resultEnum) {
        ResultEntity<Object> entity = new ResultEntity<>();
        entity.setT(ob);
        entity.setResult(true);
        entity.setMsg(resultEnum.getMsg());
        entity.setData(LIST);
        return entity;
    }
    public static ResultEntity<Object> SUCCESS(ResultEnum resultEnum,Page page) {
        ResultEntity<Object> entity = new ResultEntity<>();
        entity.setT(ob);
        entity.setPage(page);
        entity.setResult(true);
        entity.setMsg(resultEnum.getMsg());
        entity.setData(LIST);
        return entity;
    }

    public static <T> ResultEntity<T> SUCCESS(ResultEnum resultEnum, T t) {
        ResultEntity<T> entity = new ResultEntity<>();
        entity.setT(t);
        entity.setResult(true);
        entity.setMsg(resultEnum.getMsg());
        return entity;
    }

    public static <T> ResultEntity<T> SUCCESS(ResultEnum resultEnum, List<T> list) {
        ResultEntity<T> entity = new ResultEntity<>();
        entity.setT(null);
        entity.setResult(true);
        entity.setMsg(resultEnum.getMsg());
        entity.setData(list);
        return entity;
    }

    public static <T> ResultEntity<T> SUCCESS(ResultEnum resultEnum, List<T> list, Page page) {
        ResultEntity<T> entity = new ResultEntity<>();
        entity.setT(null);
        entity.setResult(true);
        entity.setMsg(resultEnum.getMsg());
        entity.setData(list);
        entity.setPage(page);
        return entity;
    }


    public static ResultEntity<Object> FAIL(ResultEnum resultEnum) {
        ResultEntity<Object> entity = new ResultEntity<>();
        entity.setT(ob);
        entity.setResult(false);
        entity.setMsg(resultEnum.getMsg());
        return entity;
    }


    public static ResultEntity<Object> ERROR(AppException appException) {
        ResultEntity<Object> entity = new ResultEntity<>();
        entity.setT(ob);
        entity.setResult(false);
        entity.setMsg(appException.getMessage());
        return entity;
    }

    public static ResultEntity<Object> ERROR(ResultEnum resultEnum) {
        ResultEntity<Object> entity = new ResultEntity<>();
        entity.setT(ob);
        entity.setResult(false);
        entity.setMsg(resultEnum.getMsg());
        entity.setData(LIST);
        return entity;
    }

}
