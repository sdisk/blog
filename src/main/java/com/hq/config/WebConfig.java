package com.hq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.hq.common.xss.XssFilter;
import com.hq.interceptor.BaseInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;


/**
 * @author: Mr.Huang
 * @create: 2019-03-19 09:45
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private BaseInterceptor baseInterceptor;

    @Autowired
    private BlogProperties blogProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Bean
    public ServletRegistrationBean druidServletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet());
        registrationBean.addUrlMappings("/druid/*");
        //查看druid监控页面的登录信息
        registrationBean.addInitParameter("loginUsername", "admin");
        registrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        //registrationBean.addInitParameter("resetEnable", "false");
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

    /**
     * 增加swagger的支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (blogProperties.getSwaggerOpen()) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        //边框
        properties.put("kaptcha.border", "no");
        //边框颜色 (r,g,b)
        properties.put("kaptcha.border.color", "105,179,90");

        //图片宽
        properties.put("kaptcha.image.width", "120");
        //图片高
        properties.put("kaptcha.image.height", "40");

        properties.put("kaptcha.session.key", "code");
        //验证码长度
        properties.put("kaptcha.textproducer.char.length", "4");
        //字体
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        //字体大小
        properties.put("kaptcha.textproducer.font.size", "35");
        //字体颜色
        properties.put("kaptcha.textproducer.font.color", "black");
        //干扰颜色
        properties.put("kaptcha.noise.color", "black");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
