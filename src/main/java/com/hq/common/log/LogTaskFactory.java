package com.hq.common.log;

import com.hq.common.constant.state.BussinessLogType;
import com.hq.common.constant.state.LogSucceed;
import com.hq.common.constant.state.LoginLogType;
import com.hq.dao.LoginLogMapper;
import com.hq.dao.OperationLogMapper;
import com.hq.model.LoginLog;
import com.hq.model.OperationLog;
import com.hq.utils.MapperUtil;
import com.hq.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 14:12
 **/
@Slf4j
public class LogTaskFactory {

    private static LoginLogMapper loginLogMapper = MapperUtil.getMapper(LoginLogMapper.class);
    private static OperationLogMapper operationLogMapper = MapperUtil.getMapper(OperationLogMapper.class);

    public static TimerTask loginLog(Integer userId, String ip){
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    LoginLog loginLog = LogFactory.createLoginLog(LoginLogType.LOGIN, userId, null, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e){
                    log.error("创建登录日志异常!",e);
                }

            }
        };
    }
    public static TimerTask loginLog(String userName, String msg, String ip){
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    LoginLog loginLog = LogFactory.createLoginLog(LoginLogType.LOGIN_FAIL, null, "账号：" + userName + "," +  msg, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e){
                    log.error("创建登录失败日志异常!",e);
                }

            }
        };
    }

    public static TimerTask loginLog(LoginLogType loginLogType, Integer userId, String msg, String ip){
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    LoginLog loginLog = LogFactory.createLoginLog(loginLogType, userId, msg, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e){
                    log.error("创建{}日志异常!", loginLogType.getMessage(), e);
                }

            }
        };
    }
    public static TimerTask exitLog(Integer userId, String ip){
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    LoginLog loginLog = LogFactory.createLoginLog(LoginLogType.EXIT, userId, null, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e){
                    log.error("创建退出登录日志异常!",e);
                }

            }
        };
    }

    public static TimerTask operationLog(Integer userId, String businessName, String clazzName ,String methodName ,String msg){
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    OperationLog operationLog = LogFactory.createOperationLog(BussinessLogType.BUSSINESS, userId, businessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
                    operationLogMapper.insert(operationLog);
                } catch (Exception e){
                    log.error("创建业务日志异常!",e);
                }

            }
        };
    }

    public static TimerTask exceptionLog(Integer userId, Exception exception){
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    String msg = ToolUtil.getExceptionMsg(exception);
                    OperationLog operationLog = LogFactory.createOperationLog(BussinessLogType.BUSSINESS_EXCEPTION, userId, null, null, null, msg, LogSucceed.FAIL);
                    operationLogMapper.insert(operationLog);
                } catch (Exception e){
                    log.error("创建业务异常日志异常!",e);
                }

            }
        };
    }
}
