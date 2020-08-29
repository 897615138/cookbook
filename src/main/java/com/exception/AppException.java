package com.exception;


import com.utils.ResultEnum;

/**
 * 自定义异常
 */
public class AppException extends Exception {

    public AppException(String msg) {
        super(msg);
    }

    public AppException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }

}
