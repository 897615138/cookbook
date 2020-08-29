package com.jdbc.parse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author zp
 * @create 2020-07-27-22:22
 */
@Data
@ToString
public class ResultMap {

    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private String classname;

    @Setter
    private Map<String, Result> map;//map.put(column, result);

    @Getter
    @Setter
    private Class<?> returnType;//获得返回值类型

    public Result get(String key) {
        return map.get(key);
    }
}
