package com.jdbc.parse;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author zp
 * @create 2020-07-27-22:23
 */
@Data
@ToString
public class MapperStatement {
    private String mapperName;//方法的映射名字

    private ResultMap resultMap; //方法执行的返回结果

    private String parameterType;//方法传递的参数类型

    private List<String> fieldNames;//解析  查询参数名
    private String sql;

    private String sqlType;//sql语句的类型 可以用枚举

}
