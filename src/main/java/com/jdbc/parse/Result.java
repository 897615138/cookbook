package com.jdbc.parse;

import lombok.Data;
import lombok.ToString;

/**
 * @author zp
 * @create 2020-07-27-22:19
 */
@Data
@ToString
public class Result {
    private String fieldName;
    private String columnName;
    private Class<?> fieldType;
}
