package com.utils;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/17 9:53 下午
 */
public class TimerUtil extends TimerTask {
    private static final int C_SCHEDULE_HOUR = 0;
    private static boolean isRunning = false;
    private ServletContext context = null;

    public TimerUtil(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance();
        System.out.println("当前时间" + cal.get(Calendar.HOUR_OF_DAY));
        if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) {//24小时任务还没结束 自己将isRunning 变为false
            isRunning = false;
        }
        if (!isRunning) {
            System.out.println("开始进入");
            isRunning = true;
            context.log("开始执行指定任务");//
            //真正要执行的任务
            SqlUtil.doSql();
            //任务执行完为了下一次继续执行,如果任务未执行完 isRunning一直为false
            isRunning = false;
            context.log("指定任务执行结束");
//            }
        } else {
            context.log("上一次任务执行还未结束");
        }
    }

}