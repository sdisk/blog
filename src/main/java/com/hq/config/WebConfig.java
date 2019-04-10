package com.hq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.hq.common.xss.XssFilter;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



/**
 * @author: Mr.Huang
 * @create: 2019-03-19 09:45
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Bean
    public ServletRegistrationBean druidServletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet());
        registrationBean.addUrlMappings("/druid/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean druidStarFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions","/static/*,*.error,*.gif,*.jpg,*.png,*.admin,,*.html,*.ico,/druid,/druid/*");
        //用于监控页面的用户名显示，登录后需要主要将username注入进来
        filterRegistrationBean.addInitParameter("principalSessionName", "username");
        return filterRegistrationBean;
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor(){
        return new DruidStatInterceptor();
    }

    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        //定义切面
        String pattern = "com.hq.service.impl.*";
        druidStatPointcut.setPatterns(pattern);
        return druidStatPointcut;
    }

    /**
     * druid为druidStatPointcut添加拦截
     */
    @Bean
    public Advisor druidStatAdvisor(){
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }

    /**
     * driud数据库连接池监控
     */
    @Bean
    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator(){
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanTypeAutoProxyCreator;
    }

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        XssFilter xssFilter = new XssFilter();
        //无需过滤
        //xssFilter.setUrlExclusion(Arrays.asList("/notice/update", "notice/add"));
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(xssFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
