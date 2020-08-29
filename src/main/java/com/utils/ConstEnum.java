package com.utils;

public enum ConstEnum {

    INSIDE_URL("WEB-INF/templates/"),
    LOGIN_USER("LOGIN_USER"),
    FIND_DISHES("DISHES"),
    SEARCH_RESULT("SEARCH_RESULT");

    private final String msg;

    ConstEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
