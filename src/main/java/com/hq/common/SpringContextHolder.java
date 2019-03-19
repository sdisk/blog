package com.hq.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 14:39
 **/
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        assertApplicationContext();
        return applicationContext;
    }

    private static void assertApplicationContext() {
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

    public static <T> T getBean(String beanName){
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }
    public static <T> T getBean(Class<T> clazz){
        assertApplicationContext();
        return applicationContext.getBean(clazz);
    }
}
