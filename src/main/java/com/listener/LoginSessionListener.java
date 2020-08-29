package com.listener;


import com.entity.UserInfo;
import com.utils.ConstEnum;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class LoginSessionListener implements HttpSessionListener {
    //登陆的时候进行相关处理
    //session被销毁的时候进行记录的移除
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Object ob = session.getAttribute(ConstEnum.LOGIN_USER.getMsg());
        if (ob != null) {
            UserInfo user = (UserInfo) ob;
            //移除登录记录
            String s = SessionLogs.accountMap.get(user.getUserName());
            SessionLogs.accountMap.remove(user.getUserName());
            SessionLogs.sessionMap.remove(s);
        }
    }
}
