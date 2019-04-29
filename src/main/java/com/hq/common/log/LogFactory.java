package com.hq.common.log;

import com.hq.common.constant.state.BussinessLogType;
import com.hq.common.constant.state.LogSucceed;
import com.hq.common.constant.state.LoginLogType;
import com.hq.model.LoginLog;
import com.hq.model.OperationLog;

import java.sql.Timestamp;

/**
 * @description: 日志对象工厂
 * @author: Mr.Huang
 * @create: 2019-03-19 11:05
 **/
public class LogFactory {

    /**
     * 创建登录日志
     */
    public static LoginLog createLoginLog(LoginLogType loginLogType, Integer userId, String msg, String ip){
        LoginLog loginLog = new LoginLog();
        loginLog.setLogName(loginLogType.getMessage());
        loginLog.setUserId(userId);
        loginLog.setMessgae(msg);
        loginLog.setIp(ip);
        loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
        loginLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return loginLog;
    }
    /**
     * 创建操作日志
     */
    public static OperationLog createOperationLog(BussinessLogType bussinessLogType, Integer userId, String bussinessName,String clazzName,String methodName,String msg,LogSucceed logSucceed){
        OperationLog operationLog = new OperationLog();
        operationLog.setLogName(bussinessName);
        operationLog.setLogType(bussinessLogType.getMessage());
        operationLog.setUserId(userId);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setSucceed(logSucceed.getMessage());
        operationLog.setMessage(msg);
        operationLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return operationLog;
    }

}
