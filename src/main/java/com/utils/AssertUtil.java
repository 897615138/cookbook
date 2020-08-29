package com.utils;


import com.exception.MyXMLParseException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author zp
 * @create 2020-07-27-11:28
 */
public class AssertUtil {
    public static void isBlank(String object) {
        if (Objects.isNull(object) || "".equals(object)) {
            throw new MyXMLParseException("解析结果为空或为空字符串");
        }
    }

    public static void isNull(Object object) {
        if (Objects.isNull(object)) {
            throw new RuntimeException("解析结果为空");
        }
    }


    public static void isAnyBlank(String... object) {
        if (StringUtils.isAnyBlank(object)) {
            throw new RuntimeException("解析结果存在空值或空字符串");
        }
    }
}
