package com.jdbc.parse;

/**
 * @author zp
 * @create 2020-07-31-18:02
 */
//TODO: 然后呢?
public enum SqlType {
    SELECT("select"), UPDATE("update"), DELETE("delete"), INSERT("insert"), LIMIT("limit");
    private final String name;

    SqlType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
