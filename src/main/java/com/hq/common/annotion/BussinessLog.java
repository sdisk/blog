package com.hq.common.annotion;

import java.lang.annotation.*;

/**
 * @author: Mr.Huang
 * @create: 2019-03-19 15:16
 **/
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {

    /**
     * 业务名称
     */
    String value() default "";

    /**
     *  被修改的实体唯一标识
     */
    String key() default "id";
}
