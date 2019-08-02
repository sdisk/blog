package com.hq.common.log;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 10:52
 **/
public class LogManager {

    //日志操作记录演示
    private static final int OPERATE_DELAY_TIME = 10;

    //异步操作记录日志的线程池
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private LogManager(){}
    private static volatile LogManager logManager;
    public static LogManager getLogManager(){
        if (null == logManager){
            synchronized (LogManager.class){
                if (null == logManager){
                    logManager = new LogManager();
                }
            }
        }
        return logManager;
    }
    public void executeLog(TimerTask timerTask){
        executor.schedule(timerTask, OPERATE_DELAY_TIME, TimeUnit.SECONDS);
    }
}
