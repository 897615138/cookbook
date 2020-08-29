package com.listener;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class SessionLogs {
    // 账号 : 设备
    public final static ConcurrentHashMap<String, String> accountMap = new ConcurrentHashMap<>();
    // 设备 : session
    public final static ConcurrentHashMap<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

}
