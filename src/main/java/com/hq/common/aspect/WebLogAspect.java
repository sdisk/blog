package com.hq.common.aspect;

import com.hq.common.annotion.BussinessLog;
import com.hq.common.log.LogManager;
import com.hq.common.log.LogTaskFactory;
import com.hq.model.User;
import com.hq.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @description:
 * @author: Mr.Huang
 * @create: 2019-03-19 10:34
 **/
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut(value = "@annotation(com.hq.common.annotion.BussinessLog)")
    public void cutService(){}


    @Before("cutService()")
    public void doBefore(JoinPoint point){
        startTime.set(System.currentTimeMillis());
        //请求的内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("URL : " + request.getRequestURI().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : "+ request.getRemoteAddr());
        log.info("CLASS_METHOD : "+ point.getSignature().getDeclaringTypeName()+ "." + point.getSignature().getName());
        log.info("ARGS : "+ Arrays.toString(point.getArgs()));
    }

    @AfterReturning(returning = "re", pointcut = "cutService()")
    public void doAfterReturning(Object re) throws Throwable{
        //记录返回内容
        log.info("RESPONSE :" + re);
        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        //remove对象，防止内存泄漏
        startTime.remove();
    }

    @Around("cutService()")
    public Object recordLog(ProceedingJoinPoint point) throws Throwable{

        Object result = point.proceed();

        try{
            handle(point);
        } catch (Exception e){
            log.error("日志记录出错!", e);
        }
        return result;
    }
    private void handle(ProceedingJoinPoint point)throws Exception{
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(methodSignature instanceof MethodSignature)){
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        String methodName = method.getName();

        //判断用户是否登录，未登录就不做日志
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes
                ()).getRequest();
        User user = ToolUtil.getLoginUser(request);
        if (null == user){
            return;
        }
        //获取拦截方法的参数
        String className = target.getClass().getName();
        Object [] params = point.getArgs();
        Parameter[] parameters = method.getParameters();
        //看下区别
        log.debug(params.toString());
        log.debug(parameters.toString());
        //获取操作名称
        BussinessLog bussinessLog = target.getClass().getAnnotation(BussinessLog.class);
        String key = bussinessLog.key();
        String value = bussinessLog.value();

        StringBuilder sb = new StringBuilder();
        for (Object param : params){
            sb.append(param);
            sb.append("&");
        }
        LogManager.getLogManager().executeLog(LogTaskFactory.operationLog(user.getUid(), key,
                className, methodName, sb.substring(0, sb.length() - 1)));
    }
}
